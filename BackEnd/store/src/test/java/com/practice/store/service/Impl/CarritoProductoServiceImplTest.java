package com.practice.store.service.Impl;

import com.practice.store.dto.request.CarritoProductoRequestDto;
import com.practice.store.dto.response.CarritoProductoResponseDto;
import com.practice.store.dto.response.CarritoResponseDto;
import com.practice.store.mapper.CarritoProductoMapper;
import com.practice.store.model.Carrito;
import com.practice.store.model.CarritoProducto;
import com.practice.store.model.Producto;
import com.practice.store.repository.CarritoProductoRepository;
import com.practice.store.repository.CarritoRepository;
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
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CarritoProductoServiceImplTest {

    @InjectMocks
    private CarritoProductoServiceImpl carritoProductoService;

    @Mock
    private CarritoRepository carritoRepository;

    @Mock
    private ProductoRepository productoRepository;

    @Mock
    private CarritoProductoRepository carritoProductoRepository;


    @Mock
    private RedisTemplate<String, Object> redisTemplate;

    @Mock
    private ValueOperations<String, Object> valueOperations;

    CarritoProductoRequestDto carritoProductoRequestDto;

    CarritoResponseDto carritoResponseDto;

    CarritoProductoResponseDto carritoProductoResponseDto;

    CarritoProducto carritoProducto;

    Carrito carrito;

    Producto producto;

    @BeforeEach
     void setup(){

        producto = Producto.builder()
                .idProducto(1L)
                .nombreProducto("Laptop")
                .precio(2345.56)
                .cantidadStock(12)
                .build();

        List<CarritoProducto> carritoProductoListDto = new ArrayList<>();

        carrito = Carrito.builder()
                .idCarrito(1L)
                .build();
        carritoProductoListDto.add(new CarritoProducto(1L,carrito,producto,5,producto.getPrecio()));

        carrito.setProductos(carritoProductoListDto);


        carritoProductoRequestDto = CarritoProductoRequestDto.builder()
                .productoId(1L)
                .carritoId(1L)
                .cantidad(carritoProductoListDto.get(0).getCantidad())
                .build();

        List<CarritoProductoResponseDto> carritoProductoListResponse = carritoProductoListDto.stream()
                .map(CarritoProductoMapper::toDto).collect(Collectors.toList());

        carritoResponseDto = CarritoResponseDto.builder()
                .idCarrito(1L)
                .productos(carritoProductoListResponse)
                .build();

        carritoProductoResponseDto = CarritoProductoResponseDto.builder()
                .carritoProductoId(1L)
                .cantidad(5)
                .precioUnitario(12565.54)
                .carritoId(1L)
                .nombreProducto("Laptop")
                .productoId(1L)
                .build();

        carritoProducto = CarritoProducto.builder()
                .carrito(carrito)
                .producto(producto)
                .carritoProductoId(1L)
                .cantidad(5)
                .precioUnitario(1234.99)
                .build();
    }

    @Test
    void agregarProducto() {
        String requestId = UUID.randomUUID().toString();

        Mockito.when(redisTemplate.hasKey(requestId)).thenReturn(false);
        Mockito.when(redisTemplate.opsForValue()).thenReturn(valueOperations);

        Optional<Carrito> carritoOptional = Optional.of(carrito);
        Mockito.when(carritoRepository.findById(Mockito.anyLong())).thenReturn(carritoOptional);

        Optional<Producto> productoOptional = Optional.of(producto);
        Mockito.when(productoRepository.findById(Mockito.anyLong())).thenReturn(productoOptional);

        Mockito.when(carritoRepository.save(Mockito.any())).thenReturn(carrito);

        CarritoProductoResponseDto response = carritoProductoService.agregarProducto(1l,1l,5,requestId);

        assertEquals(carritoProductoRequestDto.getCarritoId(),response.getCarritoId());

        Mockito.verify(carritoRepository).save(Mockito.any());
    }

    @Test
    void actualizarCantidad() {

        Optional<CarritoProducto> carritoProductoOptional = Optional.of(carritoProducto);
        Mockito.when(carritoProductoRepository.findById(Mockito.anyLong()))
                .thenReturn(carritoProductoOptional);

        Mockito.when(carritoProductoRepository.save(Mockito.any()))
                .thenReturn(carritoProducto);

        CarritoProductoResponseDto response = carritoProductoService.actualizarCantidad(1L,10);

        assertEquals(10,response.getCantidad());

        Mockito.verify(carritoProductoRepository).findById(Mockito.anyLong());
        Mockito.verify(carritoProductoRepository).save(Mockito.any());
    }

    @Test
    void eliminarProductoDelCarrito() {
        carritoProductoRepository.deleteById(1L);
        Mockito.verify(carritoProductoRepository).deleteById(Mockito.anyLong());
    }

    @Test
    void obtenerProductosDeCarrito() {
        Optional<Carrito> carritoOptional = Optional.of(carrito);

        Mockito.when(carritoRepository.findById(Mockito.anyLong()))
                .thenReturn(carritoOptional);

        List<CarritoProductoResponseDto> response = carritoProductoService.obtenerProductosDeCarrito(1L);

        assertEquals(1,response.size());
        assertEquals("Laptop",response.get(0).getNombreProducto());

        Mockito.verify(carritoRepository).findById(Mockito.anyLong());
    }

    @Test
    void calcularTotalCarrito() {
        Optional<Carrito> carritoOptional = Optional.of(carrito);

        Mockito.when(carritoRepository.findById(Mockito.anyLong()))
                .thenReturn(carritoOptional);

        double expected = 2345.56 * 5;
        double total = carritoProductoService.calcularTotalCarrito(1L);

        assertEquals(expected,total);

        Mockito.verify(carritoRepository).findById(Mockito.anyLong());
    }
}