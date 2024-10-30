package zkfood.pedidosapi.pedidos.pedidoUnitario

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import zkfood.pedidosapi.pedidos.pedidoUnitario.PedidoUnitarioDado.PedidoUnitario

@Repository
interface PedidoUnitarioRepositorio: JpaRepository<PedidoUnitario, Int> {
    fun findByPedido(id:Int): List<PedidoUnitario>;
}