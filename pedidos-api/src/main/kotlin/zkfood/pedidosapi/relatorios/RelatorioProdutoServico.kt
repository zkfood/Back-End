package zkfood.pedidosapi.relatorios

import org.springframework.stereotype.Service
import zkfood.pedidosapi.produtos.ProdutoRepositorio

@Service
class RelatorioProdutoServico(
    val produtoRepositorio: ProdutoRepositorio
) {
//    fun PratosMaisVendidosGrafico(): PratoMaisVendidosGraficoDto {
//        val listaProdutos = ?
//
//        return listaProdutos;
//    }
}