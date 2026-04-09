package com.practice.store.dto.response;

import com.practice.store.model.CarritoProducto;
import jakarta.persistence.CascadeType;
import jakarta.persistence.OneToMany;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CarritoResponseDto {

    private long idCarrito;

    @OneToMany(mappedBy = "carrito", cascade = CascadeType.ALL)
    private List<CarritoProducto> productos = new ArrayList<>();
}
