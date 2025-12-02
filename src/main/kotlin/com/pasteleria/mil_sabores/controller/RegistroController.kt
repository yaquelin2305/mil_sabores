package com.pasteleria.mil_sabores.controller

import com.pasteleria.mil_sabores.model.Usuario
import com.pasteleria.mil_sabores.service.UsuarioService
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/registro")
@CrossOrigin("*")
class RegistroController(
    private val service: UsuarioService
) {

    // POST ya existente: registrar nuevo usuario
    @PostMapping
    fun registrar(@RequestBody usuario: Usuario): ResponseEntity<Usuario> {
        val creado = service.guardar(usuario)
        return ResponseEntity.ok(creado)
    }

    // GET -> Listar todos los usuarios
    @GetMapping
    fun listar(): ResponseEntity<List<Usuario>> {
        val lista = service.listarTodos()
        return ResponseEntity.ok(lista)
    }

    // GET por id
    @GetMapping("/{id}")
    fun obtenerPorId(@PathVariable id: Long): ResponseEntity<Usuario> {
        val usuario = service.obtenerPorId(id)
        return if (usuario != null) ResponseEntity.ok(usuario)
        else ResponseEntity.notFound().build()
    }

    // PUT -> actualizar usuario por id
    @PutMapping("/{id}")
    fun actualizar(@PathVariable id: Long, @RequestBody datos: Usuario): ResponseEntity<Usuario> {
        val actualizado = service.actualizar(id, datos)
        return if (actualizado != null) ResponseEntity.ok(actualizado)
        else ResponseEntity.notFound().build()
    }

    // DELETE -> eliminar por id
    @DeleteMapping("/{id}")
    fun eliminar(@PathVariable id: Long): ResponseEntity<Void> {
        return try {
            service.eliminar(id)
            ResponseEntity.noContent().build()
        } catch (ex: EmptyResultDataAccessException) {
            ResponseEntity.notFound().build()
        }
    }
}
