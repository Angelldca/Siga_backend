package com.angelldca.store;





import com.angelldca.store.Enties.Menu;
import com.angelldca.store.Enties.Plato;
import com.angelldca.store.Service.MenuService;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;


@SpringBootTest
public class ProductRepositoryMoskTest {
    @Autowired
     private MenuService menuService;
    @Test
    public void integrarPlatoMenuDB(){
         Plato [] platos = new Plato[3];
         platos[0] =Plato.builder().id_plato(1L).build();
         platos[1] = Plato.builder().id_plato(2L).build();
         platos[2] = Plato.builder().id_plato(3L).build();
        Long [] l = new Long[3];
        l[0] = 1L;
        l[1] = 2L;
        l[2] = 3L;
   Menu menu = Menu.builder()

                  .evento("almuerzo")
                  .fecha(new Date())
                 .precio(Double.parseDouble("1240.99"))
                 .id_plato(l).build();
        menuService.createMenu(menu);

     // List<Menu> found = menuService.;
      //  Assertions.assertEquals(found.size(),3);



     //String body = this.restTemplate.getForObject("/", String.class);
    // Assertions.assertThat(body).isEqualTo("Hello World");

    }
}
