package com.daddario.facturacion.repository;

import com.daddario.facturacion.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
}
