package zkfood.pedidosapi.usuario.endereco

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import org.mockito.Mockito.*
import org.modelmapper.ModelMapper
import zkfood.pedidosapi.usuario.endereco.enderecoDado.Endereco
import zkfood.pedidosapi.usuario.endereco.enderecoDado.EnderecoCadastro
import zkfood.pedidosapi.nucleo.erros.NaoEncontradoPorIdExcecao
import zkfood.pedidosapi.usuario.usuario.UsuarioServico
import zkfood.pedidosapi.usuario.usuario.usuarioDado.Usuario
import java.util.*

class EnderecoServicoTest {

    lateinit var enderecoRepositorio: EnderecoRepositorio
    lateinit var usuarioServico: UsuarioServico
    lateinit var mapper: ModelMapper
    lateinit var enderecoServico: EnderecoServico

    @BeforeEach
    fun setup() {
        enderecoRepositorio = mock(EnderecoRepositorio::class.java)
        usuarioServico = mock(UsuarioServico::class.java)
        mapper = mock(ModelMapper::class.java)
        enderecoServico = EnderecoServico(enderecoRepositorio, mapper, usuarioServico)
    }

    @Test
    @DisplayName("deve cadastrar um endereço com sucesso")
    fun cadastrarEnderecoSucesso() {
        val enderecoCadastro = EnderecoCadastro("96969696", "Hadock", "Cidade Tiradentes", "82", "b")
        val usuarioId = 1
        val usuario = Usuario(id = usuarioId, nome = "Enzo")
        val endereco = Endereco(
            id = 1,
            cep = "96969696",
            rua = "Hadock",
            bairro = "Cidade Tiradentes",
            numero = "82",
            complemento = "b",
            usuario = usuario
        )

        `when`(usuarioServico.acharPorId(usuarioId)).thenReturn(usuario)
        `when`(mapper.map(enderecoCadastro, Endereco::class.java)).thenReturn(endereco)
        `when`(enderecoRepositorio.save(any(Endereco::class.java))).thenReturn(endereco)

        val resultado = enderecoServico.cadastrar(enderecoCadastro, usuarioId)

        assertEquals(endereco.id, resultado.id)
        assertEquals(endereco.cep, resultado.cep)
        assertEquals(endereco.rua, resultado.rua)
        assertEquals(endereco.bairro, resultado.bairro)
        assertEquals(endereco.numero, resultado.numero)
        assertEquals(endereco.complemento, resultado.complemento)
        assertEquals(endereco.usuario, resultado.usuario)
    }

    @Test
    @DisplayName("deve lançar exceção ao tentar cadastrar endereço para usuário inexistente")
    fun cadastrarEnderecoUsuarioInexistente() {
        val enderecoCadastro = EnderecoCadastro("96969696", "Hadock", "Cidade Tiradentes", "82", "b")
        val usuarioId = 1

        `when`(usuarioServico.acharPorId(usuarioId)).thenThrow(NaoEncontradoPorIdExcecao::class.java)

        val excecao = assertThrows(NaoEncontradoPorIdExcecao::class.java) {
            enderecoServico.cadastrar(enderecoCadastro, usuarioId)
        }

        assertNotNull(excecao)
    }

    @Test
    @DisplayName("deve listar endereços de um usuário com sucesso")
    fun listarEnderecosSucesso() {
        val usuarioId = 1
        val usuario = Usuario(id = usuarioId, nome = "Enzo")
        val enderecos = listOf(
            Endereco(
                id = 1,
                cep = "96969696",
                rua = "Hadock",
                bairro = "Cidade Tiradentes",
                numero = "82",
                complemento = "b",
                usuario = usuario
            ),
            Endereco(
                id = 2,
                cep = "87654321",
                rua = "Rua B",
                bairro = "Bairro C",
                numero = "456",
                complemento = "Casa 2",
                usuario = usuario
            )
        )

        `when`(usuarioServico.acharPorId(usuarioId)).thenReturn(usuario)
        `when`(enderecoRepositorio.findByUsuarioId(usuarioId)).thenReturn(enderecos)

        val resultado = enderecoServico.listarEnderecos(usuarioId)

        assertEquals(enderecos.size, resultado.size)
        assertEquals(enderecos, resultado)
    }

    @Test
    @DisplayName("deve lançar exceção ao tentar listar endereços de usuário inexistente")
    fun listarEnderecosUsuarioInexistente() {
        val usuarioId = 1

        `when`(usuarioServico.acharPorId(usuarioId)).thenThrow(NaoEncontradoPorIdExcecao::class.java)

        val excecao = assertThrows(NaoEncontradoPorIdExcecao::class.java) {
            enderecoServico.listarEnderecos(usuarioId)
        }

        assertNotNull(excecao)
    }

