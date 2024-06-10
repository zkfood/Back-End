package zkfood.pedidosapi.pedidos.pedido.pedidoDado

import zkfood.pedidosapi.nucleo.dtos.EnderecoSimplesRespostaDto
import zkfood.pedidosapi.nucleo.dtos.EstadoPedidoHistoricoSimpesRespostaDto
import zkfood.pedidosapi.nucleo.dtos.TelefoneSimplesRespostaDto
import zkfood.pedidosapi.nucleo.dtos.UsuarioSimplesRespostaDto
import zkfood.pedidosapi.produtos.produtoDado.ProdutoSimplesRespostaDto

data class PedidoCompletoResposta (
    var id: Int? = null,

    var estado:String? = null,

    var estadoPedidoHistorico:MutableList<EstadoPedidoHistoricoSimpesRespostaDto>? = null,

    var produtos:MutableList<ProdutoSimplesRespostaDto>? = null,

    var numeroMesa:String? = null,

    var delivery:Double? = null,

    var formaPagamento:String? = null,

    var tipoEntrega:String? = null,

    var usuario:UsuarioSimplesRespostaDto? = null,

    var colaborador:UsuarioSimplesRespostaDto? = null,

    var telefone: TelefoneSimplesRespostaDto? = null,

    var endereco: EnderecoSimplesRespostaDto? = null
)