package zkfood.pedidosapi.pedidos.pedido.pedidoDado

import jakarta.persistence.*

@Entity
data class Pedido (
    @field:Id @field:GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null,

    var estado:String? = null,

    @Column(name = "numero_mesa", columnDefinition = "CHAR(3)")
    var numeroMesa:String? = null,

    var delivery:Double? = null,

    var formaPagamento:String? = null,

    var motivoCancelamento:String? = null,

    var tipoEntrega:String? = null,

    @Column(name = "usuario_id")
    var usuario:Int? = null,

//    var colaborador:Int? = null,

    @Column(name = "telefone_id")
    var telefone:Int? = null,

    @Column(name = "endereco_id")
    var endereco:Int? = null
)