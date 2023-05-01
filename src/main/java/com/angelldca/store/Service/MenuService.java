package com.angelldca.store.Service;

import com.angelldca.store.Enties.Menu;
import com.angelldca.store.Repository.MenuRepository;
import com.angelldca.store.Service.dto.MenuPlatosDTO;
import com.angelldca.store.mapper.MenuDtoToMenuPlato;
import com.angelldca.store.mapper.ReservaDtoToReserva;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Service
public class MenuService {


    private final MenuRepository menuRepository;
    private final MenuDtoToMenuPlato mapper;
    public MenuService(MenuRepository menuRepository, MenuDtoToMenuPlato mapper) {
        this.menuRepository = menuRepository;
        this.mapper = mapper;
    }


    //Crear menu
    public ResponseEntity<Menu> createMenu(Menu menu){

        List<Menu> listmenu = listarMenu();
        Date dt =menu.getFecha();
        Date tomorrow = new Date(dt.getTime() + (1000 * 60 * 60 * 24));
        menu.setFecha(tomorrow);
        System.out.println(menu.getFecha());
        for ( Menu m: listmenu) {
            if(m.getEvento().equals(menu.getEvento()) & m.getFecha().compareTo(menu.getFecha()) == 0){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El menu ya esta planificado para ese dia");
            }
        }
        menuRepository.save(menu);
        return ResponseEntity.status(HttpStatus.CREATED).body(menu);

    }

    public Menu getMenu(Long id) {
        return menuRepository.findById(id).orElse(null);
    }
   
    public List<Menu> listarMenu(){

        return menuRepository.findAll();
    }
 public List<MenuPlatosDTO> listarMenuTodos(){
     List<Menu> list = menuRepository.findAll();
     List<MenuPlatosDTO>  listResult = new ArrayList<>();

     for(Menu menu :list) {
         listResult.add(mapper.map(menu));
     }
     return listResult;
 }
 public List<MenuPlatosDTO> listMenusPorIds(Long [] id){

     List<MenuPlatosDTO>  listResult = new ArrayList<>();
     Menu m;
     for (int i=0;i< id.length;i++){
         m = getMenu(id[i]);
         if(m != null)
         listResult.add(mapper.map(m));
     }
     return listResult;
 }

    public List<MenuPlatosDTO> listarMenuPlatosActivos(){
        List<Menu> list = menuRepository.findAll();
        List<MenuPlatosDTO>  listResult = new ArrayList<>();

        for(Menu menu :list) {

            if(menu.getFecha().compareTo(new Date()) > 0){
                listResult.add(mapper.map(menu));
            }else{
                //poner el estado del menu en Inactivo
                menuRepository.markAsInactive(menu.getId_menu());
            }

        }

       if(listResult.size() == 0) return null;

        return listResult;
    }

    public ResponseEntity<MenuPlatosDTO> actualizarMenu(Menu menu, Long id){
        Menu menuDb = getMenu(id);

        if (null == menuDb){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"No se encuentra el Menu en la base de datos");
        }
        menuDb.setEvento(menu.getEvento());
        menuDb.setFecha(menu.getFecha());
        menuDb.setPrecio(menu.getPrecio());
        menuDb.setId_plato(menu.getId_plato());
        menuRepository.save(menuDb);
        return ResponseEntity.status(HttpStatus.OK).body(mapper.map(menuDb));

    }

    public ResponseEntity<Menu> deleteMenu(Long id) {
        Menu menuDb = getMenu(id);
        if (null == menuDb){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"No se encuentra el Menu en la base de datos");
        }
        //platoDb.setStatus("DELETED");
        menuRepository.delete(menuDb);
        return ResponseEntity.status(HttpStatus.OK).body(menuDb);
    }
    public ResponseEntity<Menu> deleteMenuToInactive(Long id) {
        Menu menuDb = getMenu(id);
        if (null == menuDb){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"No se encuentra el Menu en la base de datos");
        }
        //Poner el menu en inactivo
        menuRepository.markAsInactive(menuDb.getId_menu());
        return ResponseEntity.status(HttpStatus.OK).body(menuDb);
    }
}
