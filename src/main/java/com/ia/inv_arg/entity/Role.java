package com.ia.inv_arg.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "ROLES")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_rol")
    private Long idRol;

    @Column(name = "rol", nullable = false, unique = true)
    private String rol;

    @CreationTimestamp
    @Temporal(TemporalType.DATE)
    @Column(name = "regist_date")
    private Date registDate;
    /**
     * Constructor para crear un rol con un nombre específico.
     *
     * @param rol El nombre del rol.
     */
    public Role(String rol) {
        this.rol = rol;
    }
}