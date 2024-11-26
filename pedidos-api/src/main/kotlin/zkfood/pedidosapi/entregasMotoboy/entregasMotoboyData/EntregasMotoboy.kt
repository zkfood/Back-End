package zkfood.pedidosapi.entregasMotoboy.entregasMotoboyData

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import java.time.LocalDate

@Entity
data class EntregasMotoboy(
    @field:Id @field:GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null,

    var nome: String? = null,

    var endereco: String? = null,

    var data: LocalDate? = null,
)
