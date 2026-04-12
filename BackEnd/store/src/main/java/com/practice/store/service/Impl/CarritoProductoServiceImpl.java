package com.practice.store.service.Impl;

import com.practice.store.dto.response.CarritoProductoResponseDto;
import com.practice.store.mapper.CarritoProductoMapper;
import com.practice.store.model.Carrito;
import com.practice.store.model.CarritoProducto;
import com.practice.store.model.Producto;
import com.practice.store.repository.CarritoProductoRepository;
import com.practice.store.repository.CarritoRepository;
import com.practice.store.repository.ProductoRepository;
import com.practice.store.service.CarritoProductoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CarritoProductoServiceImpl implements CarritoProductoService {

    private final CarritoProductoRepository carritoProductoRepository;

    private final ProductoRepository productoRepository;

    private final CarritoRepository carritoRepository;


    @Override
    public CarritoProductoResponseDto agregarProducto(Long carritoId, Long productoId, Integer cantidad) {
        Carrito carrito = carritoRepository.findById(carritoId).orElseThrow();
        Producto producto = productoRepository.findById(productoId).orElseThrow();

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

        // Se devuelve el DTO del producto agregado
        return CarritoProductoMapper.toDto(carritoProducto);
    }

    @Override
    public CarritoProductoResponseDto actualizarCantidad(Long carritoProductoId, Integer nuevaCantidad) {
        CarritoProducto carritoProducto = carritoProductoRepository.findById(carritoProductoId).orElseThrow();
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
                .orElseThrow(() -> new RuntimeException("Carrito no encontrado"));

        return carrito.getProductos().stream()
                .map(CarritoProductoMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public Double calcularTotalCarrito(Long carritoId) {
        Carrito carrito = carritoRepository.findById(carritoId).orElseThrow();
        double total = carrito.getProductos().stream().mapToDouble(
                producto -> producto.getPrecioUnitario() * producto.getCantidad()).sum();

        return total;
    }
}
