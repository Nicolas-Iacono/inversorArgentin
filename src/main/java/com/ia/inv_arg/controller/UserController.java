package com.ia.inv_arg.controller;


import com.ia.inv_arg.DTO.entrada.UserDTO;
import com.ia.inv_arg.DTO.salida.UserDtoSalida;
import com.ia.inv_arg.entity.User;
import com.ia.inv_arg.service.IUserService;
import com.ia.inv_arg.service.impl.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/user")
@CrossOrigin
public class UserController {

    private IUserService userService;

    @Autowired
    public UserController(IUserService userService){
        this.userService = userService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<UserDtoSalida>> listarUsuarios(){
        return new ResponseEntity<>(userService.listarUsuarios(), HttpStatus.OK);
    }

    @PostMapping("/registrar")
    public ResponseEntity<UserDtoSalida> registrarUsuario(@RequestBody @Valid UserDTO ususario){

        return new ResponseEntity<>(userService.registrarUsuario(ususario), HttpStatus.CREATED);
    }


    @GetMapping(path = "/{id}")
    public ResponseEntity<UserDtoSalida> listarUsuarioPorId (@PathVariable Long id) {
        return new ResponseEntity<>(userService.buscarUsuarioPorId(id), HttpStatus.OK);
    }


    @DeleteMapping(path = "/delete/{id}")
    public ResponseEntity<?> eliminarUsuario(@PathVariable Long id) {
        userService.eliminarUsuario(id);
        return new ResponseEntity<>("Usuario eliminado correctamente", HttpStatus.OK);

    }


}
