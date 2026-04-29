package com.practice.store.service.Impl;

import com.practice.store.dto.request.CarritoProductoRequestDto;
import com.practice.store.dto.request.CarritoRequestDto;
import com.practice.store.dto.response.CarritoProductoResponseDto;
import com.practice.store.dto.response.CarritoResponseDto;
import com.practice.store.mapper.CarritoProductoMapper;
import com.practice.store.model.Carrito;
import com.practice.store.model.CarritoProducto;
import com.practice.store.model.Producto;
import com.practice.store.repository.CarritoRepository;
import com.practice.store.repository.ProductoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
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
class CarritoServiceImplTest {

    @InjectMocks
    private CarritoServiceImpl carritoService;

    @Mock
    private CarritoRepository carritoRepository;

    @Mock
    private ProductoRepository productoRepository;


    @Mock
    private RedisTemplate<String, Object> redisTemplate;

    @Mock
    private ValueOperations<String, Object> valueOperations;

    Carrito carrito;

    CarritoResponseDto carritoResponseDto;

    CarritoRequestDto carritoRequestDto;

    @BeforeEach
    void setup(){
        List<CarritoProductoRequestDto> carritoProductoListDto = new ArrayList<>();

        carritoProductoListDto.add(new CarritoProductoRequestDto(1L,1L,5));
        carritoProductoListDto.add(new CarritoProductoRequestDto(2L,2L,7));

        carritoRequestDto = CarritoRequestDto.builder()
                .productos(carritoProductoListDto)
                .build();

        List<CarritoProducto> carritoProductoList = carritoProductoListDto.stream()
                .map(CarritoProductoMapper::toEntity).collect(Collectors.toList());

        carrito = Carrito.builder()
                .idCarrito(1l)
                .productos(carritoProductoList)
                .build();

        List<CarritoProductoResponseDto> carritoProductoListResponse = carritoProductoList.stream()
                .map(CarritoProductoMapper::toDto).collect(Collectors.toList());

        carritoResponseDto = CarritoResponseDto.builder()
                .idCarrito(1L)
                .productos(carritoProductoListResponse)
                .build();
    }

    @Test
    void crearCarrito() {
        String requestId = UUID.randomUUID().toString();

        Mockito.when(redisTemplate.hasKey(requestId)).thenReturn(true);
        Mockito.when(redisTemplate.opsForValue()).thenReturn(valueOperations);
        Mockito.when(valueOperations.get(requestId)).thenReturn(carritoResponseDto);

        CarritoResponseDto response = carritoService.crearCarrito(carritoRequestDto,requestId);

        assertEquals(2,response.getProductos().size());

        Mockito.verify(carritoRepository,Mockito.never()).save(Mockito.any());
    }

    @Test
    void actualizarCarrito() {

        List<CarritoProductoRequestDto> carritoProductoListDto1 = new ArrayList<>();

        carritoProductoListDto1.add(new CarritoProductoRequestDto(1L,1L,5));
        carritoProductoListDto1.add(new CarritoProductoRequestDto(2L,2L,5));

        CarritoRequestDto carritoRequestDto1 = CarritoRequestDto.builder()
                .productos(carritoProductoListDto1)
                .build();

        Optional<Carrito> carritoOptional = Optional.of(carrito);
        Mockito.when(carritoRepository.findById(Mockito.anyLong())).thenReturn(carritoOptional);

        Producto producto = new Producto(1L,"laptop,",1500.00,1);

        Optional<Producto> productoOptional = Optional.of(producto);

        Mockito.when(productoRepository.findById(Mockito.anyLong())).thenReturn(productoOptional);

        Mockito.when(carritoRepository.save(Mockito.any())).thenReturn(carrito);

        CarritoResponseDto carritoResponseDto1 = carritoService.actualizarCarrito(carritoRequestDto1,2L);

        assertNotEquals(carritoRequestDto.getProductos().get(1).getCantidad(),
                carritoResponseDto1.getProductos().get(1).getCantidad());

        Mockito.verify(carritoRepository).save(Mockito.any());
    }

    @Test
    void obtenerCarritos() {
        List<Carrito> carritoResponseDtos = new ArrayList<>();
        carritoResponseDtos.add(carrito);
        Mockito.when(carritoRepository.findAll()).thenReturn(carritoResponseDtos);

        List<CarritoResponseDto> response = carritoService.obtenerCarritos();

        assertEquals(1, response.size());
        Mockito.verify(carritoRepository).findAll();
    }

    @Test
    void obtenerCarrito() {
        Optional<Carrito> carritoOptional = Optional.of(carrito);
        Mockito.when(carritoRepository.findById(Mockito.anyLong())).thenReturn(carritoOptional);

        CarritoResponseDto responseDto = carritoService.obtenerCarrito(1L);

        assertEquals(1L,responseDto.getIdCarrito());
        Mockito.verify(carritoRepository).findById(Mockito.anyLong());
    }

    @Test
    void eliminarCarrito() {
        carritoRepository.deleteById(1L);
        Mockito.verify(carritoRepository).deleteById(Mockito.anyLong());
    }
}