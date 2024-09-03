package zkfood.pedidosapi.pedidos.pedido

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import zkfood.pedidosapi.pedidos.pedido.pedidoDado.Pedido

@Repository
interface PedidoRepositorio: JpaRepository<Pedido, Int>