package zkfood.pedidosapi.usuario.telefone

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import org.mockito.Mockito.*
import zkfood.pedidosapi.nucleo.erros.NaoEncontradoPorIdExcecao
import zkfood.pedidosapi.usuario.telefone.telefoneDado.Telefone
import zkfood.pedidosapi.usuario.telefone.telefoneDado.TelefoneCadastro
import zkfood.pedidosapi.usuario.usuario.UsuarioServico
import zkfood.pedidosapi.usuario.usuario.usuarioDado.Usuario
import java.util.*

class TelefoneServicoTest {

    lateinit var telefoneRepositorio: TelefoneRepositorio
    lateinit var usuarioServico: UsuarioServico
    lateinit var telefoneServico: TelefoneServico

    @BeforeEach
    fun setup() {
        telefoneRepositorio = mock(TelefoneRepositorio::class.java)
        usuarioServico = mock(UsuarioServico::class.java)
        telefoneServico = TelefoneServico(telefoneRepositorio, usuarioServico = usuarioServico)
    }

    @Test
    @DisplayName("Deve cadastrar um telefone com sucesso")
    fun cadastrarTelefoneSucesso() {
        val telefoneCadastro = TelefoneCadastro("123456789")
        val usuarioId = 1
        val usuario = Usuario(id = usuarioId, nome = "João")
        val telefone = Telefone(id = 1, numero = "123456789", usuario = usuario)

        `when`(usuarioServico.acharPorId(usuarioId)).thenReturn(usuario)
        `when`(telefoneRepositorio.save(any(Telefone::class.java))).thenReturn(telefone)

        val resultado = telefoneServico.cadastrar(telefoneCadastro, usuarioId)

        assertEquals(telefone.id, resultado.id)
        assertEquals(telefone.numero, resultado.numero)
        assertEquals(telefone.usuario, resultado.usuario)
    }

    @Test
    @DisplayName("Deve lançar exceção ao tentar cadastrar telefone para usuário inexistente")
    fun cadastrarTelefoneUsuarioInexistente() {
        val telefoneCadastro = TelefoneCadastro("123456789")
        val usuarioId = 1

        `when`(usuarioServico.acharPorId(usuarioId)).thenThrow(NaoEncontradoPorIdExcecao::class.java)

        val excecao = assertThrows(NaoEncontradoPorIdExcecao::class.java) {
            telefoneServico.cadastrar(telefoneCadastro, usuarioId)
        }

        assertNotNull(excecao)
    }

    @Test
    @DisplayName("Deve listar telefones de um usuário com sucesso")
    fun listarTelefonesSucesso() {
        val usuarioId = 1
        val usuario = Usuario(id = usuarioId, nome = "João")
        val telefones = listOf(
            Telefone(id = 1, numero = "123456789", usuario = usuario),
            Telefone(id = 2, numero = "987654321", usuario = usuario)
        )

        `when`(usuarioServico.acharPorId(usuarioId)).thenReturn(usuario)
        `when`(telefoneRepositorio.findByUsuarioId(usuarioId)).thenReturn(telefones)

        val resultado = telefoneServico.listarTelefones(usuarioId)

        assertEquals(telefones.size, resultado.size)
        assertEquals(telefones, resultado)
    }

    @Test
    @DisplayName("Deve lançar exceção ao tentar listar telefones de usuário inexistente")
    fun listarTelefonesUsuarioInexistente() {
        val usuarioId = 1

        `when`(usuarioServico.acharPorId(usuarioId)).thenThrow(NaoEncontradoPorIdExcecao::class.java)

        val excecao = assertThrows(NaoEncontradoPorIdExcecao::class.java) {
            telefoneServico.listarTelefones(usuarioId)
        }

        assertNotNull(excecao)
    }

    @Test
    @DisplayName("Deve encontrar telefone por ID com sucesso")
    fun acharPorIdSucesso() {
        val usuarioId = 1
        val telefoneId = 1
        val usuario = Usuario(id = usuarioId, nome = "João")
        val telefone = Telefone(id = telefoneId, numero = "123456789", usuario = usuario)

        `when`(usuarioServico.acharPorId(usuarioId)).thenReturn(usuario)
        `when`(telefoneRepositorio.findById(telefoneId)).thenReturn(Optional.of(telefone))

        val resultado = telefoneServico.acharPorId(telefoneId, usuarioId)

        assertEquals(telefone.id, resultado.id)
        assertEquals(telefone.numero, resultado.numero)
        assertEquals(telefone.usuario, resultado.usuario)
    }

