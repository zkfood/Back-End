package zkfood.pedidosapi.telefone.telefone

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import zkfood.pedidosapi.telefone.telefone.telefoneDado.Telefone

@Repository
interface TelefoneRepositorio: JpaRepository<Telefone, Int>