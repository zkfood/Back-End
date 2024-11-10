package zkfood.pedidosapi.nucleo.dtos

data class Paginacao(
    var pagina: Int? = 1,
    var tamanho: Int? = 10,
    var totalDePaginas: Int? = null,
    var totalDeDocumentos: Int? = null,
)
