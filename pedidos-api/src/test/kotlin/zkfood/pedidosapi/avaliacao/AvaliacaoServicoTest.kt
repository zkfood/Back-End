package zkfood.pedidosapi.avaliacao

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.mockito.ArgumentMatchers.any
import org.mockito.Mockito.*
import org.modelmapper.ModelMapper
import zkfood.pedidosapi.avaliacao.avaliacaoDado.Avaliacao
import zkfood.pedidosapi.avaliacao.avaliacaoDado.AvaliacaoId
import zkfood.pedidosapi.avaliacao.dto.AvaliacaoDTO
import zkfood.pedidosapi.nucleo.enums.TipoProdutoEnum
import zkfood.pedidosapi.produto.ProdutoRepositorio
import zkfood.pedidosapi.produto.produtoDado.Produto
import zkfood.pedidosapi.usuario.usuario.UsuarioRepositorio
import zkfood.pedidosapi.usuario.usuario.usuarioDado.Usuario
import java.util.*

class AvaliacaoServicoTest {

    lateinit var avaliacaoRepositorio: AvaliacaoRepositorio
    lateinit var usuarioRepositorio: UsuarioRepositorio
    lateinit var produtoRepositorio: ProdutoRepositorio
    lateinit var mapper: ModelMapper
    lateinit var avaliacaoServico: AvaliacaoServico

    @BeforeEach
    fun iniciar(){
        avaliacaoRepositorio = mock(AvaliacaoRepositorio::class.java)
        usuarioRepositorio = mock(UsuarioRepositorio::class.java)
        produtoRepositorio = mock(ProdutoRepositorio::class.java)
        mapper = ModelMapper()
        avaliacaoServico = AvaliacaoServico(avaliacaoRepositorio, usuarioRepositorio, produtoRepositorio, mapper)
    }

    @Test
    @DisplayName("Deve lançar exceção quando usuário não for encontrado")
    fun cadastrarOuAtualizar_usuarioNaoEncontrado() {
        val usuarioId = 1
        val produtoId = 1

        // Configura o mock para retornar Optional.empty() quando o método findById do usuarioRepositorio é chamado
        `when`(usuarioRepositorio.findById(usuarioId)).thenReturn(Optional.empty())

        // Tenta chamar o método cadastrarOuAtualiazar e espera que uma RuntimeException seja lançada
        try {
            avaliacaoServico.cadastrarOuAtualiazar(usuarioId, produtoId, true, 5, "Ótimo produto")
            // Se nenhuma exceção for lançada, o teste falha com a mensagem especificada
            fail("Esperava-se uma exceção de RuntimeException, mas nenhuma foi lançada.")
        } catch (e: RuntimeException) {
            // Verifica se a mensagem da exceção lançada é "Usuário não encontrado"
            assertEquals("Usuário não encontrado", e.message)
        }
    }

    @Test
    @DisplayName("Deve lançar exceção quando produto não for encontrado")
    fun cadastrarOuAtualizar_produtoNaoEncontrado() {
        val usuarioId = 1
        val produtoId = 1
        val usuario = Usuario(id = usuarioId, nome = "João", email = "joao@example.com")

        // Configura o mock para retornar o objeto usuário quando o método findById do usuarioRepositorio é chamado
        `when`(usuarioRepositorio.findById(usuarioId)).thenReturn(Optional.of(usuario))
        // Configura o mock para retornar Optional.empty() quando o método findById do produtoRepositorio é chamado
        `when`(produtoRepositorio.findById(produtoId)).thenReturn(Optional.empty())

        // Tenta chamar o método cadastrarOuAtualiazar e espera que uma RuntimeException seja lançada
        try {
            avaliacaoServico.cadastrarOuAtualiazar(usuarioId, produtoId, true, 5, "Ótimo produto")
            // Se nenhuma exceção for lançada, o teste falha com a mensagem especificada
            fail("Esperava-se uma exceção de RuntimeException, mas nenhuma foi lançada.")
        } catch (e: RuntimeException) {
            // Verifica se a mensagem da exceção lançada é "Produto não encontrado"
            assertEquals("Produto não encontrado", e.message)
        }
    }


