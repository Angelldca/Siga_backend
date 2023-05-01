package com.angelldca.store.Controller;


import com.angelldca.store.Enties.Menu;
import com.angelldca.store.Helpers.FormatMesseage;
import com.angelldca.store.Service.MenuService;
import com.angelldca.store.Service.dto.MenuPlatosDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;


import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/menu")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class MenuController {

    private final MenuService menuService;
    private FormatMesseage formatMesseage;
    public MenuController(MenuService menuService) {
        this.menuService = menuService;
        this.formatMesseage = new FormatMesseage();
    }



    @PostMapping(value = "/createMenu")
    public ResponseEntity<Menu> createMenu(@Valid  @RequestBody Menu menu, BindingResult result ){
        if (result.hasErrors()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, formatMesseage.formatMessage(result));
        }

        return menuService.createMenu(menu);
    }

    @GetMapping
    public List<MenuPlatosDTO> listarMenu(){
        return menuService.listarMenuPlatosActivos();
    }
    @GetMapping("/todos")
    public List<MenuPlatosDTO> listarMenuTodos(){
        return menuService.listarMenuTodos();
    }
    @PostMapping("/menusIds")
    public List<MenuPlatosDTO> listarMenuPorId( @RequestBody Long [] id){
        return menuService.listMenusPorIds(id);
    }
    //actualizar menu
    @PutMapping(value = "/updateMenu/{id}")
    public ResponseEntity<MenuPlatosDTO> updateMenu(@Valid @PathVariable("id") Long id, @RequestBody  Menu menu,BindingResult result){
        if (result.hasErrors()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, formatMesseage.formatMessage(result));
        }
         return menuService.actualizarMenu(menu,id);
    }
    //Eliminar menu
    @DeleteMapping(value = "/deleteMenu/{id}")
    public ResponseEntity<Menu> deleteMenu (@PathVariable("id") Long id){

        return menuService.deleteMenu(id);
    }
}
