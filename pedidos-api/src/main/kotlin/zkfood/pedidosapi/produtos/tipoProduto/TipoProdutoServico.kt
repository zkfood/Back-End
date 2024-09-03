package zkfood.pedidosapi.produtos.tipoProduto

import org.modelmapper.ModelMapper
import org.springframework.stereotype.Service
import zkfood.pedidosapi.nucleo.CrudServico
import zkfood.pedidosapi.nucleo.enums.IgnorarFormatacaoEnum
import zkfood.pedidosapi.produtos.tipoProduto.tipoDado.TipoProduto
import zkfood.pedidosapi.produtos.tipoProduto.tipoDado.TipoCadastro

@Service
class TipoProdutoServico(
    val tipoProdutoRepositorio: TipoProdutoRepositorio,
    val mapper:ModelMapper
):CrudServico<TipoProduto>(tipoProdutoRepositorio){
    fun cadastrar(novoTipo:TipoCadastro): TipoProduto {
        val tipoProduto:TipoProduto = mapper.map(novoTipo, TipoProduto::class.java);

        val filtro = TipoProduto(nome = novoTipo.nome);
        val cadastro: TipoProduto = super.cadastrar(tipoProduto, filtro);

        return cadastro;
    }

    fun listarProduto (tipo:String): TipoProduto {
        val filtro = TipoProduto(nome = tipo);

        val tipoProduto = super.listarEntidade(filtro, IgnorarFormatacaoEnum.INATIVO);

        return tipoProduto[0];
    }
}