    @Test
    @DisplayName("Deve cadastrar ou atualizar avaliação corretamente")
    fun cadastrarOuAtualizar_avaliacaoComSucesso() {
        // Define IDs para usuário e produto
        val usuarioId = 1
        val produtoId = 1

        // Cria instâncias de Usuario e Produto para usar nos testes
        val usuario = Usuario(id = usuarioId, nome = "João", email = "joao@example.com")
        val produto = Produto(id = produtoId, nome = "Feijoada G", tipo = TipoProdutoEnum.COMIDA, valorUnitario = 55.0)

        // Cria uma instância de AvaliacaoId usando os IDs do usuário e do produto
        val avaliacaoId = AvaliacaoId(usuarioId, produtoId)

        // Cria uma instância de AvaliacaoDTO com os dados da avaliação
        val avaliacaoDTO = AvaliacaoDTO(favorito = true, qtdEstrelas = 5, descricao = "Ótimo produto")

        // Configura os mocks para retornar os objetos criados quando os métodos findById são chamados
        `when`(usuarioRepositorio.findById(usuarioId)).thenReturn(Optional.of(usuario))
        `when`(produtoRepositorio.findById(produtoId)).thenReturn(Optional.of(produto))

        // Configura o mock para retornar Optional.empty() quando o método findById do avaliacaoRepositorio é chamado
        `when`(avaliacaoRepositorio.findById(avaliacaoId)).thenReturn(Optional.empty())

        // Configura o comportamento do mock avaliacaoRepositorio para o método save.
        // Quando o método save do avaliacaoRepositorio for chamado com qualquer objeto do tipo Avaliacao,
        // ele deve retornar o próprio objeto Avaliacao que foi passado para ele.
        `when`(avaliacaoRepositorio.save(any(Avaliacao::class.java))).thenAnswer { it.getArgument(0) }

        // Chama o método cadastrarOuAtualiazar do serviço AvaliacaoServico com os parâmetros definidos
        val avaliacao = avaliacaoServico.cadastrarOuAtualiazar(usuarioId, produtoId, avaliacaoDTO.favorito, avaliacaoDTO.qtdEstrelas, avaliacaoDTO.descricao)

        // Verifica se a avaliação retornada não é nula
        assertNotNull(avaliacao)

        // Verifica se os campos da avaliação retornada correspondem aos valores esperados
        assertEquals(usuarioId, avaliacao.usuarioId)
        assertEquals(produtoId, avaliacao.produtoId)
        assertEquals(avaliacaoDTO.favorito, avaliacao.favorito)
        assertEquals(avaliacaoDTO.qtdEstrelas, avaliacao.qtdEstrelas)
        assertEquals(avaliacaoDTO.descricao, avaliacao.descricao)
    }


    @Test
    @DisplayName("Deve listar todas as avaliações")
    fun listarAvaliacoes() {
        // Cria uma lista de avaliações esperadas
        val avaliacoesEsperadas = listOf(
            Avaliacao(usuarioId = 1, produtoId = 1, favorito = true, qtdEstrelas = 5, descricao = "Ótimo produto"),
            Avaliacao(usuarioId = 2, produtoId = 1, favorito = false, qtdEstrelas = 3, descricao = "Produto mediano")
        )

        // Configura o mock para retornar a lista de avaliações esperadas
        `when`(avaliacaoRepositorio.findAll()).thenReturn(avaliacoesEsperadas)

        // Chama o método listarAvaliacoes do serviço
        val resultado = avaliacaoServico.listarAvaliacoes()

        // Verifica se o resultado não é nulo
        assertNotNull(resultado)
        // Verifica se o tamanho da lista retornada é igual ao tamanho da lista esperada
        assertEquals(avaliacoesEsperadas.size, resultado.size)
        // Verifica se o conteúdo da lista retornada é igual ao conteúdo da lista esperada
        assertEquals(avaliacoesEsperadas, resultado)
    }

    @Test
    @DisplayName("Deve retornar lista vazia quando não houver avaliações")
    fun listarAvaliacoes_vazia() {
        // Configura o mock para retornar uma lista vazia de avaliações
        `when`(avaliacaoRepositorio.findAll()).thenReturn(emptyList())

        // Chama o método listarAvaliacoes do serviço
        val resultado = avaliacaoServico.listarAvaliacoes()

        // Verifica se o resultado não é nulo
        assertNotNull(resultado)
        // Verifica se o tamanho da lista retornada é zero
        assertTrue(resultado.isEmpty())
    }


