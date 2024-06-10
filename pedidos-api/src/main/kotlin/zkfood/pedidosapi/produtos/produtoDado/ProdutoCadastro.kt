package zkfood.pedidosapi.produtos.produtoDado

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull

data class ProdutoCadastro (
    @field:NotBlank
    var nome:String,

    @field:NotBlank
    var descricao:String,

    @field:NotNull
    var valor:Double,

    @field:NotNull
    var qtdPessoas:String,

    @field:NotNull
    var tipoProduto:Int,

    var disponibilidade: Boolean = true,

    // TODO: Ajustar foto aq
    var imagem: String? = null
)