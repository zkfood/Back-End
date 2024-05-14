package zkfood.pedidosapi.telefone.telefone.telefoneDado

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import org.springframework.format.annotation.NumberFormat

@Entity
data class Telefone (
    @field:Id @field:GeneratedValue(strategy = GenerationType.IDENTITY)
    var id:Int? = null,

    @field:NumberFormat
    var numero:NumberFormat? = null
){
}