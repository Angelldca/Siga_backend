package com.angelldca.store.Controller;


import com.angelldca.store.Enties.Factura;
import com.angelldca.store.Enties.Usuario;
import com.angelldca.store.Service.FacturaService;
import com.angelldca.store.Service.PlatoService;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(value = "/factura")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class FacturaController {
     private final FacturaService facturaService;
    private final PlatoService platoService;
    private  ListarFacturas listarFacturas;

    public FacturaController(FacturaService facturaService, PlatoService platoService) {
        this.facturaService = facturaService;
        this.platoService = platoService;
        this.listarFacturas = new ListarFacturas(platoService);
    }
    @GetMapping
    public List<Factura> listarFacturas(){
        return facturaService.listarFacturas();
    }
    @PostMapping("/user")
    public List<Factura> listarFacturasUsuario(@RequestBody Usuario usuario){
        return facturaService.listarFacturasUsuario(usuario);
    }
    @PostMapping("/exportPdf")
    public ResponseEntity<Resource> generarFactura(@RequestBody Factura factura) throws IOException {
             // Convertir el archivo a un recurso de Spring
            ByteArrayResource resource = listarFacturas.exportarPdfFactura(factura);
            return ResponseEntity.ok().body(resource);

    }

    @GetMapping("/cantidad")
    public int cantFacturas(){
        return facturaService.listarFacturas().size();
    }
    @GetMapping("/importe")
    public float importeTotal(){
        return facturaService.importeTotal();
    }
}
