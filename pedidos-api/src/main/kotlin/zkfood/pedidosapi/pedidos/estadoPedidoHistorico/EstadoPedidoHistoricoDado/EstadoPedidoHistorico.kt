package zkfood.pedidosapi.pedidos.estadoPedidoHistorico.EstadoPedidoHistoricoDado

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.ManyToOne
import zkfood.pedidosapi.pedidos.pedido.pedidoDado.Pedido
import java.time.LocalDateTime

@Entity
data class EstadoPedidoHistorico(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null,

    var estado: String? = null,

    var hora: LocalDateTime? = null,

    @Column(name = "pedido_id")
    var pedido: Int? = null
)