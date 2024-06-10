package zkfood.pedidosapi.produtos.tipoProduto

import org.modelmapper.ModelMapper
import org.springframework.stereotype.Service
import zkfood.pedidosapi.nucleo.CrudServico
import zkfood.pedidosapi.nucleo.enums.IgnorarFormatacaoEnum
import zkfood.pedidosapi.produtos.tipoProduto.tipoDado.TipoProdudo
import zkfood.pedidosapi.produtos.tipoProduto.tipoDado.TipoCadastro

@Service
class TipoProdutoServico(
    val tipoProdutoRepositorio: TipoProdutoRepositorio,
    val mapper:ModelMapper
):CrudServico<TipoProdudo>(tipoProdutoRepositorio){
    fun cadastrar(novoTipo:TipoCadastro): TipoProdudo {
        val tipoProdudo:TipoProdudo = mapper.map(novoTipo, TipoProdudo::class.java);

        val filtro = TipoProdudo(nome = novoTipo.nome);
        val cadastro: TipoProdudo = super.cadastrar(tipoProdudo, filtro);

        return cadastro;
    }

    fun listarProduto (tipo:String): TipoProdudo {
        val filtro = TipoProdudo(nome = tipo);

        val tipoProduto = super.listarEntidade(filtro, IgnorarFormatacaoEnum.INATIVO);

        return tipoProduto[0];
    }
}