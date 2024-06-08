package zkfood.pedidosapi.produtos

import org.modelmapper.ModelMapper
import org.springframework.stereotype.Service
import zkfood.pedidosapi.nucleo.CrudServico
import zkfood.pedidosapi.nucleo.erros.NaoEncontradoPorIdExcecao
import zkfood.pedidosapi.produtos.produtoDado.NovoValorDTO
import zkfood.pedidosapi.produtos.produtoDado.Produto
import zkfood.pedidosapi.produtos.produtoDado.ProdutoCadastro
import zkfood.pedidosapi.produtos.tipo.TipoServico
import zkfood.pedidosapi.produtos.tipo.tipoDado.Tipo

@Service
class ProdutoServico (
    val produtoRepositorio: ProdutoRepositorio,
    val mapper: ModelMapper,
    val tipoServico: TipoServico
):CrudServico<Produto>(produtoRepositorio){

    private fun validarTipo(idTipoParametro:Int): Tipo {
        return tipoServico.acharPorId(idTipoParametro);
    }

    fun cadastrarProduto(novoProduto: ProdutoCadastro, idTipo: Int):Produto{
        val tipoEncontrado = validarTipo(idTipo)

        val produto: Produto = mapper.map(novoProduto, Produto::class.java)

        produto.tipo = tipoEncontrado

        val cadastro:Produto = super.cadastrar(produto, null)

        return cadastro
    }

    fun listarProdutos():List<Produto>{
        val listaProdutos: List<Produto> = produtoRepositorio.findAll()

        return listaProdutos
    }

    fun listarProdutosPorTipo(idTipo:Int): List<Produto>{
        val tipo:Tipo = validarTipo(idTipo)

        val listaProduto: List<Produto> = produtoRepositorio.findByTipoId(idTipo)

        return listaProduto
    }

    fun listarProdutoPorId(idProduto:Int):Produto{
        val produto: Produto = super.acharPorId(idProduto)

        return produto
    }

    fun listarProdutosDisponiveis():List<Produto>{
        val listaDisponiveis: List<Produto> = produtoRepositorio.findByDisponibilidadeTrue()

        return listaDisponiveis
    }

    fun listarProdutosIndisponiveis():List<Produto>{
        val listaIndisponiveis: List<Produto> = produtoRepositorio.findByDisponibilidadeTrue()

        return listaIndisponiveis
    }

    fun listarProdutosDisponiveisPorTipo(idTipo:Int):List<Produto>{
        val listaDisponiveis:List<Produto> = produtoRepositorio.findByDisponibilidadeTrueAndTipoId(idTipo)

        return listaDisponiveis
    }

    fun listarProdutosIndisponiveisPorTipo(idTipo:Int):List<Produto>{
        val listaIndisponiveis:List<Produto> = produtoRepositorio.findByDisponibilidadeFalseAndTipoId(idTipo)

        return listaIndisponiveis
    }

    fun atualizarValorProduto(idProduto:Int, dto:NovoValorDTO):Produto{
        val produto = produtoRepositorio.findById(idProduto).orElseThrow{
            throw RuntimeException("Produto não encontrado com id: $idProduto")
        }

        produto.valorUnitario = dto.valoUnitario

        return produto
    }

    fun deletarProduto(idProduto:Int){
        super.deletarPorId(idProduto)
    }
}