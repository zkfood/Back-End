package zkfood.pedidosapi.usuario.telefone

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import zkfood.pedidosapi.usuario.telefone.telefoneDado.Telefone

@Repository
interface TelefoneRepositorio: JpaRepository<Telefone, Int> {
    fun findByUsuarioId(id: Int?): List<Telefone>
}