package zkfood.pedidosapi.usuario.usuario

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.Mockito.*
import org.modelmapper.ModelMapper
import org.springframework.web.server.ResponseStatusException
import zkfood.pedidosapi.nucleo.EnviarEmail
import zkfood.pedidosapi.usuario.usuario.usuarioDado.*
import zkfood.pedidosapi.usuario.usuario.usuarioErros.LoginOuSenhaNaoInformados
import java.util.*

class UsuarioServicoTest {

    lateinit var usuarioRepositorio: UsuarioRepositorio
    lateinit var enviarEmail: EnviarEmail
    lateinit var mapper: ModelMapper
    lateinit var service: UsuarioServico

    @BeforeEach
    fun setup() {
        usuarioRepositorio = mock(UsuarioRepositorio::class.java)
        enviarEmail = mock(EnviarEmail::class.java)
        mapper = mock(ModelMapper::class.java)
        service = UsuarioServico(usuarioRepositorio, enviarEmail, mapper)
    }

    @DisplayName("Cadastrar novo usuário com sucesso")
    @Test
    fun cadastrar_Success() {
        val usuarioCadastro = UsuarioCadastro(
            nome = "Teste",
            email = "teste@teste.com",
            senha = "123456",
            cpf = "111.111.111-11",
            eCadastroOnline = true
        )

        val usuario = Usuario(
            id = 1,
            nome = "Teste",
            email = "teste@teste.com",
            senha = "123456",
            cpf = "111.111.111-11",
            autenticado = false
        )

        `when`(mapper.map(usuarioCadastro, Usuario::class.java)).thenReturn(usuario)
        `when`(usuarioRepositorio.save(any(Usuario::class.java))).thenReturn(usuario)

        val resultado = service.cadastrar(usuarioCadastro)

        assertEquals(usuario, resultado)
    }

    @DisplayName("Erro ao cadastrar usuário sem senha")
    @Test
    fun cadastrar_SemSenha() {
        val usuarioCadastro = UsuarioCadastro(
            nome = "Teste",
            email = "teste@teste.com",
            senha = null,
            cpf = "111.111.111-11",
            eCadastroOnline = false
        )

        assertThrows<LoginOuSenhaNaoInformados> {
            service.cadastrar(usuarioCadastro)
        }
    }

    @DisplayName("Recuperar senha com sucesso")
    @Test
    fun recuperarSenha_Success() {
        val usuario = Usuario(
            id = 1,
            nome = "Teste",
            email = "teste@teste.com",
            senha = "123456",
            cpf = "111.111.111-11",
            autenticado = false
        )

        `when`(usuarioRepositorio.findAll()).thenReturn(listOf(usuario))
        `when`(usuarioRepositorio.save(any(Usuario::class.java))).thenReturn(usuario)

        service.recuperarSenha("teste@teste.com")

        verify(enviarEmail).enviarEmail(eq("teste@teste.com"), anyString(), contains("Olá Teste"))
    }

    @DisplayName("Erro ao recuperar senha com email vazio")
    @Test
    fun recuperarSenha_EmailVazio() {
        assertThrows<ResponseStatusException> {
            service.recuperarSenha("")
        }
    }

    @DisplayName("Alterar senha com sucesso")
    @Test
    fun alterarSenha_Success() {
        val usuario = Usuario(
            id = 1,
            nome = "Teste",
            email = "teste@teste.com",
            senha = "123456",
            cpf = "111.111.111-11",
            autenticado = false
        )

        `when`(usuarioRepositorio.findById(1)).thenReturn(Optional.of(usuario))
        `when`(usuarioRepositorio.save(any(Usuario::class.java))).thenReturn(usuario)

        val novaSenha = NovaSenha(senha = "novaSenha123")
        val resultado = service.alterarSenha(1, novaSenha)

        assertEquals("novaSenha123", resultado.senha)
    }

    @DisplayName("Erro ao alterar senha para usuário inexistente")
    @Test
    fun alterarSenha_UsuarioInexistente() {
        `when`(usuarioRepositorio.findById(1)).thenReturn(Optional.empty())

        val novaSenha = NovaSenha(senha = "novaSenha123")

        assertThrows<ResponseStatusException> {
            service.alterarSenha(1, novaSenha)
        }
    }

    @DisplayName("Entrar com sucesso")
    @Test
    fun entrar_Success() {
        val usuario = Usuario(
            id = 1,
            nome = "Teste",
            email = "teste@teste.com",
            senha = "123456",
            cpf = "111.111.111-11",
            autenticado = false
        )

        val login = Login(email = "teste@teste.com", senha = "123456")

        `when`(usuarioRepositorio.findAll()).thenReturn(listOf(usuario))
        `when`(usuarioRepositorio.save(any(Usuario::class.java))).thenReturn(usuario)

        val resultado = service.entrar(login)

        assertTrue(resultado.autenticado)
    }

    @DisplayName("Erro ao entrar com login ou senha inválidos")
    @Test
    fun entrar_LoginOuSenhaInvalidos() {
        val login = Login(email = null, senha = "123456")

        assertThrows<LoginOuSenhaNaoInformados> {
            service.entrar(login)
        }
    }

    @DisplayName("Sair com sucesso")
    @Test
    fun sair_Success() {
        val usuario = Usuario(
            id = 1,
            nome = "Teste",
            email = "teste@teste.com",
            senha = "123456",
            cpf = "111.111.111-11",
            autenticado = true
        )

        `when`(usuarioRepositorio.findById(1)).thenReturn(Optional.of(usuario))

        service.sair(1)

        assertFalse(usuario.autenticado)
    }

    @DisplayName("Erro ao sair com ID inexistente")
    @Test
    fun sair_IdInexistente() {
        `when`(usuarioRepositorio.findById(1)).thenReturn(Optional.empty())

        assertThrows<ResponseStatusException> {
            service.sair(1)
        }
    }
}
