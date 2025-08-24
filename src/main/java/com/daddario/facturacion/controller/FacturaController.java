package com.daddario.facturacion.controller;

import com.daddario.facturacion.dto.FacturaCreateDTO;
import com.daddario.facturacion.dto.FacturaResponseDTO;
import com.daddario.facturacion.service.FacturaService;
import jakarta.validation.Valid;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/facturas")
public class FacturaController {
    private final FacturaService service;

    public FacturaController(FacturaService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<FacturaResponseDTO> crear(@RequestBody @Valid FacturaCreateDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.crearYDevolverDTO(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<FacturaResponseDTO> get(@PathVariable Long id) {
        return service.buscarDTO(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public List<FacturaResponseDTO> listar() {
        return service.listarDTO();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> borrar(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
