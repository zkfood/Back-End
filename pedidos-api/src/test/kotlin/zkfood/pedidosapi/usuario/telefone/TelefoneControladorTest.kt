package zkfood.pedidosapi.usuario.telefone

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import zkfood.pedidosapi.usuario.telefone.telefoneDado.Telefone
import zkfood.pedidosapi.usuario.telefone.telefoneDado.TelefoneCadastro
import zkfood.pedidosapi.usuario.usuario.usuarioDado.Usuario

import org.junit.jupiter.api.Assertions.*

class TelefoneControladorTest {

    lateinit var telefoneServico: TelefoneServico
    lateinit var telefoneControlador: TelefoneControlador

    @BeforeEach
    fun setup() {
        telefoneServico = mock(TelefoneServico::class.java)
        telefoneControlador = TelefoneControlador(telefoneServico)
    }

    @Test
    @DisplayName("Deve cadastrar um telefone com sucesso")
    fun cadastrarTelefoneSucesso() {
        val usuarioId = 1
        val telefoneCadastro = TelefoneCadastro(numero = "123456789")
        val telefoneEsperado = Telefone(id = 1, numero = "123456789", usuario = Usuario(id = usuarioId))

        `when`(telefoneServico.cadastrar(telefoneCadastro, usuarioId)).thenReturn(telefoneEsperado)

        val resposta = telefoneControlador.cadastrar(usuarioId, telefoneCadastro)

        assertEquals(HttpStatus.CREATED, resposta.statusCode)
        assertEquals(telefoneEsperado, resposta.body)
    }

    @Test
    @DisplayName("Deve listar todos os telefones de um usuário")
    fun listarTelefonesSucesso() {
        val usuarioId = 1
        val telefoneEsperado = listOf(
            Telefone(id = 1, numero = "123456789", usuario = Usuario(id = usuarioId)),
            Telefone(id = 2, numero = "987654321", usuario = Usuario(id = usuarioId))
        )

        `when`(telefoneServico.listarTelefones(usuarioId)).thenReturn(telefoneEsperado)

        val resposta = telefoneControlador.listar(usuarioId)

        assertEquals(HttpStatus.OK, resposta.statusCode)
        assertEquals(telefoneEsperado, resposta.body)
    }

    @Test
    @DisplayName("Deve achar um telefone por ID com sucesso")
    fun acharPorIdTelefoneSucesso() {
        val usuarioId = 1
        val telefoneId = 1
        val telefoneEsperado = Telefone(id = telefoneId, numero = "123456789", usuario = Usuario(id = usuarioId))

        `when`(telefoneServico.acharPorId(telefoneId, usuarioId)).thenReturn(telefoneEsperado)

        val resposta = telefoneControlador.acharPorId(telefoneId, usuarioId)

        assertEquals(HttpStatus.OK, resposta.statusCode)
        assertEquals(telefoneEsperado, resposta.body)
    }

    @Test
    @DisplayName("Deve atualizar um telefone com sucesso")
    fun atualizarTelefoneSucesso() {
        val usuarioId = 1
        val telefoneId = 1
        val telefoneAtualizado = Telefone(id = telefoneId, numero = "987654321", usuario = Usuario(id = usuarioId))

        `when`(telefoneServico.atualizar(telefoneId, telefoneAtualizado, usuarioId)).thenReturn(telefoneAtualizado)

        val resposta = telefoneControlador.atualizar(telefoneId, telefoneAtualizado, usuarioId)

        assertEquals(HttpStatus.OK, resposta.statusCode)
        assertEquals(telefoneAtualizado, resposta.body)
    }

    @Test
    @DisplayName("Deve deletar um telefone com sucesso")
    fun deletarTelefoneSucesso() {
        val usuarioId = 1
        val telefoneId = 1

        doNothing().`when`(telefoneServico).deletarPorId(telefoneId, usuarioId)

        val resposta = telefoneControlador.deletar(telefoneId, usuarioId)

        assertEquals(HttpStatus.NO_CONTENT, resposta.statusCode)
    }
}
