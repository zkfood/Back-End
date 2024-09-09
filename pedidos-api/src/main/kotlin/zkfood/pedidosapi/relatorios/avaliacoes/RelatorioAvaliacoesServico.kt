package zkfood.pedidosapi.relatorios.avaliacoes

import org.modelmapper.ModelMapper
import org.springframework.stereotype.Service
import zkfood.pedidosapi.avaliacao.AvaliacaoServico
import zkfood.pedidosapi.relatorios.avaliacoes.RelatorioAvaliacoesDado.NuvemDePalavras
import zkfood.pedidosapi.relatorios.avaliacoes.RelatorioAvaliacoesDado.QuantiadeAvaliacoesPorEstrelas
import zkfood.pedidosapi.relatorios.avaliacoes.RelatorioAvaliacoesDado.TopPratos

@Service
class RelatorioAvaliacoesServico(
    val avaliacaoServico: AvaliacaoServico,
    val mapper: ModelMapper
) {
    fun topPratos(): TopPratos {
        val top4ou5 = avaliacaoServico.acharTop5PratosCom4ou5Estrelas();
        val top1ou2 = avaliacaoServico.acharTop5PratosCom1ou2Estrelas();

        val topPratos = TopPratos(
            top4ou5 = top4ou5,
            top1ou2 = top1ou2
        )

        return topPratos;
    }

    fun quantidadeAvaliacoesPorEstrelas(): List<Int?> {
        val quantidadeAvaliacoesPorEstrelas = avaliacaoServico.quantidadeAvaliacoesPorEstrelas();

        val avaliacaoMapeada = quantidadeAvaliacoesPorEstrelas.map {
            mapper.map(
                it,
                QuantiadeAvaliacoesPorEstrelas::class.java
            )
        }

        val retorno = avaliacaoMapeada.map { it.quantidade }

        return retorno;
    }

    fun nuvemDePalavras(): NuvemDePalavras {
        // Obtém todas as descrições de avaliações
        val descricoes = avaliacaoServico.nuvemDePalavras();

        // Contador para as palavras
        val wordCount = mutableMapOf<String, Int>();

        // Processa cada descrição
        for (descricao in descricoes) {
            // Divide a descrição em palavras, considerando apenas letras e números
            val words = descricao.split("\\s+".toRegex()).filter { it.isNotBlank() }

            for (word in words) {
                // Conta as palavras, normalizando para minúsculas
                val normalizedWord = word.lowercase()
                wordCount[normalizedWord] = wordCount.getOrDefault(normalizedWord, 0) + 1
            }
        }

        val nuvemDePalavras = NuvemDePalavras(
            palavras = wordCount.keys,
            quantidades = wordCount.values
        );

        return nuvemDePalavras;
    }
}