    @Test
    @DisplayName("deve encontrar endereço por ID com sucesso")
    fun acharPorIdSucesso() {
        val usuarioId = 1
        val enderecoId = 1
        val usuario = Usuario(id = usuarioId, nome = "Enzo")
        val endereco = Endereco(
            id = enderecoId,
            cep = "96969696",
            rua = "Hadock",
            bairro = "Cidade Tiradentes",
            numero = "82",
            complemento = "b",
            usuario = usuario
        )

        `when`(usuarioServico.acharPorId(usuarioId)).thenReturn(usuario)
        `when`(enderecoRepositorio.findById(enderecoId)).thenReturn(Optional.of(endereco))

        val resultado = enderecoServico.acharPorId(enderecoId, usuarioId)

        assertEquals(endereco.id, resultado.id)
        assertEquals(endereco.cep, resultado.cep)
        assertEquals(endereco.rua, resultado.rua)
        assertEquals(endereco.bairro, resultado.bairro)
        assertEquals(endereco.numero, resultado.numero)
        assertEquals(endereco.complemento, resultado.complemento)
        assertEquals(endereco.usuario, resultado.usuario)
    }

    @Test
    @DisplayName("deve lançar exceção ao tentar encontrar endereço por ID inexistente")
    fun acharPorIdInexistente() {
        val usuarioId = 1
        val enderecoId = 1
        val usuario = Usuario(id = usuarioId, nome = "Enzo")

        `when`(usuarioServico.acharPorId(usuarioId)).thenReturn(usuario)
        `when`(enderecoRepositorio.findById(enderecoId)).thenReturn(Optional.empty())

        val excecao = assertThrows(NaoEncontradoPorIdExcecao::class.java) {
            enderecoServico.acharPorId(enderecoId, usuarioId)
        }

        assertNotNull(excecao)
    }

    @Test
    @DisplayName("deve atualizar endereço com sucesso")
    fun atualizarEnderecoSucesso() {
        val usuarioId = 1
        val enderecoId = 1
        val usuario = Usuario(id = usuarioId, nome = "Enzo")
        val enderecoAtualizado = Endereco(
            id = enderecoId,
            cep = "96969696",
            rua = "Hadock",
            bairro = "Cidade Tiradentes",
            numero = "82",
            complemento = "b",
            usuario = usuario
        )
        val endereco = Endereco(
            id = enderecoId,
            cep = "87654321",
            rua = "Rua B",
            bairro = "Bairro C",
            numero = "456",
            complemento = "Casa 2",
            usuario = usuario
        )

        `when`(usuarioServico.acharPorId(usuarioId)).thenReturn(usuario)
        `when`(enderecoRepositorio.findById(enderecoId)).thenReturn(Optional.of(endereco))
        `when`(enderecoRepositorio.save(any(Endereco::class.java))).thenReturn(enderecoAtualizado)

        val resultado = enderecoServico.atualizar(enderecoId, enderecoAtualizado, usuarioId)

        assertEquals(enderecoAtualizado.id, resultado.id)
        assertEquals(enderecoAtualizado.cep, resultado.cep)
        assertEquals(enderecoAtualizado.rua, resultado.rua)
        assertEquals(enderecoAtualizado.bairro, resultado.bairro)
        assertEquals(enderecoAtualizado.numero, resultado.numero)
        assertEquals(enderecoAtualizado.complemento, resultado.complemento)
        assertEquals(enderecoAtualizado.usuario, resultado.usuario)
    }

    @Test
    @DisplayName("deve lançar exceção ao tentar atualizar endereço de usuário inexistente")
    fun atualizarEnderecoUsuarioInexistente() {
        val usuarioId = 1
        val enderecoId = 1
        val enderecoAtualizado = Endereco(
            id = enderecoId,
            cep = "96969696",
            rua = "Hadock",
            bairro = "Cidade Tiradentes",
            numero = "82",
            complemento = "b",
            usuario = null
        )

        `when`(usuarioServico.acharPorId(usuarioId)).thenThrow(NaoEncontradoPorIdExcecao::class.java)

        val excecao = assertThrows(NaoEncontradoPorIdExcecao::class.java) {
            enderecoServico.atualizar(enderecoId, enderecoAtualizado, usuarioId)
        }

        assertNotNull(excecao)
    }

    @Test
    @DisplayName("deve lançar exceção ao tentar atualizar endereço inexistente")
    fun atualizarEnderecoInexistente() {
        val usuarioId = 1
        val enderecoId = 1
        val usuario = Usuario(id = usuarioId, nome = "Enzo")
        val enderecoAtualizado = Endereco(
            id = enderecoId,
            cep = "96969696",
            rua = "Hadock",
            bairro = "Cidade Tiradentes",
            numero = "82",
            complemento = "b",
            usuario = usuario
        )

        `when`(usuarioServico.acharPorId(usuarioId)).thenReturn(usuario)
        `when`(enderecoRepositorio.findById(enderecoId)).thenReturn(Optional.empty())

        val excecao = assertThrows(NaoEncontradoPorIdExcecao::class.java) {
            enderecoServico.atualizar(enderecoId, enderecoAtualizado, usuarioId)
        }

        assertNotNull(excecao)
    }
}
