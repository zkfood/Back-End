package zkfood.pedidosapi.produto

import org.junit.jupiter.api.Test


import org.modelmapper.ModelMapper
import org.springframework.http.HttpStatusCode
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.RequestParam
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvFileSource
import org.mockito.Mockito
import org.mockito.Mockito.*
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.web.server.ResponseStatusException
import zkfood.pedidosapi.nucleo.enums.TipoProdutoEnum
import zkfood.pedidosapi.nucleo.erros.NaoEncontradoPorIdExcecao
import zkfood.pedidosapi.produto.produtoDado.NovoValor
import zkfood.pedidosapi.produto.produtoDado.Produto
import zkfood.pedidosapi.produto.produtoDado.ProdutoCadastro
import java.util.*

@SpringBootTest
class ProdutoServicoTest {

    lateinit var produtoRepositorio: ProdutoRepositorio
    lateinit var produtoServico: ProdutoServico

    @BeforeEach
    fun iniciar(){
        produtoRepositorio = mock(ProdutoRepositorio::class.java)
        produtoServico = ProdutoServico(produtoRepositorio)
    }

    @DisplayName("Se houver produto na tabela, deve retornar os mesmos elementos")
    @Test
    fun listarProdutos() {
        val listaEsperada = listOf(Produto(id = 1, nome = "Feijoada G", tipo = TipoProdutoEnum.COMIDA, disponibilidade = true, descricao = "Feijoada que serve até 3 pessoas", valorUnitario = 70.00, qtdPessoas = 3))
        `when`(produtoRepositorio.findAll()).thenReturn(listaEsperada)

        val resultado = produtoServico.listarProdutos()

        assertEquals(listaEsperada.size, resultado.size)
        assertEquals(listaEsperada, resultado)
    }

    @DisplayName("Se a tabela estiver vazia, deve retornar uma lista vazia")
    @Test
    fun listarProdutosVazia() {
        `when`(produtoRepositorio.findAll()).thenReturn(emptyList())

        val resultado = produtoServico.listarProdutos()

        assertTrue(resultado.isEmpty())
    }

    @Test
    fun cadastrar(){
        // Dado um novo produto a ser cadastrado
        val novoProduto = ProdutoCadastro(
            nome = "Feijoada G",
            tipo = TipoProdutoEnum.COMIDA,
            disponibilidade = true,
            descricao = "Feijoada que serve até 3 pessoas",
            valorUnitario = 70.00,
            qtdPessoas = 3
        )

        // Mock do produto retornado pelo repositório após o cadastro
        val produtoCadastrado = Produto(
            id = 1,
            nome = "Feijoada G",
            tipo = TipoProdutoEnum.COMIDA,
            disponibilidade = true,
            descricao = "Feijoada que serve até 3 pessoas",
            valorUnitario = 70.00,
            qtdPessoas = 3
        )

        // Configurar o comportamento do mock do repositório
        `when`(produtoRepositorio.save(any(Produto::class.java))).thenReturn(produtoCadastrado)

        // Executar o método cadastrar do serviço
        val produtoRetornado = produtoServico.cadastrar(novoProduto)

        // Verificar se o produto retornado corresponde ao esperado
        assertEquals(produtoCadastrado, produtoRetornado)
    }

    @DisplayName("Se houver produto na tabela, deve retornar o elemento do determinado id")
    @Test
    fun buscarPorId() {
        val idExistente = 1
        val produtoEsperado = Produto(id = idExistente, nome = "Feijoada G", tipo = TipoProdutoEnum.COMIDA, disponibilidade = true, descricao = "Feijoada que serve até 3 pessoas", valorUnitario = 70.00, qtdPessoas = 3)

        `when`(produtoRepositorio.findById(idExistente)).thenReturn(Optional.of(produtoEsperado))

        val resultado = produtoServico.buscarProdutoPorId(idExistente)

        assertEquals(produtoEsperado, resultado)
    }

    @DisplayName("Quando o produto com ID fornecido não existe, deve lançar ResponseStatusException")
    @Test
    fun buscarProdutoPorId_ProdutoNaoExistente() {
        val idInexistente = 2

        `when`(produtoRepositorio.findById(idInexistente)).thenReturn(Optional.empty())

        val exception = assertThrows(ResponseStatusException::class.java) {
            produtoServico.buscarProdutoPorId(idInexistente)
        }

        assertEquals("Produto não encontrado", exception.reason)
    }

    @DisplayName("Quando o produto com ID fornecido existe, deve alterar o valor unitário do produto")
    @Test
    fun alterarValorUnitario_ProdutoExistente() {
        val idExistente = 1
        val produtoExistente = Produto(id = idExistente, nome = "Feijoada G", valorUnitario = 70.00)
        val novoValor = NovoValor(valor = 80.00)

        `when`(produtoRepositorio.findById(idExistente)).thenReturn(Optional.of(produtoExistente))
        `when`(produtoRepositorio.save(any(Produto::class.java))).thenAnswer { it.getArgument(0) }

        val produtoAtualizado = produtoServico.alterarValorUnitario(idExistente, novoValor)

        assertEquals(novoValor.valor, produtoAtualizado.valorUnitario)
    }

    @DisplayName("Quando o produto com ID fornecido não existe, deve lançar ResponseStatusException")
    @Test
    fun alterarValorUnitario_ProdutoNaoExistente() {
        val idInexistente = 2
        val novoValor = NovoValor(valor = 80.00)

        `when`(produtoRepositorio.findById(idInexistente)).thenReturn(Optional.empty())

        val exception = assertThrows(ResponseStatusException::class.java) {
            produtoServico.alterarValorUnitario(idInexistente, novoValor)
        }

        assertEquals("Produto não encontrado", exception.reason)
    }

    @DisplayName("Deve deletar produto com sucesso")
    @Test
    fun deletarProduto_DeveDeletarComSucesso() {
        val produtoId = 1
        val produto = Produto(id = produtoId, nome = "Produto Teste", tipo = TipoProdutoEnum.COMIDA, disponibilidade = true, descricao = "Descrição Teste", valorUnitario = 10.0, qtdPessoas = 2)

        `when`(produtoRepositorio.findById(produtoId)).thenReturn(Optional.of(produto))
        Mockito.doNothing().`when`(produtoRepositorio).deleteById(produtoId)

        produtoServico.deletarProduto(produtoId)

        Mockito.verify(produtoRepositorio).deleteById(produtoId)
    }

    @DisplayName("Deve lançar exceção ao tentar deletar produto inexistente")
    @Test
    fun deletarProduto_DeveLancarExcecaoParaProdutoInexistente() {
        val produtoId = 1

        `when`(produtoRepositorio.findById(produtoId)).thenReturn(Optional.empty())

        val excecao = assertThrows<NaoEncontradoPorIdExcecao> {
            produtoServico.deletarProduto(produtoId)
        }

        assertEquals("Entidade com $produtoId não encontrada!", excecao.message)
    }
    
}