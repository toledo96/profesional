package com.techi.microservices.inventory.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;


/*
* Clase para implementar indeponencia en eventos
* */
@Document(collection = "processed_events")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProcessedEvent {
    @Id
    private String id;

    @Indexed(unique = true) // evita duplicados a nivel DB
    private String eventId;

    private Long orderId;
}
