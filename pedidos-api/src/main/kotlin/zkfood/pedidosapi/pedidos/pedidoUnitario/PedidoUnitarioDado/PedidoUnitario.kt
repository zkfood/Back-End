package zkfood.pedidosapi.pedidos.pedidoUnitario.PedidoUnitarioDado


import jakarta.persistence.*

@Entity
data class PedidoUnitario (
    @field:Id @field:GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null,

    @Column(name = "pedido_id")
    var pedido: Int? = null,

    @Column(name = "produto_id")
    var produto: Int? = null,

    var quantidade: Int? = null,

    var observacao: String? = null,

    var entregue: Boolean = false,
)