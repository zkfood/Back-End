package zkfood.pedidosapi.nucleo.dtos

data class EnderecoSimplesRespostaDto(
    var id:Int? = null,

    var cep:String? = null,

    var rua:String? = null,

    var bairro:String? = null,

    var numero:String? = null,

    var complemento:String? = null
)