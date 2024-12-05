package zkfood.pedidosapi.relatorios

import org.springframework.stereotype.Service
import zkfood.pedidosapi.nucleo.utilidade.ListaUtil
import zkfood.pedidosapi.produtos.ProdutoRepositorio

@Service
class RelatorioServico(
    val produtosRepositorio: ProdutoRepositorio,
) {
    fun dashboardProdutos(): List<Any> {
        val listaDashboardProdutos = produtosRepositorio.dashboardProdutos();
        ListaUtil().validarLista(listaDashboardProdutos);

//        val listaDashboardProdutosDtoRespostaDtos = listaDashboardProdutos.mapNotNull {
//            if (it is Array<*> && it.size == 2 && it[0] is String && it[1] is Int) {
//                val nome = it[0] as String;
//                val quantidadeVenda = it[1] as Int;
//                DashboardProdutosRespostaDto(nome, quantidadeVenda);
//            } else {
//                null;
//            }
//        }

        return listaDashboardProdutos;
    }

    fun kpisProdutos(): Any {
        val listaDashboardProdutos = this.dashboardProdutos();

        val top3Melhores = listaDashboardProdutos.take(3);
        val top3Piores = listaDashboardProdutos.takeLast(3);

        val objetoAny = object {
            val top3Melhores = top3Melhores
            val top3Piores = top3Piores
        }

//        val listaKipsProdutos = KpisProdutosRespostaDto(
//            top3Melhores = top3Melhores,
//            top3Piores = top3Piores
//        );

        return objetoAny
    }
}