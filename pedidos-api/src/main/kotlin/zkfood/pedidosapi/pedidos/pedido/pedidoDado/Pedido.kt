package zkfood.pedidosapi.pedidos.pedido.pedidoDado

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

@Entity
data class Pedido (
    @field:Id @field:GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null,

    var estado:String? = null,

    var numeroMesa:String? = null,

    var delivery:Double? = null,

    var formaPagamento:String? = null,

    var tipoEntrega:String? = null,

    var usuario:Int? = null,

    var colaborador:Int? = null,

    var telefone:Int? = null,

    var endereco:Int? = null
)