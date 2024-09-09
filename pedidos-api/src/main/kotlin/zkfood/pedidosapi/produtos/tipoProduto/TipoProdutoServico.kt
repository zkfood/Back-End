package zkfood.pedidosapi.produtos.tipoProduto

import org.modelmapper.ModelMapper
import org.springframework.stereotype.Service
import zkfood.pedidosapi.nucleo.CrudServico
import zkfood.pedidosapi.nucleo.enums.IgnorarFormatacaoEnum
import zkfood.pedidosapi.nucleo.erros.DadoDuplicadoExcecao
import zkfood.pedidosapi.produtos.tipoProduto.tipoDado.TipoProduto
import zkfood.pedidosapi.produtos.tipoProduto.tipoDado.TipoCadastro

@Service
class TipoProdutoServico(
    val tipoProdutoRepositorio: TipoProdutoRepositorio,
    val mapper:ModelMapper
):CrudServico<TipoProduto>(tipoProdutoRepositorio){
    fun cadastrarDeDTO(novoTipo:TipoCadastro): TipoProduto {
        val tipoProduto:TipoProduto = mapper.map(novoTipo, TipoProduto::class.java);

        val filtro = TipoProduto(nome = novoTipo.nome);
        val cadastro: TipoProduto = this.cadastrar(tipoProduto, filtro);

        return cadastro;
    }

    override fun cadastrar(dto: TipoProduto, exemplo: TipoProduto?): TipoProduto {
        if (exemplo != null) {
            val estaDuplicado: Boolean = repositorio.exists(super.combinadorFiltro(exemplo, IgnorarFormatacaoEnum.INATIVO));
            if (estaDuplicado) throw DadoDuplicadoExcecao(exemplo, super.getEntidade(dto));
        }
        val cadastro = repositorio.save(dto);

        return cadastro;
    }

    fun listarProduto (tipo:String): TipoProduto {
        val filtro = TipoProduto(nome = tipo);

        val tipoProduto = super.listarEntidade(filtro, IgnorarFormatacaoEnum.INATIVO);

        return tipoProduto[0];
    }
}