package com.angelldca.store.Service;

import com.angelldca.store.Enties.Factura;
import com.angelldca.store.Enties.Usuario;
import com.angelldca.store.Repository.FacturaRepository;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class FacturaService {
    private final FacturaRepository facturaRepository;

    public FacturaService(FacturaRepository facturaRepository) {
        this.facturaRepository = facturaRepository;
    }

    public Factura createFactura(Factura factura){
        return facturaRepository.save(factura);
    }
    public List<Factura> listarFacturas(){
        return facturaRepository.findAll();
    }
    public List<Factura> listarFacturasUsuario(Usuario usuario){
        return facturaRepository.findByUsuario(usuario);
    }

    public float importeTotal() {
        List<Factura> listarFacturas = this.listarFacturas();
        float total = 0.00f;
        for (Factura f: listarFacturas ) {
            total += f.getReserva().getPrecio();
        }
        return total;
    }
}
