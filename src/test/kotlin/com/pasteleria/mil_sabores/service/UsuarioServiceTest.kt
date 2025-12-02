package com.pasteleria.mil_sabores.service

import com.pasteleria.mil_sabores.model.Usuario
import com.pasteleria.mil_sabores.repository.UsuarioRepository
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.jupiter.MockitoExtension
import java.util.Optional

@ExtendWith(MockitoExtension::class)
class UsuarioServiceTest {

    @Mock
    lateinit var repository: UsuarioRepository

    @InjectMocks
    lateinit var service: UsuarioService

    @Test
    fun `guardar - deberia llamar al repositorio y retornar el usuario guardado`() {
        val usuarioNuevo = Usuario(null, "Yeider", "yeider@test.com", "123456", "999999")
        val usuarioGuardado = usuarioNuevo.copy(id = 1)

        `when`(repository.save(usuarioNuevo)).thenReturn(usuarioGuardado)

        val resultado = service.guardar(usuarioNuevo)

        assertNotNull(resultado.id)
        assertEquals(1L, resultado.id)

        verify(repository, times(1)).save(usuarioNuevo)
    }

    @Test
    fun `actualizar - si el usuario existe, deberia actualizar sus datos`() {
        val id = 1L
        val usuarioExistente = Usuario(id, "Antiguo", "old@mail.com", "111", "000")
        val datosNuevos = Usuario(null, "Nuevo Nombre", "new@mail.com", "222", "111")

        `when`(repository.findById(id)).thenReturn(Optional.of(usuarioExistente))

        `when`(repository.save(any(Usuario::class.java))).thenAnswer { it.arguments[0] }

        val resultado = service.actualizar(id, datosNuevos)

        assertNotNull(resultado)
        assertEquals("Nuevo Nombre", resultado?.nombre) // Verificamos que cambió el nombre
        assertEquals("new@mail.com", resultado?.correo) // Verificamos que cambió el correo

        verify(repository).save(any(Usuario::class.java))
    }

    @Test
    fun `actualizar - si el usuario NO existe, deberia retornar null y no guardar nada`() {
        val idInexistente = 99L
        val datosNuevos = Usuario(null, "X", "x", "x", "x")

        `when`(repository.findById(idInexistente)).thenReturn(Optional.empty())

        val resultado = service.actualizar(idInexistente, datosNuevos)

        assertNull(resultado)

        verify(repository, never()).save(any())
    }

    @Test
    fun `eliminar - deberia mandar la orden de eliminar al repositorio`() {
        val id = 5L

        service.eliminar(id)

        verify(repository, times(1)).deleteById(id)
    }

    @Test
    fun `listarTodos - deberia retornar lista de la BD`() {
        val listaFicticia = listOf(
            Usuario(1, "A", "a", "1", "1"),
            Usuario(2, "B", "b", "2", "2")
        )
        `when`(repository.findAll()).thenReturn(listaFicticia)

        val resultado = service.listarTodos()

        assertEquals(2, resultado.size)
        verify(repository).findAll()
    }
}