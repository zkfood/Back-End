package zkfood.pedidosapi.produtos.tipoProduto

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.mockito.Mockito.*
import org.modelmapper.ModelMapper
import zkfood.pedidosapi.produtos.tipoProduto.tipoDado.TipoProdudo
import zkfood.pedidosapi.produtos.tipoProduto.tipoDado.TipoCadastro

class TipoProdudoServicoTest {

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

        `when`(tipoProdutoRepositorio.save(any(TipoProdudo::class.java))).thenAnswer{
            invocation -> val tipoProdudo = invocation.getArgument<TipoProdudo>(0)
            tipoProdudo.id = 1
            tipoProdudo
        }

        val resultado = tipoProdutoServico.cadastrar(tipoCadastro)

        assert(resultado.nome == tipoCadastro.nome)
    }

    @Test
    @DisplayName("Listagem de tipos")
    fun listar() {
        val tipoProdudo1 = TipoProdudo(id = 1, nome = "Tipo 1")
        val tipoProdudo2 = TipoProdudo(id = 2, nome = "Tipo 2")
        val tipos = listOf(tipoProdudo1, tipoProdudo2)

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