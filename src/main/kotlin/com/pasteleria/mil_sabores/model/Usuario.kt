package com.pasteleria.mil_sabores.model

import jakarta.persistence.*

@Entity
@Table(name = "usuarios")
data class Usuario(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    val nombre: String,
    val correo: String,
    val contrasena: String,
    val telefono: String? = null
)