package zkfood.pedidosapi.entregasMotoboy

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import zkfood.pedidosapi.entregasMotoboy.entregasMotoboyData.EntregasMotoboy

@Repository
interface EntregasMotoboyRepositorio: JpaRepository<EntregasMotoboy, Int>{
    @Query(
        """
        SELECT *
        FROM entregas_motoboy as e
        WHERE MONTH(e.data) = :mes AND YEAR(e.data) = :ano
        """, nativeQuery = true
    )
    fun motoboyMesAno(mes: Int, ano: Int): List<EntregasMotoboy>
}