    @Test
    @DisplayName("Deve listar avaliações por usuário")
    fun listarAvaliacaoPorUsuario() {
        val usuarioId = 1

        // Cria uma lista de avaliações esperadas para o usuário especificado
        val avaliacoesEsperadas = listOf(
            Avaliacao(usuarioId = usuarioId, produtoId = 1, favorito = true, qtdEstrelas = 5, descricao = "Ótimo produto"),
            Avaliacao(usuarioId = usuarioId, produtoId = 2, favorito = false, qtdEstrelas = 4, descricao = "Bom produto")
        )

        // Configura o mock para retornar todas as avaliações
        `when`(avaliacaoRepositorio.findAll()).thenReturn(avaliacoesEsperadas)

        // Chama o método listarAvaliacaoPorUsuario do serviço
        val resultado = avaliacaoServico.listarAvaliacaoPorUsuario(usuarioId)

        // Verifica se o resultado não é nulo
        assertNotNull(resultado)
        // Verifica se o tamanho da lista retornada é igual ao tamanho da lista esperada
        assertEquals(avaliacoesEsperadas.size, resultado.size)
        // Verifica se o conteúdo da lista retornada é igual ao conteúdo da lista esperada
        assertEquals(avaliacoesEsperadas, resultado)
    }

    @Test
    @DisplayName("Deve retornar lista vazia quando não houver avaliações para o usuário")
    fun listarAvaliacaoPorUsuario_vazia() {
        val usuarioId = 1

        // Configura o mock para retornar uma lista vazia de avaliações
        `when`(avaliacaoRepositorio.findAll()).thenReturn(emptyList())

        // Chama o método listarAvaliacaoPorUsuario do serviço
        val resultado = avaliacaoServico.listarAvaliacaoPorUsuario(usuarioId)

        // Verifica se o resultado não é nulo
        assertNotNull(resultado)
        // Verifica se o tamanho da lista retornada é zero
        assertTrue(resultado.isEmpty())
    }

    @Test
    @DisplayName("Deve listar avaliação por ID")
    fun listarAvaliacaoPorId() {
        val usuarioId = 1
        val produtoId = 1

        // Cria uma avaliação esperada
        val avaliacaoEsperada = Avaliacao(usuarioId = usuarioId, produtoId = produtoId, favorito = true, qtdEstrelas = 5, descricao = "Ótimo produto")

        // Configura o mock para retornar a avaliação esperada
        `when`(avaliacaoRepositorio.findById(AvaliacaoId(usuarioId, produtoId))).thenReturn(Optional.of(avaliacaoEsperada))

        // Chama o método listarAvaliacaoPorId do serviço
        val resultado = avaliacaoServico.listarAvaliacaoPorId(usuarioId, produtoId)

        // Verifica se o resultado não é nulo
        assertNotNull(resultado)
        // Verifica se o resultado retornado é igual à avaliação esperada
        assertEquals(avaliacaoEsperada, resultado)
    }

    @Test
    @DisplayName("Deve retornar null quando não houver avaliação com o ID especificado")
    fun listarAvaliacaoPorId_vazia() {
        val usuarioId = 1
        val produtoId = 1

        // Configura o mock para retornar Optional.empty()
        `when`(avaliacaoRepositorio.findById(AvaliacaoId(usuarioId, produtoId))).thenReturn(Optional.empty())

        // Chama o método listarAvaliacaoPorId do serviço
        val resultado = avaliacaoServico.listarAvaliacaoPorId(usuarioId, produtoId)

        // Verifica se o resultado é nulo
        assertNull(resultado)
    }

    @Test
    @DisplayName("Deve deletar avaliação por ID")
    fun deletar() {
        val usuarioId = 1
        val produtoId = 1

        // Cria uma avaliação esperada
        val avaliacaoEsperada = Avaliacao(usuarioId = usuarioId, produtoId = produtoId, favorito = true, qtdEstrelas = 5, descricao = "Ótimo produto")

        // Configura o mock para retornar a avaliação esperada
        `when`(avaliacaoRepositorio.findById(AvaliacaoId(usuarioId, produtoId))).thenReturn(Optional.of(avaliacaoEsperada))

        // Chama o método deletar do serviço
        avaliacaoServico.deletar(usuarioId, produtoId)

        // Verifica se o método deleteById do mock avaliacaoRepositorio foi chamado com o AvaliacaoId esperado
        verify(avaliacaoRepositorio).deleteById(AvaliacaoId(usuarioId, produtoId))
    }
}