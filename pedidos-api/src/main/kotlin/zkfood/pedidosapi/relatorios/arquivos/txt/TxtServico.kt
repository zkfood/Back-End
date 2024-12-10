package zkfood.pedidosapi.relatorios.arquivos.txt

import org.springframework.stereotype.Service
import zkfood.pedidosapi.pedidos.pedido.PedidoRepositorio
import zkfood.pedidosapi.relatorios.arquivos.csv.SaidasDoDiaDto
import java.io.ByteArrayOutputStream
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

@Service
class TxtServico(
    val pedidoRepositorio: PedidoRepositorio
) {
    fun relatorioSaidasDoDia(data: String): ByteArray{
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

        if (dados.isEmpty()) {
            throw IllegalArgumentException("A lista está vazia e não há nada para gravar.");
        };

        val outputStream = ByteArrayOutputStream();
        val output = Formatter(outputStream);

        val dataAtual = LocalDateTime.now();
        val dataFormatada = dataAtual.format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));
        val versao = "01"

        // header
        val cabecalho = "00ZKFOOD${dataAtual.year}${dataFormatada}${versao}";
        output.format(cabecalho + "\n");


        resposta.forEach { item ->
            output.format(
                "%2s %-20s %-10d %-10.2f %-10.2f %-10s\n",
                "01",
                item.produto,
                item.quantidade,
                item.valor,
                item.receita,
                item.data
            )
        }

        //trailer
        val reboque = "02${String.format("%05d", dados.size)}";

        output.format(reboque + "\n");

        output.close();
        return outputStream.toByteArray();
    }
}