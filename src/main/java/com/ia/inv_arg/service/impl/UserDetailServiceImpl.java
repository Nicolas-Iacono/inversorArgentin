package com.ia.inv_arg.service.impl;

import com.ia.inv_arg.DTO.salida.UserDtoSalida;
import com.ia.inv_arg.entity.User;
import com.ia.inv_arg.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserDetailServiceImpl implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.findUserByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("El usuario " + username + " no existe"));

        List<SimpleGrantedAuthority> authorityList = new ArrayList<>();

        user.getRoles()
                .forEach(role -> authorityList.add(new SimpleGrantedAuthority("ROLE_".concat(role.getRol()))));

        user.getRoles().stream()
                .flatMap(role -> role.getPermisosList().stream())
                .forEach(permisos -> authorityList.add(new SimpleGrantedAuthority(permisos.getName())));


        return new User(user.getUsername(),
                user.getPassword(),
                user.isAccountNonLocked(),
                user.isEnabled(),
                user.isAccountNonLocked(),
                user.isAccountNonExpired(),
                authorityList);
    }
}
