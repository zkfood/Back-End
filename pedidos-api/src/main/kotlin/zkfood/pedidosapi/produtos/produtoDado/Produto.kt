package zkfood.pedidosapi.produtos.produtoDado

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.ManyToOne
import zkfood.pedidosapi.produtos.tipo.tipoDado.Tipo

@Entity
data class Produto (
    @field:Id @field:GeneratedValue(strategy = GenerationType.IDENTITY)
    var id:Int? = null,

    var nome:String? = null,

    @field:ManyToOne
    var tipo:Tipo? = null,

    var disponibilidade:Boolean = false,

    var descricao:String? = null,

    var valorUnitario:Double? = null,

    var qtdPessoas:Int? = null
)