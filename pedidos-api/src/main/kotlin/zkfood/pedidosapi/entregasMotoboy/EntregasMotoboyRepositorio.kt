package zkfood.pedidosapi.entregasMotoboy

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import zkfood.pedidosapi.entregasMotoboy.entregasMotoboyData.EntregasMotoboy

@Repository
interface EntregasMotoboyRepositorio: JpaRepository<EntregasMotoboy, Int>