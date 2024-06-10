package zkfood.pedidosapi.pedidos.pedidoUnitario

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import zkfood.pedidosapi.pedidos.pedidoUnitario.PedidoUnitarioDado.ChaveCompostaPedidoUnitario
import zkfood.pedidosapi.pedidos.pedidoUnitario.PedidoUnitarioDado.PedidoUnitario

@Repository
interface PedidoUnitarioRepositorio: JpaRepository<PedidoUnitario,ChaveCompostaPedidoUnitario> {
    fun findByIdPedido(id:Int): List<PedidoUnitario>;
}