package com.daddario.facturacion.dto;

import java.util.List;

public record FacturaCreateDTO(
        Long clienteId,
        List<Item> items) {
    public record Item(Long productoId, Integer cantidad) {
    }
}
