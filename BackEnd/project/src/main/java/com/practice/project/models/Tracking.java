package com.practice.project.models;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "tracking")
@Data
@Builder
public class Tracking {

    private Long PedidoId;

    private String status;

}
