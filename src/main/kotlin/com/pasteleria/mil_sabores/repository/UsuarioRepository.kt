package com.pasteleria.mil_sabores.repository
import  com.pasteleria.mil_sabores.model.Usuario
import org.springframework.data.jpa.repository.JpaRepository

interface UsuarioRepository : JpaRepository<Usuario, Long>