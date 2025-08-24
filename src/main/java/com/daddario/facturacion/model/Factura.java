package com.daddario.facturacion.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Factura {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime fecha = LocalDateTime.now();

    @ManyToOne(optional = false)
    private Cliente cliente;

    @OneToMany(mappedBy = "factura", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DetalleFactura> detalles = new ArrayList<>();

    public void agregarDetalle(DetalleFactura d) {
        d.setFactura(this);
        detalles.add(d);
    }
}
