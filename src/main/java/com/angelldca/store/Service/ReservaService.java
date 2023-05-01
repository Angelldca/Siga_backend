package com.angelldca.store.Service;

import com.angelldca.store.Enties.Factura;
import com.angelldca.store.Enties.Reserva;
import com.angelldca.store.Enties.Usuario;
import com.angelldca.store.Repository.FacturaRepository;
import com.angelldca.store.Repository.ReservaRepository;
import com.angelldca.store.Service.dto.ReservaDto;
import com.angelldca.store.Service.dto.ReservaDtoMenu;
import com.angelldca.store.mapper.ReservaDtoToReserva;
import com.angelldca.store.mapper.ReservaToReservaDtoMenu;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ReservaService {
    private final ReservaRepository reservaRepository;
    private final ReservaDtoToReserva mapper;
    private final FacturaRepository facturaRepository;
    private final ReservaToReservaDtoMenu mapperReservaMenu;
    public ReservaService(ReservaRepository reservaRepository, ReservaDtoToReserva reservaDto, FacturaRepository facturaRepository, ReservaToReservaDtoMenu mapperReservaMenu) {
        this.reservaRepository = reservaRepository;
        this.mapper = reservaDto;

        this.facturaRepository = facturaRepository;
        this.mapperReservaMenu = mapperReservaMenu;
    }


    //Crear reserva
    public ResponseEntity<Reserva> createReserva(ReservaDto reservaDto){

        Reserva reserva = mapper.map(reservaDto);
        List<Reserva> reservas = reservaRepository.findAll();
        for(Reserva r :reservas) {

            if(r.getMenu().getId_menu()==reserva.getMenu().getId_menu() & r.getUsuarioReserva().getId_usuario()==reserva.getUsuarioReserva().getId_usuario()){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Ya se realizó una reserva de este menu");
                //return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(reserva);
            }

        }
        Reserva reserva1 = reservaRepository.save(reserva);
        Factura factura = new Factura();
        factura.setReserva(reserva1);
        factura.setEstado("PENDIENTE");
        factura.setUsuario(reserva1.getUsuarioReserva());
        facturaRepository.save(factura);
        return ResponseEntity.status(HttpStatus.CREATED).body(reserva);

    }

    public Reserva getReserva(Long id) {
        return reservaRepository.findById(id).orElse(null);
    }
    public List<Reserva> listarReservas(){
        return reservaRepository.findAll();
    }


    public ResponseEntity<Reserva> actualizarReserva(ReservaDto reserva, Long id){
        Reserva reservaDb = getReserva(id);
        Reserva reserva1 = mapper.map(reserva);

        if (null == reservaDb){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"No se encuentra el Menu en la base de datos");
        }

        Factura f = facturaRepository.findByReserva(reservaDb);
        reservaDb.setId_platosMenu(reserva.getId_platosMenu());
        reservaDb.setPrecio(reserva1.getPrecio());
        f.setReserva(reservaRepository.save(reservaDb));
        facturaRepository.save(f);
        return ResponseEntity.status(HttpStatus.OK).body(reservaDb);

    }

    public ResponseEntity<Reserva> deleteReserva(Long id) {
        Reserva reservaDb = getReserva(id);
        if (null == reservaDb){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"No se encuentra la Reserva en la base de datos");
        }
        if(reservaDb.getEstado().equals("TERMINADA"))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Ya pasó la fecha de la reserva base de datos");
        reservaRepository.delete(reservaDb);
        return ResponseEntity.status(HttpStatus.OK).body(reservaDb);
    }
    public ResponseEntity<Reserva> deleteReservaAdmin(Long id) {
        Reserva reservaDb = getReserva(id);
        if (null == reservaDb){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"No se encuentra la Reserva en la base de datos");
        }
        Factura factura = facturaRepository.findByReserva(reservaDb);
        facturaRepository.delete(factura);
        reservaRepository.delete(reservaDb);
        return ResponseEntity.status(HttpStatus.OK).body(reservaDb);
    }
    /// mostrar reserva por fecha
    public List<Reserva> findAllByFecha(Date fechaInicio, Date fechaFin){
        return reservaRepository.findByFecha_reserva(fechaInicio,fechaFin);
    }
    /// mostrar reserva por fecha y plato
    public List<Reserva> findAllByFechaPlato(Date fechaInicio, Date fechaFin, Long id_plato){
         List<Reserva> reservas = this.findAllByFecha(fechaInicio,fechaFin);
         List<Reserva> result = new ArrayList<>();
        for (Reserva r: reservas) {
            for (Long id: r.getId_platosMenu()) {
                if(id.compareTo(id_plato)== 0)
                    result.add(r);
            }


        }

        return result;
    }
    public List<Integer> reservasDias(Date fechaInicio, Date fechaFin, Long id_plato){
        List<Reserva> reservas = findAllByFechaPlato(fechaInicio,fechaFin,id_plato);
        List<Integer> result = new ArrayList<>();

        for(int i =1;i<= 31;i++){
            int contador = 0;
            for (Reserva r: reservas) {
                if(r.getFecha_reserva().getDate() == i){
                    System.out.println(r.getFecha_reserva().getDate());
                    contador++;

                }

            }
            result.add(contador);
        }

           return result;
    }
    public List<ReservaDtoMenu> findByUsuarioReserva (Usuario user){
        List<Reserva> list = reservaRepository.findByUsuarioReserva(user);
        List<ReservaDtoMenu> listReservaDto = new ArrayList<>();
        Date now  = new Date();
        for (Reserva r: list ) {
            if (r.getFecha_reserva().compareTo(now) ==1 )
            listReservaDto.add(mapperReservaMenu.map(r));
        }
        return listReservaDto;
    }
}
