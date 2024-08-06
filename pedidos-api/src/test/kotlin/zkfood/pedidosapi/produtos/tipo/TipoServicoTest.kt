package zkfood.pedidosapi.produtos.tipo

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.mockito.Mockito.*
import org.modelmapper.ModelMapper
import zkfood.pedidosapi.produtos.tipo.tipoDado.Tipo
import zkfood.pedidosapi.produtos.tipo.tipoDado.TipoCadastro

class TipoServicoTest {

    lateinit var tipoServico: TipoServico
    lateinit var mapper:ModelMapper
    lateinit var tipoRepositorio: TipoRepositorio

    @BeforeEach
    fun iniciar(){
        mapper = ModelMapper()
        tipoRepositorio = mock(TipoRepositorio::class.java)
        tipoServico = TipoServico(tipoRepositorio, mapper)
    }
    @Test
    @DisplayName("Cadastro com sucesso")
    fun cadastrar() {
        val tipoCadastro = TipoCadastro(nome = "Comida")

        `when`(tipoRepositorio.save(any(Tipo::class.java))).thenAnswer{
            invocation -> val tipo = invocation.getArgument<Tipo>(0)
            tipo.id = 1
            tipo
        }

        val resultado = tipoServico.cadastrar(tipoCadastro)

        assert(resultado.nome == tipoCadastro.nome)
    }

    @Test
    @DisplayName("Listagem de tipos")
    fun listar() {
        val tipo1 = Tipo(id = 1, nome = "Tipo 1")
        val tipo2 = Tipo(id = 2, nome = "Tipo 2")
        val tipos = listOf(tipo1, tipo2)

        `when`(tipoRepositorio.findAll()).thenReturn(tipos)

        val listaObtida = tipoServico.listar()

        assert(listaObtida == tipos)
    }

    @Test
    @DisplayName("Listagem de tipos - Lista vazia")
    fun listar_listaVazia() {

        `when`(tipoRepositorio.findAll()).thenReturn(emptyList())

        val listaObtida = tipoServico.listar()

        assert(listaObtida.isEmpty())
    }
}