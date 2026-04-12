package com.practice.store.service.Impl;

import com.practice.store.dto.request.CarritoRequestDto;
import com.practice.store.dto.response.CarritoResponseDto;
import com.practice.store.mapper.CarritoMapper;
import com.practice.store.mapper.CarritoProductoMapper;
import com.practice.store.model.Carrito;
import com.practice.store.model.CarritoProducto;
import com.practice.store.model.Producto;
import com.practice.store.repository.CarritoRepository;
import com.practice.store.repository.ProductoRepository;
import com.practice.store.service.CarritoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CarritoServiceImpl implements CarritoService {

    private final CarritoRepository carritoRepository;
    private final ProductoRepository productoRepository;

    @Override
    public CarritoResponseDto crearCarrito(CarritoRequestDto carritoRequestDto) {
        Carrito carrito = carritoRepository.save(CarritoMapper.toEntity(carritoRequestDto));
        List<CarritoProducto> productos = carritoRequestDto.getProductos().stream()
                .map(
                        dto -> {
                            Producto producto = productoRepository.findById(dto.getProductoId())
                                    .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
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
        return CarritoMapper.toDto(carrito);
    }

    @Override
    public CarritoResponseDto actualizarCarrito(CarritoRequestDto carritoRequestDto, Long carritoId) {
        Carrito carrito = carritoRepository.findById(carritoId)
                .orElseThrow(() -> new RuntimeException("Carrito no encontrado"));

        List<CarritoProducto> productos = carritoRequestDto.getProductos().stream()
                .map(dto -> {
                    Producto producto = productoRepository.findById(dto.getProductoId())
                            .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
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
                .orElseThrow(() -> new RuntimeException("Carrito no encontrado"));
        return CarritoMapper.toDto(carrito);
    }

    @Override
    public void eliminarCarrito(Long carritoId) {
        carritoRepository.deleteById(carritoId);
    }


}
