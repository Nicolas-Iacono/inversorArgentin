package com.ia.inv_arg.service;

import com.ia.inv_arg.DTO.entrada.LoginEntradaDto;
import com.ia.inv_arg.DTO.entrada.UserAdminEntradaDto;
import com.ia.inv_arg.DTO.entrada.UserDTO;
import com.ia.inv_arg.DTO.modificacion.UserModificacionEntradaDTO;
import com.ia.inv_arg.DTO.salida.TokenSalidaDto;
import com.ia.inv_arg.DTO.salida.UserDtoSalida;
import com.ia.inv_arg.exception.ResourceNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IUserService {


    List<UserDtoSalida> listarUsuarios();

    TokenSalidaDto registrarUsuario(UserDTO usuario);

//    TokenSalidaDto createUser(UserDTO usuario) throws DataIntegrityViolationException;
//
//    TokenSalidaDto createUserAdmin(UserAdminEntradaDto userAdminEntradaDto) throws DataIntegrityViolationException;

    UserDtoSalida buscarUsuarioPorId(Long id);

    void eliminarUsuario(Long id);

    UserDtoSalida actualizarUsuario(UserModificacionEntradaDTO usuario);

    UserDtoSalida buscarUsuarioPorEmail(String email);

    TokenSalidaDto registrarAdmin(UserAdminEntradaDto userAdminEntradaDto);

//    TokenSalidaDto loginUserAndCheckEmail(LoginEntradaDto loginDtoEntrance) throws ResourceNotFoundException;
}
