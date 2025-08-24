package com.daddario.facturacion.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;
import lombok.*;
import java.math.BigDecimal;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class DetalleFactura {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    private Factura factura;

    @ManyToOne(optional = false)
    private Producto producto;

    @Positive
    private Integer cantidad;
    @Positive
    private BigDecimal precioUnitario;
    @Positive
    private BigDecimal subtotal;
}
