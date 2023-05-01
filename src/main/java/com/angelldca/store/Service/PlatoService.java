package com.angelldca.store.Service;


import com.angelldca.store.Enties.Menu;
import com.angelldca.store.Enties.Plato;
import com.angelldca.store.Repository.MenuRepository;
import com.angelldca.store.Repository.PlatoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class PlatoService {
    private final PlatoRepository platoRepository;
    private final MenuRepository menuRepository;

    public PlatoService(PlatoRepository platoRepository, MenuRepository menuRepository) {
        this.platoRepository = platoRepository;
        this.menuRepository = menuRepository;
    }

    public Plato getPlato(Long id) {
        return platoRepository.findById(id).orElse(null);
    }
    public Plato findByNombre_plato(String plato) {
        return platoRepository.selectPlatoByName(plato);
    }

    public ResponseEntity<Plato> createPlato(Plato plato){
        platoRepository.save(plato);
        return ResponseEntity.status(HttpStatus.CREATED).body(plato);
    }
    public List<Plato> listarPlatos(){

        return platoRepository.findAll();
    }
    //actualizar platos
    public ResponseEntity<Plato> actualizarPlato(Plato plato, Long id){
        Plato platoDb = getPlato(id);
        if (null == platoDb){
          throw new ResponseStatusException(HttpStatus.NOT_FOUND,"No se encuentra el plato en la base de datos");
        }
        platoDb.setNombre_plato(plato.getNombre_plato());
        platoDb.setGramaje(plato.getGramaje());
        platoDb.setPrecio_plato(plato.getPrecio_plato());

        platoRepository.save(platoDb);

        return ResponseEntity.status(HttpStatus.OK).body(platoDb);

    }

    public ResponseEntity<Plato> deletePlato(Long id) {
        if (null == id){
            return null;
        }
        Plato plato = this.getPlato(id);
        if (plato == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"No se encuentra el plato en la base de datos");
        }
         List<Menu> menuList = menuRepository.findAll();
        for ( Menu m: menuList) {
            for (Long id_plato : m.getId_plato()){
                if(id_plato.equals(id)){
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El plato está en la planificación de algún menú");
                }
            }

        }
         platoRepository.deleteById(id);

        return ResponseEntity.status(HttpStatus.OK).body(plato);
    }
}
