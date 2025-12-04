package com.pasteleria.mil_sabores.service

import com.pasteleria.mil_sabores.model.Usuario
import com.pasteleria.mil_sabores.repository.UsuarioRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class UsuarioService(
    private val repo: UsuarioRepository
) {

    fun guardar(usuario: Usuario): Usuario {
        return repo.save(usuario)
    }

    fun listarTodos(): List<Usuario> = repo.findAll()

    fun obtenerPorId(id: Long): Usuario? = repo.findByIdOrNull(id)

    fun actualizar(id: Long, datos: Usuario): Usuario? {
        val existente = obtenerPorId(id) ?: return null
        val actualizado = existente.copy(
            nombre = datos.nombre,
            correo = datos.correo,
            contrasena = datos.contrasena,
            telefono = datos.telefono
        )
        return repo.save(actualizado)
    }

    fun eliminar(id: Long) {
        repo.deleteById(id)
    }


    fun login(correo: String, contrasena: String): Usuario? {
        val usuario = repo.findByCorreo(correo)
        return if (usuario != null && usuario.contrasena == contrasena) {
            usuario
        } else {
            null
        }
    }
}
