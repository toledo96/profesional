package com.techi.microservices.inventory.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "products")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Product {

    @Id
    private String id;

    private Long productId;

    private String name;

    private Integer stock;

    @Version //  clave,  si otro hilo ya actualizó falla automáticamente
    private Integer version;

}
