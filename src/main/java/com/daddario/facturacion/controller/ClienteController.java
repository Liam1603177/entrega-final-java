package com.daddario.facturacion.controller;

import com.daddario.facturacion.model.Cliente;
import com.daddario.facturacion.repository.ClienteRepository;
import jakarta.validation.Valid;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {
    private final ClienteRepository repo;

    public ClienteController(ClienteRepository repo) {
        this.repo = repo;
    }

    @PostMapping
    public ResponseEntity<Cliente> crear(@RequestBody @Valid Cliente c) {
        return ResponseEntity.status(HttpStatus.CREATED).body(repo.save(c));
    }

    @GetMapping
    public List<Cliente> listar() {
        return repo.findAll();
    }
}
