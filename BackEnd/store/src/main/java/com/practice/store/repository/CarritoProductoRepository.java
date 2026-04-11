package com.practice.store.repository;

import com.practice.store.model.CarritoProducto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarritoProductoRepository extends JpaRepository<CarritoProducto,Long> {
}
