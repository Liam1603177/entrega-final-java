package com.daddario.facturacion.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record FacturaResponseDTO(
        Long id,
        LocalDateTime fecha,
        Cliente cliente,
        BigDecimal total,
        List<Item> items) {
    public record Cliente(Long id, String nombre, String email) {
    }

    public record Item(Long id, Long productoId, String productoNombre,
            Integer cantidad, BigDecimal precioUnitario, BigDecimal subtotal) {
    }
}
