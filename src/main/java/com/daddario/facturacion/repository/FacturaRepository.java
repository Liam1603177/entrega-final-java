package com.daddario.facturacion.repository;

import com.daddario.facturacion.model.Factura;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FacturaRepository extends JpaRepository<Factura, Long> {
}
