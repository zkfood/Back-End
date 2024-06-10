package zkfood.pedidosapi.produtos.produtoDado

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.ManyToOne
import zkfood.pedidosapi.produtos.tipoProduto.tipoDado.TipoProdudo

@Entity
data class Produto (
    @field:Id @field:GeneratedValue(strategy = GenerationType.IDENTITY)
    var id:Int? = null,

    var nome:String? = null,

    var disponibilidade:Boolean? = null,

    var descricao:String? = null,

    var valor:Double? = null,

    var qtdPessoas:String? = null,

//    @field:ManyToOne
    var tipoProduto:Int? = null,

    // TODO: Ajustar imagem aq
    var imagem:String? = null
)