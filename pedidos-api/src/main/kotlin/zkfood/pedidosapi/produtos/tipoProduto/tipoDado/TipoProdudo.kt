package zkfood.pedidosapi.produtos.tipoProduto.tipoDado

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

@Entity
data class TipoProdudo (
    @field:Id @field:GeneratedValue(strategy = GenerationType.IDENTITY)
    var id:Int? = null,

    var nome:String? = null
)