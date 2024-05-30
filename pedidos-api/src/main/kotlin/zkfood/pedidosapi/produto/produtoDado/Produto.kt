package zkfood.pedidosapi.produto.produtoDado

import jakarta.persistence.*
import jakarta.validation.constraints.Positive
import jakarta.validation.constraints.Size
import zkfood.pedidosapi.nucleo.enums.TipoProdutoEnum

@Entity
data class Produto (
    @field:Id @field:GeneratedValue(strategy = GenerationType.IDENTITY)
    var id:Int? = null,

    @field:Size(min = 2)
    var nome:String? = null,

    @field:Enumerated(EnumType.STRING)
    var tipo: TipoProdutoEnum? = null,

    var disponibilidade:Boolean? = false,

    @field:Size(min = 5)
    var descricao:String? = null,

    @field:Positive
    var valorUnitario:Double? = null,

    @field:Positive
    var qtdPessoas:Int? = null
){
}