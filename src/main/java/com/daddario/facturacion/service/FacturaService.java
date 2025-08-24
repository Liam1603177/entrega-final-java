package com.daddario.facturacion.service;

import com.daddario.facturacion.dto.FacturaCreateDTO;
import com.daddario.facturacion.exception.NotFoundException;
import com.daddario.facturacion.model.*;
import com.daddario.facturacion.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.util.*;

@Service
public class FacturaService {
    private final FacturaRepository facturaRepo;
    private final ClienteRepository clienteRepo;
    private final ProductoRepository productoRepo;

    public FacturaService(FacturaRepository f, ClienteRepository c, ProductoRepository p) {
        this.facturaRepo = f;
        this.clienteRepo = c;
        this.productoRepo = p;
    }

    @Transactional
    public Factura crear(FacturaCreateDTO dto) {
        var cliente = clienteRepo.findById(dto.clienteId())
                .orElseThrow(() -> new NotFoundException("Cliente no encontrado"));

        var factura = new Factura();
        factura.setCliente(cliente);

        for (var it : dto.items()) {
            var prod = productoRepo.findById(it.productoId())
                    .orElseThrow(() -> new NotFoundException("Producto no encontrado: " + it.productoId()));

            var detalle = new DetalleFactura();
            detalle.setProducto(prod);
            detalle.setCantidad(it.cantidad());
            detalle.setPrecioUnitario(prod.getPrecio());
            detalle.setSubtotal(prod.getPrecio().multiply(BigDecimal.valueOf(it.cantidad())));

            factura.agregarDetalle(detalle);
        }
        return facturaRepo.save(factura);
    }

    public Optional<Factura> buscar(Long id) {
        return facturaRepo.findById(id);
    }

    public List<Factura> listar() {
        return facturaRepo.findAll();
    }

    public void eliminar(Long id) {
        facturaRepo.deleteById(id);
    }

    private com.daddario.facturacion.dto.FacturaResponseDTO toDTO(Factura f) {
        var total = f.getDetalles().stream()
                .map(DetalleFactura::getSubtotal)
                .reduce(java.math.BigDecimal.ZERO, java.math.BigDecimal::add);

        var items = f.getDetalles().stream().map(d -> new com.daddario.facturacion.dto.FacturaResponseDTO.Item(
                d.getId(),
                d.getProducto().getId(),
                d.getProducto().getNombre(),
                d.getCantidad(),
                d.getPrecioUnitario(),
                d.getSubtotal())).toList();

        var cli = new com.daddario.facturacion.dto.FacturaResponseDTO.Cliente(
                f.getCliente().getId(),
                f.getCliente().getNombre(),
                f.getCliente().getEmail());

        return new com.daddario.facturacion.dto.FacturaResponseDTO(
                f.getId(),
                f.getFecha(),
                cli,
                total,
                items);
    }

    public com.daddario.facturacion.dto.FacturaResponseDTO crearYDevolverDTO(FacturaCreateDTO dto) {
        var f = crear(dto);
        return toDTO(f);
    }

    public Optional<com.daddario.facturacion.dto.FacturaResponseDTO> buscarDTO(Long id) {
        return facturaRepo.findById(id).map(this::toDTO);
    }

    public List<com.daddario.facturacion.dto.FacturaResponseDTO> listarDTO() {
        return facturaRepo.findAll().stream().map(this::toDTO).toList();
    }
}
