package com.techi.microservices.inventory.repository;

import com.techi.microservices.inventory.model.ProcessedEvent;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface ProcessedEventRepository extends MongoRepository<ProcessedEvent, String> {

    Optional<ProcessedEvent> findByEventId(String eventId);
}