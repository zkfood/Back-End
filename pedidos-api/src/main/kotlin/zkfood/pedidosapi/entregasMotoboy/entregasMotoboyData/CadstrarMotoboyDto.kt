package zkfood.pedidosapi.entregasMotoboy.entregasMotoboyData

import java.time.LocalDate

data class CadstrarMotoboyDto(
    val data: LocalDate,
    val nome: String,
    val endereco: String
)
