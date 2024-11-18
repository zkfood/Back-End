package zkfood.pedidosapi.produtos.produtoDado

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.*

@Entity
data class Produto (
    @field:Id @field:GeneratedValue(strategy = GenerationType.IDENTITY)
    var id:Int? = null,

    var nome:String? = null,

    var disponibilidade:Boolean? = null,

    var descricao:String? = null,

    var valor:Double? = null,

    var qtdPessoas:String? = null,

    @Column(name = "prato_do_dia")
    var pratoDoDia:Int? = null,

    @Column(name = "tipo_produto_id")
    var tipoProduto:Int? = null,

    @field:Column(length = 10 * 1024 * 1024)
    @field:JsonIgnore
    var imagem:ByteArray? = null
)