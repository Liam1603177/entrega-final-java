package com.daddario.facturacion.controller;

import com.daddario.facturacion.model.Producto;
import com.daddario.facturacion.repository.ProductoRepository;
import jakarta.validation.Valid;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {
    private final ProductoRepository repo;

    public ProductoController(ProductoRepository repo) {
        this.repo = repo;
    }

    @PostMapping
    public ResponseEntity<Producto> crear(@RequestBody @Valid Producto p) {
        return ResponseEntity.status(HttpStatus.CREATED).body(repo.save(p));
    }

    @GetMapping
    public List<Producto> listar() {
        return repo.findAll();
    }
}