    @Test
    @DisplayName("Deve lançar exceção ao tentar encontrar telefone por ID inexistente")
    fun acharPorIdInexistente() {
        val usuarioId = 1
        val telefoneId = 1
        val usuario = Usuario(id = usuarioId, nome = "João")

        `when`(usuarioServico.acharPorId(usuarioId)).thenReturn(usuario)
        `when`(telefoneRepositorio.findById(telefoneId)).thenReturn(Optional.empty())

        val excecao = assertThrows(NaoEncontradoPorIdExcecao::class.java) {
            telefoneServico.acharPorId(telefoneId, usuarioId)
        }

        assertNotNull(excecao)
    }

    @Test
    @DisplayName("Deve atualizar telefone com sucesso")
    fun atualizarTelefoneSucesso() {
        val usuarioId = 1
        val telefoneId = 1
        val usuario = Usuario(id = usuarioId, nome = "João")
        val telefoneAtualizado = Telefone(id = telefoneId, numero = "123456789", usuario = usuario)
        val telefone = Telefone(id = telefoneId, numero = "987654321", usuario = usuario)

        `when`(usuarioServico.acharPorId(usuarioId)).thenReturn(usuario)
        `when`(telefoneRepositorio.findById(telefoneId)).thenReturn(Optional.of(telefone))
        `when`(telefoneRepositorio.save(any(Telefone::class.java))).thenReturn(telefoneAtualizado)

        val resultado = telefoneServico.atualizar(telefoneId, telefoneAtualizado, usuarioId)

        assertEquals(telefoneAtualizado.id, resultado.id)
        assertEquals(telefoneAtualizado.numero, resultado.numero)
        assertEquals(telefoneAtualizado.usuario, resultado.usuario)
    }

    @Test
    @DisplayName("Deve lançar exceção ao tentar atualizar telefone de usuário inexistente")
    fun atualizarTelefoneUsuarioInexistente() {
        val usuarioId = 1
        val telefoneId = 1
        val telefoneAtualizado = Telefone(id = telefoneId, numero = "123456789", usuario = null)

        `when`(usuarioServico.acharPorId(usuarioId)).thenThrow(NaoEncontradoPorIdExcecao::class.java)

        val excecao = assertThrows(NaoEncontradoPorIdExcecao::class.java) {
            telefoneServico.atualizar(telefoneId, telefoneAtualizado, usuarioId)
        }

        assertNotNull(excecao)
    }

    @Test
    @DisplayName("Deve deletar telefone por ID com sucesso")
    fun deletarTelefoneSucesso() {
        val usuarioId = 1
        val telefoneId = 1
        val usuario = Usuario(id = usuarioId, nome = "João")
        val telefone = Telefone(id = telefoneId, numero = "123456789", usuario = usuario)

        `when`(usuarioServico.acharPorId(usuarioId)).thenReturn(usuario)
        `when`(telefoneRepositorio.findById(telefoneId)).thenReturn(Optional.of(telefone))

        telefoneServico.deletarPorId(telefoneId, usuarioId)

        verify(telefoneRepositorio, times(1)).deleteById(telefoneId)
    }

    @Test
    @DisplayName("Deve lançar exceção ao tentar deletar telefone por ID inexistente")
    fun deletarTelefoneInexistente() {
        val usuarioId = 1
        val telefoneId = 1
        val usuario = Usuario(id = usuarioId, nome = "João")

        `when`(usuarioServico.acharPorId(usuarioId)).thenReturn(usuario)
        `when`(telefoneRepositorio.findById(telefoneId)).thenReturn(Optional.empty())

        val excecao = assertThrows(NaoEncontradoPorIdExcecao::class.java) {
            telefoneServico.deletarPorId(telefoneId, usuarioId)
        }

        assertNotNull(excecao)
    }
}
