package zkfood.pedidosapi.pedidos.estadoPedidoHistorico.EstadoPedidoHistoricoDado

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import java.time.LocalDateTime

@Entity
data class EstadoPedidoHistorico(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null,

    var estado: String? = null,

    var hora: LocalDateTime? = null,

    var pedido: Int? = null
)