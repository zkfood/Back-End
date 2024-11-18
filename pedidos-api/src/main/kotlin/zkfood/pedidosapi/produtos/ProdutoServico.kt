package zkfood.pedidosapi.produtos

import org.modelmapper.ModelMapper
import org.springframework.stereotype.Service
import zkfood.pedidosapi.nucleo.CrudServico
import zkfood.pedidosapi.nucleo.enums.IgnorarFormatacaoEnum
import zkfood.pedidosapi.nucleo.erros.DadoDuplicadoExcecao
import zkfood.pedidosapi.produtos.produtoDado.Produto
import zkfood.pedidosapi.produtos.produtoDado.ProdutoCadastro
import zkfood.pedidosapi.produtos.tipoProduto.TipoProdutoServico
import java.time.LocalDateTime

@Service
class ProdutoServico (
    val produtoRepositorio: ProdutoRepositorio,
    val mapper: ModelMapper,
    val tipoProdutoServico: TipoProdutoServico
):CrudServico<Produto>(produtoRepositorio){
    fun cadastrarProduto(novoProduto: ProdutoCadastro):Produto{
        val produto: Produto = mapper.map(novoProduto, Produto::class.java);

        val cadastro:Produto = this.cadastrar(produto, null);

        return cadastro;
    }

    override fun cadastrar(dto: Produto, exemplo: Produto?): Produto {
        if (exemplo != null) {
            val estaDuplicado: Boolean = repositorio.exists(super.combinadorFiltro(exemplo, IgnorarFormatacaoEnum.INATIVO));
            if (estaDuplicado) throw DadoDuplicadoExcecao(exemplo, super.getEntidade(dto));
        }
        val cadastro = repositorio.save(dto);

        return cadastro;
    }

    fun listarProdutos(tipo:String?, disponibilidade:Boolean?):List<Produto>{
        val filtro = Produto();

        if (tipo != null) {
            if (tipo == "PratoDoDia") {
                filtro.pratoDoDia = LocalDateTime.now().dayOfWeek.value + 1;
            } else {
                filtro.tipoProduto = tipoProdutoServico.listarProduto(tipo).id;
            }
        }
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