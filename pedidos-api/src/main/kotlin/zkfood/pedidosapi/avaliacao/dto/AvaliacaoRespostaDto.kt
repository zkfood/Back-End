package zkfood.pedidosapi.avaliacao.dto

import zkfood.pedidosapi.avaliacao.avaliacaoDado.AvaliacaoId
import zkfood.pedidosapi.produtos.produtoDado.ProdutoSimplesRespostaDto

data class AvaliacaoRespostaDto (
    var id: AvaliacaoId? = null,

    var produto: ProdutoSimplesRespostaDto? = null,

    var favorito: Boolean = false,

    var qtdEstrelas: Int? = null,

    var descricao: String? = null
)