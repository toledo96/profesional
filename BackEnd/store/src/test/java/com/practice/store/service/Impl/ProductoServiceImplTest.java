package com.practice.store.service.Impl;

import com.practice.store.dto.request.ProductoRequestDto;
import com.practice.store.dto.response.ProductoResponseDto;
import com.practice.store.model.Producto;
import com.practice.store.repository.ProductoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ProductoServiceImplTest {

    @Mock
    private ProductoRepository productoRepository;

    @InjectMocks
    private ProductoServiceImpl productoService;

    @Mock
    private RedisTemplate<String, Object> redisTemplate;

    @Mock
    private ValueOperations<String, Object> valueOperations;


    Producto producto;

    ProductoRequestDto productoRequestDto;

    ProductoResponseDto productoResponseDto;

    @BeforeEach
    void setup(){

        productoRequestDto = ProductoRequestDto.builder()
                .nombreProducto("Laptop Asus")
                .cantidadStock(15)
                .precio(6000.00)
                .build();

        producto = Producto.builder()
                .idProducto(1L)
                .nombreProducto(productoRequestDto.getNombreProducto())
                .precio(productoRequestDto.getPrecio())
                .cantidadStock(productoRequestDto.getCantidadStock())
                .build();

        productoResponseDto = ProductoResponseDto.builder()
                .idProducto(producto.getIdProducto())
                .nombreProducto(producto.getNombreProducto())
                .precio(producto.getPrecio())
                .build();

    }


    @Test
    void crearProductoExistenteEnRedis() {
        String requestId = UUID.randomUUID().toString();

        Mockito.when(redisTemplate.hasKey(Mockito.anyString())).thenReturn(true);
        Mockito.when(redisTemplate.opsForValue()).thenReturn(valueOperations);
        Mockito.when(valueOperations.get(requestId)).thenReturn(productoResponseDto);

        ProductoResponseDto response = productoService.crearProducto(productoRequestDto, requestId);

        assertEquals("Laptop Asus", response.getNombreProducto());
        Mockito.verify(productoRepository, Mockito.never()).save(Mockito.any());
    }

    @Test
    void actualizarProducto() {

        ProductoRequestDto productoRequestDto1 = ProductoRequestDto.builder()
                .nombreProducto("Laptop Asus-V2")
                .cantidadStock(15)
                .precio(6000.00)
                .build();

        Optional<Producto> productoOptional = Optional.of(producto);
        Mockito.when(productoRepository.findById(Mockito.any())).thenReturn(productoOptional);

        Mockito.when(productoRepository.save(Mockito.any())).thenReturn(producto);

        ProductoResponseDto response = productoService.actualizarProducto(productoRequestDto1,1L);

        assertEquals("Laptop Asus-V2",response.getNombreProducto());
        Mockito.verify(productoRepository).findById(Mockito.anyLong());
        Mockito.verify(productoRepository).save(Mockito.any());
    }

    @Test
    void obtenerProductos() {
        List<Producto> lista = new ArrayList<>();
        lista.add(new Producto(1L,"Lap-top",50.00,12));

        Mockito.when(productoRepository.findAll()).thenReturn(lista);

        List<ProductoResponseDto> listaResponse = productoService.obtenerProductos();

        assertEquals(1,lista.size());
        assertEquals("Lap-top", listaResponse.get(0).getNombreProducto());

        Mockito.verify(productoRepository).findAll();
    }

    @Test
    void obtenerProducto() {
        Optional<Producto> productoOptional = Optional.of(producto);
        Mockito.when(productoRepository.findById(Mockito.anyLong()))
                .thenReturn(productoOptional);

        ProductoResponseDto producto_buscado = productoService.obtenerProducto(1L);

        assertEquals("Laptop Asus",producto_buscado.getNombreProducto());
        Mockito.verify(productoRepository).findById(Mockito.anyLong());
    }

    @Test
    void eliminarProducto() {
        productoRepository.deleteById(1L);
        Mockito.verify(productoRepository).deleteById(Mockito.anyLong());
    }
}