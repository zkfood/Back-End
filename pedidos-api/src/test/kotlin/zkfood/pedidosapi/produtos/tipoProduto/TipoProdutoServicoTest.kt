package zkfood.pedidosapi.produtos.tipoProduto

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.mockito.Mockito.*
import org.modelmapper.ModelMapper
import zkfood.pedidosapi.produtos.tipoProduto.tipoDado.TipoProduto
import zkfood.pedidosapi.produtos.tipoProduto.tipoDado.TipoCadastro

class TipoProdutoServicoTest {

    lateinit var tipoProdutoServico: TipoProdutoServico
    lateinit var mapper:ModelMapper
    lateinit var tipoProdutoRepositorio: TipoProdutoRepositorio

    @BeforeEach
    fun iniciar(){
        mapper = ModelMapper()
        tipoProdutoRepositorio = mock(TipoProdutoRepositorio::class.java)
        tipoProdutoServico = TipoProdutoServico(tipoProdutoRepositorio, mapper)
    }
    @Test
    @DisplayName("Cadastro com sucesso")
    fun cadastrar() {
        val tipoCadastro = TipoCadastro(nome = "Comida")

        `when`(tipoProdutoRepositorio.save(any(TipoProduto::class.java))).thenAnswer{
            invocation -> val tipoProduto = invocation.getArgument<TipoProduto>(0)
            tipoProduto.id = 1
            tipoProduto
        }

        val resultado = tipoProdutoServico.cadastrar(tipoCadastro)

        assert(resultado.nome == tipoCadastro.nome)
    }

    @Test
    @DisplayName("Listagem de tipos")
    fun listar() {
        val tipoProduto1 = TipoProduto(id = 1, nome = "Tipo 1")
        val tipoProduto2 = TipoProduto(id = 2, nome = "Tipo 2")
        val tipos = listOf(tipoProduto1, tipoProduto2)

        `when`(tipoProdutoRepositorio.findAll()).thenReturn(tipos)

        val listaObtida = tipoProdutoServico.listar()

        assert(listaObtida == tipos)
    }

    @Test
    @DisplayName("Listagem de tipos - Lista vazia")
    fun listar_listaVazia() {

        `when`(tipoProdutoRepositorio.findAll()).thenReturn(emptyList())

        val listaObtida = tipoProdutoServico.listar()

        assert(listaObtida.isEmpty())
    }
}