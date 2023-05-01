package com.angelldca.store.Controller;


import com.angelldca.store.Enties.Factura;
import com.angelldca.store.Enties.Plato;
import com.angelldca.store.Enties.Reserva;
import com.angelldca.store.Enties.Usuario;
import com.angelldca.store.Repository.PlatoRepository;
import com.angelldca.store.Service.PlatoService;
import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.core.io.ByteArrayResource;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class ListarFacturas  {


       private  PlatoService platoService;

    public ListarFacturas(PlatoService platoService) {
        this.platoService = platoService;
    }

    public ByteArrayResource exportarPdfFactura(Factura factura){

        Usuario usuario = factura.getUsuario();
        Reserva reserva = factura.getReserva();
        Document document = new Document(PageSize.A4);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        List<Plato> platos = new ArrayList<>();
        for(int i = 0;i< reserva.getId_platosMenu().length;i++){
            platos.add(platoService.getPlato(reserva.getId_platosMenu()[i]));
        }



        try {
            //new FileOutputStream("factura.pdf")
            PdfWriter.getInstance(document, baos);
        } catch (DocumentException e) {
            throw new RuntimeException(e);
        }
        document.open();
        Paragraph paragraph = new Paragraph();
        paragraph.add("Factura");
        paragraph.add("\n\n");
        paragraph.add("Número de factura: "+ factura.getId_factura());
        paragraph.add("                              ---");
        paragraph.add("Fecha: "+ reserva.getFecha_reserva());
        paragraph.add("\n\n");
        paragraph.add("Estado: "+ factura.getEstado());
        paragraph.add("\n\n");
        paragraph.add("Menu: ");
        paragraph.add("\n");
        PdfPTable tabla = new PdfPTable(3);
        tabla.addCell(new PdfPCell(new Phrase("Platos del menú")));
        tabla.addCell(new PdfPCell(new Phrase("Gramaje")));
        tabla.addCell(new PdfPCell(new Phrase("Precio")));

        for(int i = 0;i< platos.size();i++){
            tabla.addCell(new PdfPCell(new Phrase(platos.get(i).getNombre_plato())));
            tabla.addCell(new PdfPCell(new Phrase(platos.get(i).getGramaje())));
            tabla.addCell(new PdfPCell(new Phrase("$ "+platos.get(i).getPrecio_plato()+"")));
           // paragraph.add(platos.get(i).getNombre_plato());
          //  paragraph.add("\n");
        }
        paragraph.add(tabla);
        paragraph.add("\n\n");
        paragraph.add("Importe Total :     $ "+reserva.getPrecio() );
        paragraph.add("\n\n");
        paragraph.add("Usuario :    ");
        paragraph.add(usuario.getNombre()+" "+ usuario.getApellidos());
        paragraph.add("\n");
        paragraph.add("Correo :    ");
        paragraph.add(usuario.getCorreo());
        paragraph.add("\n");
        paragraph.add("Carnet de Identidad :    ");
        paragraph.add(usuario.getCi());
        paragraph.add("\n");
        paragraph.add("Categoría :    ");
        paragraph.add(usuario.getCategoria());
        paragraph.add("\n");


        document.add(paragraph);
        document.close();

        ByteArrayResource resource = new ByteArrayResource(baos.toByteArray());
       return resource;
    }

}
