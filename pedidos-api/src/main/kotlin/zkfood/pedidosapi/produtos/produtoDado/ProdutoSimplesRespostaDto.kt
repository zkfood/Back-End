package zkfood.pedidosapi.produtos.produtoDado

data class ProdutoSimplesRespostaDto(
    var id: Int? = null,

    var quantidade: Int? = null,

    var observacao: String? = null,

    var nome: String? = null,

    var valor: Double? = null,

    var qtdPessoas:String? = null,

    var descricao:String? = null,
)