package com.practice.store.service.Impl;

import com.practice.store.dto.request.ProductoRequestDto;
import com.practice.store.dto.response.ProductoResponseDto;
import com.practice.store.exception.ProductoNoEncontradoException;
import com.practice.store.mapper.ProductoMapper;
import com.practice.store.model.Producto;
import com.practice.store.repository.ProductoRepository;
import com.practice.store.service.ProductoService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductoServiceImpl implements ProductoService {

    private final ProductoRepository productoRepository;

    private final RedisTemplate<String, Object> redisTemplate;

    @Override
    public ProductoResponseDto crearProducto(ProductoRequestDto productoRequestDto, String requestId) {
        // 1. Verificar si ya existe en Redis
        if (Boolean.TRUE.equals(redisTemplate.hasKey(requestId))) {
            return (ProductoResponseDto) redisTemplate.opsForValue().get(requestId);
        }
        // 2. Crear producto
        Producto producto = productoRepository.save(ProductoMapper.toEntity(productoRequestDto));
        ProductoResponseDto response = ProductoMapper.toDto(producto);
        // 3. Guardar resultado en Redis con TTL
        redisTemplate.opsForValue().set(requestId, response, Duration.ofMinutes(10));
        return response;
    }


    @Override
    public ProductoResponseDto actualizarProducto(ProductoRequestDto productoRequestDto, Long productoId) {
        Producto producto = productoRepository.findById(productoId)
                .orElseThrow(() -> new ProductoNoEncontradoException(productoId));
        producto.setNombreProducto(productoRequestDto.getNombreProducto());
        producto.setPrecio(productoRequestDto.getPrecio());
        producto.setCantidadStock(productoRequestDto.getCantidadStock());
        productoRepository.save(producto);
        return ProductoMapper.toDto(producto);
    }

    @Override
    public List<ProductoResponseDto> obtenerProductos() {
        return productoRepository.findAll().stream()
                .map(ProductoMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public ProductoResponseDto obtenerProducto(Long productoId) {
        Producto producto = productoRepository.findById(productoId)
                .orElseThrow(() -> new ProductoNoEncontradoException(productoId));
        return ProductoMapper.toDto(producto);
    }

    @Override
    public void eliminarProducto(Long productoId) {
        productoRepository.deleteById(productoId);
    }

}
