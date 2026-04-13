package com.practice.store.service.Impl;

import com.practice.store.dto.response.CarritoProductoResponseDto;
import com.practice.store.exception.CarritoNoEncontradoException;
import com.practice.store.exception.CarritoProductoNoEncontradoException;
import com.practice.store.exception.ProductoNoEncontradoException;
import com.practice.store.mapper.CarritoProductoMapper;
import com.practice.store.model.Carrito;
import com.practice.store.model.CarritoProducto;
import com.practice.store.model.Producto;
import com.practice.store.repository.CarritoProductoRepository;
import com.practice.store.repository.CarritoRepository;
import com.practice.store.repository.ProductoRepository;
import com.practice.store.service.CarritoProductoService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CarritoProductoServiceImpl implements CarritoProductoService {

    private final CarritoProductoRepository carritoProductoRepository;

    private final ProductoRepository productoRepository;

    private final CarritoRepository carritoRepository;

    private final RedisTemplate<String, Object> redisTemplate;



    @Override
    public CarritoProductoResponseDto agregarProducto(Long carritoId, Long productoId, Integer cantidad, String requestId) {
        // 1. Verificar si ya existe en Redis
        if (Boolean.TRUE.equals(redisTemplate.hasKey(requestId))) {
            return (CarritoProductoResponseDto) redisTemplate.opsForValue().get(requestId);
        }
        Carrito carrito = carritoRepository.findById(carritoId).
                orElseThrow(() -> new CarritoNoEncontradoException(carritoId));
        Producto producto = productoRepository.findById(productoId)
                .orElseThrow(() -> new ProductoNoEncontradoException(productoId));

        CarritoProducto carritoProducto = CarritoProducto.builder()
                .precioUnitario(producto.getPrecio())
                .cantidad(cantidad)
                .producto(producto)
                .carrito(carrito)
                .build();

        // Se agrega a la lista de carrito
        carrito.getProductos().add(carritoProducto);

        // Se guardar el carrito actualizado
        carritoRepository.save(carrito);

        // Convertir a DTO
        CarritoProductoResponseDto response = CarritoProductoMapper.toDto(carritoProducto);

        // Guardar resultado en Redis con TTL (ej. 10 minutos)
        redisTemplate.opsForValue().set(requestId, response, Duration.ofMinutes(10));

        return response;
    }

    @Override
    public CarritoProductoResponseDto actualizarCantidad(Long carritoProductoId, Integer nuevaCantidad) {
        CarritoProducto carritoProducto = carritoProductoRepository.findById(carritoProductoId)
                .orElseThrow(() -> new CarritoProductoNoEncontradoException(carritoProductoId));
        carritoProducto.setCantidad(nuevaCantidad);
        carritoProductoRepository.save(carritoProducto);
        return CarritoProductoMapper.toDto(carritoProducto);
    }

    @Override
    public void eliminarProductoDelCarrito(Long carritoProductoId) {
        carritoProductoRepository.deleteById(carritoProductoId);
    }

    @Override
    public List<CarritoProductoResponseDto> obtenerProductosDeCarrito(Long carritoId) {
        Carrito carrito = carritoRepository.findById(carritoId)
                .orElseThrow(() -> new CarritoNoEncontradoException(carritoId));

        return carrito.getProductos().stream()
                .map(CarritoProductoMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public Double calcularTotalCarrito(Long carritoId) {
        Carrito carrito = carritoRepository.findById(carritoId)
                .orElseThrow(() -> new CarritoNoEncontradoException(carritoId));
        double total = carrito.getProductos().stream().mapToDouble(
                producto -> producto.getPrecioUnitario() * producto.getCantidad()).sum();

        return total;
    }
}
