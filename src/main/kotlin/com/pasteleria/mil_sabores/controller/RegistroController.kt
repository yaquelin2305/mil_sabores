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

    @PostMapping
    fun registrar(@RequestBody usuario: Usuario): ResponseEntity<Usuario> {
        val creado = service.guardar(usuario)
        return ResponseEntity.ok(creado)
    }

    @GetMapping
    fun listar(): ResponseEntity<List<Usuario>> {
        val lista = service.listarTodos()
        return ResponseEntity.ok(lista)
    }

    @GetMapping("/{id}")
    fun obtenerPorId(@PathVariable id: Long): ResponseEntity<Usuario> {
        val usuario = service.obtenerPorId(id)
        return if (usuario != null) ResponseEntity.ok(usuario)
        else ResponseEntity.notFound().build()
    }

    @PutMapping("/{id}")
    fun actualizar(@PathVariable id: Long, @RequestBody datos: Usuario): ResponseEntity<Usuario> {
        val actualizado = service.actualizar(id, datos)
        return if (actualizado != null) ResponseEntity.ok(actualizado)
        else ResponseEntity.notFound().build()
    }

    @DeleteMapping("/{id}")
    fun eliminar(@PathVariable id: Long): ResponseEntity<Void> {
        return try {
            service.eliminar(id)
            ResponseEntity.noContent().build()
        } catch (ex: EmptyResultDataAccessException) {
            ResponseEntity.notFound().build()
        }
    }
    @PostMapping("/login")
    fun login(@RequestBody datos: Map<String, String>): ResponseEntity<Any> {

        println("### LOGIN RECIBIDO ###")
        println("Correo: " + datos["correo"])
        println("Contraseña: " + datos["contrasena"])

        val correo = datos["correo"] ?: return ResponseEntity.badRequest().body("Falta el correo")
        val contrasena = datos["contrasena"] ?: return ResponseEntity.badRequest().body("Falta la contraseña")

        val usuario = service.login(correo, contrasena)

        return if (usuario != null) {
            ResponseEntity.ok(usuario)
        } else {
            ResponseEntity.status(401).body("Credenciales incorrectas")
        }
    }


}
