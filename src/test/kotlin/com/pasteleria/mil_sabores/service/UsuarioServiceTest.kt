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
    private lateinit var repository: UsuarioRepository

    @InjectMocks
    private lateinit var service: UsuarioService

    @Test
    fun `deberia guardar un usuario correctamente`() {
        val usuarioNuevo = Usuario(nombre = "Juan", correo = "juan@test.com", contrasena = "123")
        val usuarioGuardado = usuarioNuevo.copy(id = 1L)

        `when`(repository.save(usuarioNuevo)).thenReturn(usuarioGuardado)

        val resultado = service.guardar(usuarioNuevo)

        assertNotNull(resultado.id)
        assertEquals("Juan", resultado.nombre)
    }

    @Test
    fun `deberia retornar un usuario si existe el ID`() {
        val id = 1L
        val usuarioMock = Usuario(id = id, nombre = "Maria", correo = "m@test.com", contrasena = "abc")

        `when`(repository.findById(id)).thenReturn(Optional.of(usuarioMock))

        val resultado = service.obtenerPorId(id)

        assertNotNull(resultado)
        assertEquals("Maria", resultado?.nombre)
    }

    @Test
    fun `deberia actualizar datos de un usuario existente`() {
        val id = 1L
        val usuarioOriginal = Usuario(id = id, nombre = "Original", correo = "old@test.com", contrasena = "111")
        val datosNuevos = Usuario(nombre = "Editado", correo = "new@test.com", contrasena = "222")

        `when`(repository.findById(id)).thenReturn(Optional.of(usuarioOriginal))
        `when`(repository.save(any(Usuario::class.java))).thenAnswer { it.arguments[0] }

        val resultado = service.actualizar(id, datosNuevos)

        assertEquals("Editado", resultado?.nombre)
        assertEquals("new@test.com", resultado?.correo)
    }

    @Test
    fun `deberia llamar al repositorio para eliminar`() {
        val id = 1L

        service.eliminar(id)

        verify(repository, times(1)).deleteById(id)
    }

    @Test
    fun `login deberia retornar usuario si credenciales son correctas`() {
        val correo = "admin@mil.com"
        val pass = "secreta"
        val usuarioMock = Usuario(id = 1, nombre = "Admin", correo = correo, contrasena = pass)

        `when`(repository.findByCorreo(correo)).thenReturn(usuarioMock)

        val resultado = service.login(correo, pass)

        assertNotNull(resultado)
        assertEquals(correo, resultado?.correo)
    }

    @Test
    fun `login deberia retornar null si la contrasena es incorrecta`() {
        val correo = "admin@mil.com"
        val usuarioMock = Usuario(id = 1, nombre = "Admin", correo = correo, contrasena = "real")

        `when`(repository.findByCorreo(correo)).thenReturn(usuarioMock)

        val resultado = service.login(correo, "incorrecta")

        assertNull(resultado)
    }
}