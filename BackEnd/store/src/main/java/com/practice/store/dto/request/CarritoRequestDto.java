package com.practice.store.dto.request;

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
public class CarritoRequestDto {

    private long idCarrito;

    private List<CarritoProducto> productos = new ArrayList<>();

}
