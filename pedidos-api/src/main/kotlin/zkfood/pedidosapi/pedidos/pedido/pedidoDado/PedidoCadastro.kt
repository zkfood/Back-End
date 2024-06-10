package zkfood.pedidosapi.pedidos.pedido.pedidoDado

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotEmpty
import zkfood.pedidosapi.pedidos.pedidoUnitario.PedidoUnitarioDado.ProdutoUnitarioCadastro
import zkfood.pedidosapi.usuario.usuario.usuarioDado.Usuario

data class PedidoCadastro(
    var numeroMesa:String? = null,

    var delivery:Double? = null,

    @field:NotBlank
    var formaPagamento:String,

    @field:NotBlank
    var tipoEntrega:String,

    @field:NotEmpty
    var produtos:List<ProdutoUnitarioCadastro>,

    var usuario:Int? = null,

    var colaborador:Int? = null,

    var telefone:Int? = null,

    var endereco:Int? = null
)