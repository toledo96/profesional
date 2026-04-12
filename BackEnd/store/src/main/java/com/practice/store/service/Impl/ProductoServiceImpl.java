package com.practice.store.service.Impl;

import com.practice.store.dto.request.ProductoRequestDto;
import com.practice.store.dto.response.ProductoResponseDto;
import com.practice.store.mapper.ProductoMapper;
import com.practice.store.model.Producto;
import com.practice.store.repository.ProductoRepository;
import com.practice.store.service.ProductoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductoServiceImpl implements ProductoService {

    private final ProductoRepository productoRepository;

    @Override
    public ProductoResponseDto crearProducto(ProductoRequestDto productoRequestDto) {
        Producto producto = productoRepository.save(ProductoMapper.toEntity(productoRequestDto));
        return ProductoMapper.toDto(producto);
    }

    @Override
    public ProductoResponseDto actualizarProducto(ProductoRequestDto productoRequestDto, Long productoId) {
        Producto producto = productoRepository.findById(productoId).orElseThrow();
        producto.setNombreProducto(productoRequestDto.getNombreProducto());
        producto.setPrecio(productoRequestDto.getPrecio());
        producto.setCantidadStock(productoRequestDto.getCantidadStock());
        return ProductoMapper.toDto(producto);
    }

    @Override
    public List<ProductoResponseDto> obtenerProductos() {
        return productoRepository.findAll().stream()
                .map(ProductoMapper::toDto) // convierte cada entidad a su ResponseDto
                .collect(Collectors.toList());
    }

    @Override
    public ProductoResponseDto obtenerProducto(Long productoId) {
        Producto producto = productoRepository.findById(productoId)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
        return ProductoMapper.toDto(producto);
    }

    @Override
    public void eliminarProducto(Long productoId) {
        productoRepository.deleteById(productoId);
    }

}
