package zkfood.pedidosapi.usuario.telefone

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import org.mockito.InjectMocks
import org.mockito.Mock
import org.springframework.http.ResponseEntity
import zkfood.pedidosapi.usuario.telefone.telefoneDado.Telefone
import zkfood.pedidosapi.usuario.telefone.telefoneDado.TelefoneCadastro

class TelefoneControladorTests {

    @Mock
    private lateinit var telefoneServico: TelefoneServico

    @InjectMocks
    private lateinit var telefoneControlador: TelefoneControlador

    private lateinit var telefone: Telefone
    private lateinit var telefoneCadastro: TelefoneCadastro

    @BeforeEach
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        telefone = Telefone(id = 1, numero = "1234567890", usuario = null)
        telefoneCadastro = TelefoneCadastro(numero = "1234567890")
    }

    @Test
    fun `deve cadastrar um novo telefone`() {
        `when`(telefoneServico.cadastrar(telefoneCadastro, 1)).thenReturn(telefone)

        val response = telefoneControlador.cadastrar(1, telefoneCadastro)

        assertNotNull(response)
        assertEquals(201, response.statusCodeValue)
        assertEquals(telefone, response.body)
    }

    @Test
    fun `deve listar os telefones de um usuario`() {
        `when`(telefoneServico.listarTelefones(1)).thenReturn(listOf(telefone))

        val response = telefoneControlador.listar(1)

        assertNotNull(response)
        assertEquals(200, response.statusCodeValue)
        assertEquals(1, response.body?.size)
        assertEquals(telefone, response.body?.get(0))
    }

    @Test
    fun `deve retornar o telefone por id`() {
        `when`(telefoneServico.acharPorId(1, 1)).thenReturn(telefone)

        val response = telefoneControlador.acharPorId(1, 1)

        assertNotNull(response)
        assertEquals(200, response.statusCodeValue)
        assertEquals(telefone, response.body)
    }

    @Test
    fun `deve atualizar o telefone`() {
        `when`(telefoneServico.atualizar(1, telefone, 1)).thenReturn(telefone)

        val response = telefoneControlador.atualizar(1, telefone, 1)

        assertNotNull(response)
        assertEquals(200, response.statusCodeValue)
        assertEquals(telefone, response.body)
    }

    @Test
    fun `deve deletar o telefone`() {
        doNothing().`when`(telefoneServico).deletarPorId(1, 1)

        val response = telefoneControlador.deletar(1, 1)

        assertNotNull(response)
        assertEquals(204, response.statusCodeValue)
    }
}
