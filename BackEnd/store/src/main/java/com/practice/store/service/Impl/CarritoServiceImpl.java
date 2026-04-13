package com.practice.store.service.Impl;

import com.practice.store.dto.request.CarritoRequestDto;
import com.practice.store.dto.response.CarritoResponseDto;
import com.practice.store.exception.CarritoNoEncontradoException;
import com.practice.store.exception.ProductoNoEncontradoException;
import com.practice.store.mapper.CarritoMapper;
import com.practice.store.mapper.CarritoProductoMapper;
import com.practice.store.model.Carrito;
import com.practice.store.model.CarritoProducto;
import com.practice.store.model.Producto;
import com.practice.store.repository.CarritoRepository;
import com.practice.store.repository.ProductoRepository;
import com.practice.store.service.CarritoService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CarritoServiceImpl implements CarritoService {

    private final CarritoRepository carritoRepository;
    private final ProductoRepository productoRepository;

    private final RedisTemplate<String, Object> redisTemplate;


    @Override
    public CarritoResponseDto crearCarrito(CarritoRequestDto carritoRequestDto,String requestId) {
        // 1. Verificar si ya existe en Redis
        if(Boolean.TRUE.equals(redisTemplate.hasKey(requestId))){
            return (CarritoResponseDto) redisTemplate.opsForValue().get(requestId);
        }

        Carrito carrito = CarritoMapper.toEntity(carritoRequestDto);
        List<CarritoProducto> productos = carritoRequestDto.getProductos().stream()
                .map(
                        dto -> {
                            Producto producto = productoRepository.findById(dto.getProductoId())
                                    .orElseThrow(() -> new ProductoNoEncontradoException(dto.getProductoId()));
                            return CarritoProducto.builder()
                                    .carrito(carrito)
                                    .producto(producto)
                                    .cantidad(dto.getCantidad())
                                    .precioUnitario(producto.getPrecio())
                                    .build();
                        }
                ).collect(Collectors.toList());
        carrito.setProductos(productos);
        carritoRepository.save(carrito);

        // hacermos la conversión a DTO
        CarritoResponseDto response = CarritoMapper.toDto(carrito);

        // guardar resultado en redis con ttl
        redisTemplate.opsForValue().set(requestId, response, Duration.ofMinutes(10));

        return response;
    }

    @Override
    public CarritoResponseDto actualizarCarrito(CarritoRequestDto carritoRequestDto, Long carritoId) {
        Carrito carrito = carritoRepository.findById(carritoId)
                .orElseThrow(() -> new CarritoNoEncontradoException(carritoId));

        List<CarritoProducto> productos = carritoRequestDto.getProductos().stream()
                .map(dto -> {
                    Producto producto = productoRepository.findById(dto.getProductoId())
                            .orElseThrow(() -> new ProductoNoEncontradoException(dto.getProductoId()));
                    return CarritoProducto.builder()
                            .carrito(carrito)
                            .producto(producto)
                            .cantidad(dto.getCantidad())
                            .precioUnitario(producto.getPrecio())
                            .build();
                })
                .collect(Collectors.toList());

        carrito.setProductos(productos);
        carritoRepository.save(carrito);

        return CarritoMapper.toDto(carrito);
    }

    @Override
    public List<CarritoResponseDto> obtenerCarritos() {
        return carritoRepository.findAll().stream()
                .map(CarritoMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public CarritoResponseDto obtenerCarrito(Long carritoId) {
        Carrito carrito = carritoRepository.findById(carritoId)
                .orElseThrow(() -> new CarritoNoEncontradoException(carritoId));
        return CarritoMapper.toDto(carrito);
    }

    @Override
    public void eliminarCarrito(Long carritoId) {
        carritoRepository.deleteById(carritoId);
    }


}
