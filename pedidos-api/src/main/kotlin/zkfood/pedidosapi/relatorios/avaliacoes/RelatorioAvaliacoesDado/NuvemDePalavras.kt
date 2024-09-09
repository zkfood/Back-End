package zkfood.pedidosapi.relatorios.avaliacoes.RelatorioAvaliacoesDado

data class NuvemDePalavras(
    var palavras: MutableCollection<String>,
    var quantidades: MutableCollection<Int>
)
