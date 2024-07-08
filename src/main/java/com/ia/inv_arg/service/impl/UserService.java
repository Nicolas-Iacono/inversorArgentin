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
import jakarta.transaction.Transactional;
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
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService implements IUserService {

    private final Logger LOGGER = LoggerFactory.getLogger(UserService.class);
    @Autowired
    private UserRepository userRepository;
//    private AuthenticationManager authenticationManager;
//    private JwtService jwtService;
//    @Autowired
//    private PasswordEncoder passwordEncoder;
//    @Autowired
//    private RolRepository rolRepository;
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

    @Override
    public UserDtoSalida registrarUsuario(UserDTO usuario) {

        if(userRepository.existsByUsername(usuario.getUsername())){
            throw new RuntimeException("el username ya se encuentra registrado");
        }
        //convertimos mediante el mapper de dtoEntrada a entidad
        LOGGER.info("UsuarioEntradaDto: " + JsonPrinter.toString(usuario));
        User usuarioEntidad = modelMapper.map(usuario, User.class);


        //mandamos a persistir a la capa dao y obtenemos una entidad
        User usuarioAPersistir = userRepository.save(usuarioEntidad);
        //transformamos la entidad obtenida en salidaDto
        UserDtoSalida userDtoSalida = modelMapper.map(usuarioAPersistir, UserDtoSalida.class);
        LOGGER.info("UsuarioSalidaDto: " + JsonPrinter.toString(userDtoSalida));
        return userDtoSalida;
    }
//    @Transactional
//    @Override
//    public TokenSalidaDto createUser(UserDTO userDtoEntrada) throws DataIntegrityViolationException {
//        // Mapeo del DTO de usuario a la entidad User
//        User user = modelMapper.map(userDtoEntrada, User.class);
//
//        // Encriptar la contraseña del usuario
//        String contraseñaEncriptada = passwordEncoder.encode(user.getPassword());
//        user.setPassword(contraseñaEncriptada);
//
//        // Buscar el rol de usuario y crearlo si no existe
//        Role role = rolRepository.findByRol(RoleConstants.USER)
//                .orElseGet(() -> rolRepository.save(new Role(RoleConstants.USER)));
//
//        // Asignar el rol al usuario
//        Set<Role> roles = new HashSet<>();
//        roles.add(role);
//        user.setRoles(roles);
//
//        // Guardar el usuario en la base de datos
//        User userSaved = userRepository.save(user);
//
//        // Generar el token JWT para el usuario guardado
//        String token = jwtService.generateToken(userSaved);
//
//        // Crear el DTO de salida con los datos del usuario y el token
//        TokenSalidaDto tokenDtoSalida = new TokenSalidaDto(
//                userSaved.getId(),
//                userSaved.getUsername(),
//                userSaved.getFirstname(),
//                userSaved.getLastname(),
//                new ArrayList<>(userSaved.getRoles()),
//                token
//        );
//
//        return tokenDtoSalida;
//    }
//
//
//    @Transactional
//    @Override
//    public TokenSalidaDto createUserAdmin(UserAdminEntradaDto userAdminDtoEntrance) throws DataIntegrityViolationException
//             {
//        User user = modelMapper.map(userAdminDtoEntrance, User.class);
//        String contraseñaEncriptada = passwordEncoder.encode(user.getPassword());
//        user.setPassword(contraseñaEncriptada);
//        Role role = rolRepository.findByRol(RoleConstants.ADMIN)
//                .orElseGet(() -> rolRepository.save(new Role(RoleConstants.ADMIN)));
//        Set<Role> roles = new HashSet<>();
//        roles.add(role);
//        user.setRoles(roles);
//        String token = jwtService.generateToken(user);
//        User userSaved = userRepository.save(user);
//        TokenSalidaDto tokenDtoSalida = new TokenSalidaDto(
//                userSaved.getId(),
//                userSaved.getUsername(),
//                userSaved.getFirstname(),
//                userSaved.getLastname(),
//                new ArrayList<>(userSaved.getRoles()),
//                token
//        );
//
//
//        return tokenDtoSalida;
//    }
//    @Override
//    public TokenSalidaDto loginUserAndCheckEmail(LoginEntradaDto loginDtoEntrance) throws ResourceNotFoundException, AuthenticationException {
//        Optional<User> userOptional = userRepository.findByEmail(loginDtoEntrance.getEmail());
//        if (userOptional.isEmpty()) {
//            throw new ResourceNotFoundException("Usuario no encontrado con el correo electrónico proporcionado.");
//        }
//        Authentication authentication = authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(loginDtoEntrance.getEmail(), loginDtoEntrance.getPassword())
//        );
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
//        String token = jwtService.generateToken(userDetails);
//        User user = userOptional.get();
//        TokenSalidaDto tokenDtoSalida = new TokenSalidaDto(
//                user.getId(),
//                user.getUsername(),
//                user.getFirstname(),
//                user.getLastname(),
//                new ArrayList<>(user.getRoles()),
//                token
//        );
//        return tokenDtoSalida;
//    }

    @Override
    public UserDtoSalida buscarUsuarioPorId(Long id) {
        User usuarioBuscado = userRepository.findById(id).orElse(null);
        UserDtoSalida usuarioEncontrado = null;

        if(usuarioBuscado != null){
            usuarioEncontrado = modelMapper.map(usuarioBuscado, UserDtoSalida.class);

        }else LOGGER.error("El id no se encuentra registrao en la base de datos");
        return  usuarioEncontrado;
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



    private void configureMapping() {
        modelMapper.typeMap(UserDTO.class, User.class)
                .addMappings(mapper -> mapper.map(UserDTO::getArticlesDto, User::setArticles));
        modelMapper.typeMap(User.class, UserDtoSalida.class)
                .addMappings(mapper -> mapper.map(User::getArticles, UserDtoSalida::setArticlesSalida));
        modelMapper.typeMap(UserModificacionEntradaDTO.class, User.class)
                .addMappings(mapper -> mapper.map(UserModificacionEntradaDTO::getArticlesModificaionEntradaDto, User::setArticles));
    }
}
