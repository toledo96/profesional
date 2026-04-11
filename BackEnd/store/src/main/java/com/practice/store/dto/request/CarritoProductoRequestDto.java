package com.practice.store.dto.request;

import com.practice.store.model.Carrito;
import com.practice.store.model.Producto;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CarritoProductoRequestDto {


 /*
   Cuando hablamos de una entidad intermedia como , el cliente necesita indicar a qué carrito y qué producto se refiere.
 */

    private Long carritoId;
    private Long productoId;
    private Integer cantidad;


}
