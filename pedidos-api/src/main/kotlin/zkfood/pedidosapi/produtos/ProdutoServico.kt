package zkfood.pedidosapi.produtos

import org.modelmapper.ModelMapper
import org.springframework.stereotype.Service
import zkfood.pedidosapi.nucleo.CrudServico
import zkfood.pedidosapi.nucleo.enums.IgnorarFormatacaoEnum
import zkfood.pedidosapi.produtos.produtoDado.Produto
import zkfood.pedidosapi.produtos.produtoDado.ProdutoCadastro
import zkfood.pedidosapi.produtos.tipoProduto.TipoProdutoServico

@Service
class ProdutoServico (
    val produtoRepositorio: ProdutoRepositorio,
    val mapper: ModelMapper,
    val tipoProdutoServico: TipoProdutoServico
):CrudServico<Produto>(produtoRepositorio){
    fun cadastrarProduto(novoProduto: ProdutoCadastro):Produto{
        val produto: Produto = mapper.map(novoProduto, Produto::class.java);

        val cadastro:Produto = super.cadastrar(produto, null);

        return cadastro;
    }

    fun listarProdutos(tipo:String?, disponibilidade:Boolean?):List<Produto>{
        val filtro = Produto();

        if (tipo != null) filtro.tipoProduto = tipoProdutoServico.listarProduto(tipo).id;
        if (disponibilidade != null) filtro.disponibilidade = disponibilidade;

        val listaProdutos: List<Produto> = super.listarEntidade(filtro, IgnorarFormatacaoEnum.INATIVO);

        return listaProdutos;
    }

    fun cadastrarImagem(id: Int, novaImagem: ByteArray) {
        super.atualizar(id, Produto(imagem = novaImagem));
    }

    fun recuperarImagem(id:Int):ByteArray {
        val imagem = produtoRepositorio.recuperarImagem(id);

        return imagem;
    }
}