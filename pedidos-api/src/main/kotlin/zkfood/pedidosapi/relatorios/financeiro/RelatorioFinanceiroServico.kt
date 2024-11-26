package zkfood.pedidosapi.relatorios.financeiro

import org.modelmapper.ModelMapper
import org.springframework.stereotype.Service
import zkfood.pedidosapi.pedidos.pedido.PedidoRepositorio
import zkfood.pedidosapi.produtos.produtoDado.Produto
import zkfood.pedidosapi.relatorios.financeiro.RelatorioFinanceiroDado.*
import java.time.LocalDate
import java.time.LocalDateTime

@Service
class RelatorioFinanceiroServico(
    val mapper: ModelMapper,
    val pedidoRepositorio: PedidoRepositorio
) {

    fun kpis(data: String): List<Map<String, Number>> {
        val dataAjustada = LocalDateTime.parse(data);

        return pedidoRepositorio.financeiroKpis(dataAjustada);
    }

    fun receitaAnoMeses(ano: Int): List<Any> {
        val dados = pedidoRepositorio.receitaAnoMeses(ano);

        val valoresEntrega = mutableListOf<Double>(0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0);
        val valoresBalcao = mutableListOf<Double>(0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0);
        val valoresPresencial = mutableListOf<Double>(0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0);

        val entrega = ReceitaAnoMesesResultadoConsulta("Entrega", valoresEntrega);
        val balcao = ReceitaAnoMesesResultadoConsulta("Balcão", valoresBalcao);
        val presencial = ReceitaAnoMesesResultadoConsulta("Presencial", valoresPresencial);

        val resposta = mutableListOf<ReceitaAnoMesesResultadoConsulta>();

        resposta.add(entrega);
        resposta.add(balcao);
        resposta.add(presencial);

        dados.map {data ->
            val tipo = resposta.find {dto ->
                data[1] == dto.tipoEntrega;
            }

            tipo?.valores!![data[0].toInt() - 1] = data[2].toDouble();
        }

        return resposta;
    }

    fun receitaPorAnos(): ReceitaPorAnosResposta {
        val dados = pedidoRepositorio.receitaPorAnos();

        val hashmap = HashMap<Int, MutableList<ReceitaPorAnosHashmap>>();

        dados.map {data ->
            if (hashmap[data[0].toInt()] == null) {
                hashmap[data[0].toInt()] = mutableListOf();
            }

            hashmap[data[0].toInt()]!!.add(ReceitaPorAnosHashmap(data[1], data[2].toDouble()));
        }

        val resposta = ReceitaPorAnosResposta();

        hashmap.keys.map {
            resposta.anos.add(it);

            hashmap[it]!!.map {
                if (it.tipoProduto == "Presencial") {
                    resposta.receitaPresencial.add(it.valor);
                }
                if (it.tipoProduto == "Entrega") {
                    resposta.receitaEntrega.add(it.valor);
                }
                if (it.tipoProduto == "Balcão") {
                    resposta.receitaBalcao.add(it.valor);
                }
            }
        }

        return resposta;
    }

    fun topReceitas(data: String): List<TopReceitasRespostaDto> {
        val dados = pedidoRepositorio.topReceitas(LocalDate.parse(data));

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

        return resposta;
    }
}