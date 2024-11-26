package zkfood.pedidosapi.relatorios.arquivos.csv

import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import zkfood.pedidosapi.entregasMotoboy.EntregasMotoboyRepositorio
import zkfood.pedidosapi.entregasMotoboy.entregasMotoboyData.CadstrarMotoboyDto
import zkfood.pedidosapi.entregasMotoboy.entregasMotoboyData.EntregasMotoboy
import zkfood.pedidosapi.nucleo.arquivos.csv.CsvAjudante
import zkfood.pedidosapi.pedidos.pedido.PedidoRepositorio
import zkfood.pedidosapi.produtos.ProdutoServico
import zkfood.pedidosapi.relatorios.financeiro.RelatorioFinanceiroDado.TopReceitasRespostaDto
import java.time.LocalDate

@Service
class CsvServico (
    val pedidoRepositorio: PedidoRepositorio,
    val entregasMotoboyRepositorio: EntregasMotoboyRepositorio
) {
    val csvAjudante: CsvAjudante = CsvAjudante();

    fun topReceitasMesAno(mes: Int, ano: Int): ByteArray {
        val dados = pedidoRepositorio.topReceitasMesAno(mes, ano);

        val resposta = mutableListOf<TopReceitasRespostaDto>();

        dados.map {
            resposta.add(
                TopReceitasRespostaDto(
                    produto = it[0],
                    quantidadeVendida = it[1].toInt(),
                    receita = it[2].toDouble()
                )
            )
        }

        return csvAjudante.escrever(resposta);
    }

    fun relatorioSaidasDoDia(data: String): ByteArray {
        val dataTransformada = LocalDate.parse(data)
        val dados = pedidoRepositorio.relatorioSaidasDoDia(dataTransformada);

        val resposta = mutableListOf<SaidasDoDiaDto>();

        dados.map {
            resposta.add(
                SaidasDoDiaDto(
                    data = dataTransformada,
                    quantidade = it[1].toInt(),
                    produto = it[0],
                    valor = it[2].toDouble() / it[1].toInt(),
                    receita = it[2].toDouble(),
                    formaDePagamento = null
                )
            )
        }

        return csvAjudante.escrever(resposta);
    }

    fun uploadMotoboy(arquivo: MultipartFile) {
        val motoboys = csvAjudante.lerCsv(arquivo.inputStream, CadstrarMotoboyDto::class);

        motoboys.map {
            val motoboy = EntregasMotoboy();
            motoboy.data = it.data;
            motoboy.nome = it.nome;
            motoboy.endereco = it.endereco;

            entregasMotoboyRepositorio.save(motoboy);
        }
    }
}