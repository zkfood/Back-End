package zkfood.pedidosapi.produto

import org.modelmapper.ModelMapper
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException
import zkfood.pedidosapi.nucleo.CrudServico
import zkfood.pedidosapi.nucleo.erros.NaoEncontradoPorIdExcecao
import zkfood.pedidosapi.produto.produtoDado.NovoValor
import zkfood.pedidosapi.produto.produtoDado.Produto
import zkfood.pedidosapi.produto.produtoDado.ProdutoCadastro
import java.util.*

@Service
class ProdutoServico(
    val produtoRepositorio: ProdutoRepositorio,
    val mapper: ModelMapper = ModelMapper()
):CrudServico<Produto>(produtoRepositorio){

    fun cadastrar(novoProduto: ProdutoCadastro):Produto{
        val produtoDto: Produto = mapper.map(novoProduto, Produto::class.java);

        val filtro = Produto(nome = novoProduto.nome);
        val cadastro = super.cadastrar(produtoDto, filtro);

        return cadastro

    }

    fun listarProdutos():List<Produto>{
        val lista = produtoRepositorio.findAll()

        return lista
    }

    fun buscarPorId(id:Int): Optional<Produto> {
        val produto = produtoRepositorio.findById(id)

        return produto
    }

    fun alterarValorUnitario(id:Int, novoValor:NovoValor):Produto{
        val produto = produtoRepositorio.findById(id).orElseThrow {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, "Produto não encontrado")
        }
        produto.valorUnitario = novoValor.valor

        val produtoAtualizado:Produto = super.atualizar(produto.id!!, produto);

        return produtoAtualizado
    }

    fun deletarProduto(id:Int){
        super.deletarPorId(id)
    }

    fun buscarProdutoPorId(id: Int): Produto {
        return produtoRepositorio.findById(id).orElseThrow {
            ResponseStatusException(HttpStatus.NOT_FOUND, "Produto não encontrado")
        }
    }
}