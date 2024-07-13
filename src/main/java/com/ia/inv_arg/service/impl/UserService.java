package com.ia.inv_arg.service.impl;

import com.ia.inv_arg.DTO.entrada.LoginEntradaDto;
import com.ia.inv_arg.DTO.entrada.UserAdminEntradaDto;
import com.ia.inv_arg.DTO.entrada.UserDTO;
import com.ia.inv_arg.DTO.modificacion.UserModificacionEntradaDTO;
import com.ia.inv_arg.DTO.salida.TokenSalidaDto;
import com.ia.inv_arg.DTO.salida.UserDtoSalida;
import com.ia.inv_arg.entity.Role;
import com.ia.inv_arg.entity.User;
import com.ia.inv_arg.exception.ResourceNotFoundException;
import com.ia.inv_arg.repository.RolRepository;
import com.ia.inv_arg.repository.UserRepository;
import com.ia.inv_arg.service.IUserService;
import com.ia.inv_arg.utils.JsonPrinter;
import com.ia.inv_arg.utils.RoleConstants;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.hibernate.Hibernate;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService implements IUserService, UserDetailsService {

    private final Logger LOGGER = LoggerFactory.getLogger(UserService.class);
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EntityManager entityManager;
    @Autowired
    private RolRepository rolRepository;
    private ModelMapper modelMapper;

//    private JwtService jwtService;
    public UserService(UserRepository userRepository, ModelMapper modelMapper){
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        configureMapping();
    }
    @Transactional
    @Override
    public List<UserDtoSalida> listarUsuarios() {
        List<UserDtoSalida> usuarioSalidaDto = userRepository.findAll()
                .stream()
                .map(usuario -> modelMapper.map(usuario, UserDtoSalida.class))
                .toList();
        return usuarioSalidaDto;
    }
    @Transactional
    @Override
    public TokenSalidaDto registrarUsuario(UserDTO usuario) {
        if (userRepository.existsByUsername(usuario.getUsername())) {
            throw new RuntimeException("El username ya se encuentra registrado");
        }

        LOGGER.info("UsuarioEntradaDto: " + JsonPrinter.toString(usuario));

        // Mapea el DTO de entrada a la entidad User
        User usuarioEntidad = modelMapper.map(usuario, User.class);

        // Asigna el rol ADMIN al usuario
        Role userRole = rolRepository.findByRol(RoleConstants.USER)
                .orElseGet(() -> new Role(RoleConstants.USER));

        if (userRole.getIdRol() == null) {
            userRole = rolRepository.save(userRole);  // Guarda el rol si no existe
        }

        // Asegúrate de que el rol esté gestionado por el EntityManager
        userRole = entityManager.merge(userRole);

        Set<Role> roles = new HashSet<>();
        roles.add(userRole);
        usuarioEntidad.setRoles(roles);

        // Guarda el usuario en el repositorio
        User usuarioAPersistir = userRepository.save(usuarioEntidad);

        TokenSalidaDto tokenSalidaDto = modelMapper.map(usuarioAPersistir, TokenSalidaDto.class);

        // Crea y retorna el DTO de salida con el token
        new TokenSalidaDto(
                usuarioAPersistir.getId(),
                usuarioAPersistir.getUsername(),
                usuarioAPersistir.getLastname(),
                usuarioAPersistir.getEmail(),
                usuarioAPersistir.getFirstname(),
                usuarioAPersistir.getArticles(),
                usuarioAPersistir.getFavourites(),
                new ArrayList<>(usuarioAPersistir.getRoles())
        );

        return tokenSalidaDto;
    }

    @Override
    @Transactional
    public UserDtoSalida buscarUsuarioPorId(Long id) {
        User usuarioBuscado = userRepository.findById(id).orElse(null);

        if (usuarioBuscado == null) {
            LOGGER.error("El id no se encuentra registrado en la base de datos");
            return null;
        }

        // Inicializa las colecciones perezosas
        Hibernate.initialize(usuarioBuscado.getArticles());

        // Mapea el usuario a su DTO
        UserDtoSalida usuarioEncontrado = modelMapper.map(usuarioBuscado, UserDtoSalida.class);

        return usuarioEncontrado;
    }

    @Override
    public void eliminarUsuario(Long id) {
        if (userRepository.findById(id).orElse(null) != null) {
            userRepository.deleteById(id);
            LOGGER.warn("se ha eliminado el usuario con id: {}", id);
        }
    }

//    @Override
    public UserDtoSalida actualizarUsuario(UserModificacionEntradaDTO usuario) {
        User usuarioRecibido = modelMapper.map(usuario, User.class);
        User usuarioAActualizar = userRepository.findById(usuarioRecibido.getId()).orElse(null);

        UserDtoSalida userDtoSalida = null;
        if(usuarioAActualizar != null) {
            usuarioAActualizar = usuarioRecibido;
            userRepository.save(usuarioAActualizar);

            userDtoSalida = modelMapper.map(usuarioAActualizar, UserDtoSalida.class);
            LOGGER.warn("Usuario actualizado: {}", JsonPrinter.toString(userDtoSalida));
        }else{
            LOGGER.error("No fue posible actualizar el usuario porque no se encontro en la base de datos");
        }

        return userDtoSalida;
    }

//    @Override
    public UserDtoSalida buscarUsuarioPorEmail(String email) {
        return modelMapper.map(userRepository.findByEmail(email), UserDtoSalida.class);
    }

    @Transactional
    @Override
    public TokenSalidaDto registrarAdmin(UserAdminEntradaDto userAdminEntradaDto) {
        if (userRepository.existsByUsername(userAdminEntradaDto.getUsername())) {
            throw new RuntimeException("El username ya se encuentra registrado");
        }

        LOGGER.info("UsuarioEntradaDto: " + JsonPrinter.toString(userAdminEntradaDto));

        // Mapea el DTO de entrada a la entidad User
        User usuarioEntidad = modelMapper.map(userAdminEntradaDto, User.class);

        // Asigna el rol ADMIN al usuario
        Role adminRole = rolRepository.findByRol(RoleConstants.ADMIN)
                .orElseGet(() -> new Role(RoleConstants.ADMIN));

        if (adminRole.getIdRol() == null) {
            adminRole = rolRepository.save(adminRole);  // Guarda el rol si no existe
        }

        // Asegúrate de que el rol esté gestionado por el EntityManager
        adminRole = entityManager.merge(adminRole);

        Set<Role> roles = new HashSet<>();
        roles.add(adminRole);
        usuarioEntidad.setRoles(roles);

        // Guarda el usuario en el repositorio
        User usuarioAPersistir = userRepository.save(usuarioEntidad);

        TokenSalidaDto tokenSalidaDto = modelMapper.map(usuarioAPersistir, TokenSalidaDto.class);

        // Crea y retorna el DTO de salida con el token
                 new TokenSalidaDto(
                usuarioAPersistir.getId(),
                usuarioAPersistir.getUsername(),
                usuarioAPersistir.getLastname(),
                usuarioAPersistir.getEmail(),
                usuarioAPersistir.getFirstname(),
                new ArrayList<>(usuarioAPersistir.getRoles())
        );

        return tokenSalidaDto;
    }






    private void configureMapping() {
        modelMapper.typeMap(UserDTO.class, User.class)
                .addMappings(mapper -> mapper.map(UserDTO::getArticlesDto, User::setArticles));
        modelMapper.typeMap(User.class, UserDtoSalida.class)
                .addMappings(mapper -> mapper.map(User::getArticles, UserDtoSalida::setArticlesSalida));
        modelMapper.typeMap(User.class, TokenSalidaDto.class)
                .addMappings(mapper->mapper.map(User::getArticles, TokenSalidaDto::setArticlesSalida));
        modelMapper.typeMap(UserModificacionEntradaDTO.class, User.class)
                .addMappings(mapper -> mapper.map(UserModificacionEntradaDTO::getArticlesModificaionEntradaDto, User::setArticles));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findUserByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("El usuario " + username + " no existe"));

        List<SimpleGrantedAuthority> authorityList = new ArrayList<>();
        user.getRoles().forEach(role -> authorityList.add(new SimpleGrantedAuthority("ROLE_" + role.getRol())));
        user.getRoles().stream()
                .flatMap(role -> role.getPermisosList().stream())
                .forEach(permission -> authorityList.add(new SimpleGrantedAuthority(permission.getName())));

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                user.isAccountNonLocked(),
                user.isEnabled(),
                user.isAccountNonLocked(),
                user.isAccountNonExpired(),
                authorityList
        );
    }
}
