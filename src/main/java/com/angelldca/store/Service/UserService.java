package com.angelldca.store.Service;

import com.angelldca.store.Enties.Menu;
import com.angelldca.store.Enties.Rol;
import com.angelldca.store.Enties.Usuario;
import com.angelldca.store.Helpers.FormatMesseage;
import com.angelldca.store.Helpers.JwtTokenProvider;
import com.angelldca.store.Repository.RolRepository;
import com.angelldca.store.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


import java.util.ArrayList;
import java.util.List;
@Service
public class UserService implements UserDetailsService{
    private final UserRepository userRepository;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    @Autowired
    private RolRepository rolRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;

    }


    public ResponseEntity<Usuario> createUser(Usuario user){
        if(user == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        user.setEstado("ACTIVO");
        Usuario usuario = userRepository.save(user);
        this.loadUserByUsername(usuario.getUsername());
        return ResponseEntity.status(HttpStatus.CREATED).body(usuario);

    }

    public Usuario getUser(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public List<Usuario> listarUser(){
        List<Usuario> usuarios = userRepository.findAll();
        List<Usuario> result = new ArrayList<>();
        for (Usuario u:usuarios ) {
            if( u.getEstado()==null){
                result.add(u);
            } else if (!u.getEstado().equals("INACTIVO") ) {
                result.add(u);
            }


        }
        return result;
    }


    public ResponseEntity<Usuario> actualizarUser(Usuario user, Long id){
        Usuario userDb = getUser(id);

        if (null == userDb){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"No se encuentra el usuario en la base de datos");
        }
        userDb.setNombre(user.getNombre());
        userDb.setApellidos(user.getApellidos());
        userDb.setCi(user.getCi());
        userDb.setApto(user.getApto());
        userDb.setCorreo(user.getCorreo());
        userDb.setCategoria(user.getCategoria());
        userDb.setEdificio(user.getEdificio());
        userDb.setRol(user.getRol());
        userDb.setCorreo(user.getCorreo());
        userDb.setUsername(user.getUsername());
          userRepository.save(userDb);
        return ResponseEntity.status(HttpStatus.OK).body(userDb);

    }

    public ResponseEntity<Usuario> deleteUser(Long id) {
        Usuario userDb = getUser(id);
        if (null == userDb){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"El usuario no se encuentra en la base de datos");
        }
        //platoDb.setStatus("DELETED");
        userRepository.delete(userDb);
        return ResponseEntity.status(HttpStatus.OK).body(userDb);
    }

    public ResponseEntity<Usuario> autenticarUsuario(String username, String password){
        Usuario user = userRepository.findByUsername(username);
        if (user == null ) {
            System.out.println(username);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } else if (user.getEstado().equals("INACTIVO")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }else{
            String hashedPassword = user.getPassword();
            if (!passwordEncoder.matches(password, hashedPassword)) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
            this.loadUserByUsername(username);
            return ResponseEntity.status(HttpStatus.OK).body(user);
        }

    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Usuario usuario = userRepository.findByUsername(username);
        List<Rol> rols = rolRepository.findAll();
        List<GrantedAuthority> authorities  = new ArrayList<>();
        for( Rol rol: rols){
             authorities.add(new SimpleGrantedAuthority(rol.getRolStatus()));
        }
        UserDetails userDetails = new User(usuario.getUsername(),usuario.getPassword(),authorities);

        return userDetails;
    }
    public ResponseEntity<Usuario> makeAsInactivo (Long id){
        Usuario usuario = getUser(id);
        if (null == usuario){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"El usuario no se encuentra en la base de datos");
        }
        usuario.setEstado("INACTIVO");
        Usuario user = userRepository.save(usuario);
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }
/*
*
*   String token = jwtTokenProvider.resolveToken(request);
        if (token != null && jwtTokenProvider.validateToken(token)) {
            String username = jwtTokenProvider.getUsernameFromToken(token);
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            // Hacer algo con los detalles del usuario
             Usuario usuario = userRepository.findByUsername(username);
             if(usuario != null)
            return ResponseEntity.status(HttpStatus.OK).body(usuario);

            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
* */



}
