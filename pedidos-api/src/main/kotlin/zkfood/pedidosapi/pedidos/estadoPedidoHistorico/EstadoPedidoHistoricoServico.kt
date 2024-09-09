package zkfood.pedidosapi.pedidos.estadoPedidoHistorico

import org.springframework.stereotype.Service
import zkfood.pedidosapi.nucleo.CrudServico
import zkfood.pedidosapi.nucleo.enums.IgnorarFormatacaoEnum
import zkfood.pedidosapi.nucleo.erros.DadoDuplicadoExcecao
import zkfood.pedidosapi.pedidos.estadoPedidoHistorico.EstadoPedidoHistoricoDado.EstadoPedidoHistorico
import zkfood.pedidosapi.pedidos.pedido.PedidoServico
import zkfood.pedidosapi.pedidos.pedido.pedidoDado.Pedido
import java.time.LocalDateTime

@Service
class EstadoPedidoHistoricoServico(
    val estadoPedidoHistoricoRepositorio: EstadoPedidoHistoricoRepositorio
): CrudServico<EstadoPedidoHistorico>(estadoPedidoHistoricoRepositorio) {
    fun cadastrarDeDTO (idPedido:Int): MutableList<EstadoPedidoHistorico> {
        val estadoPedidoHistoricoCadastro = EstadoPedidoHistorico();
        estadoPedidoHistoricoCadastro.pedido = idPedido;
        estadoPedidoHistoricoCadastro.hora = LocalDateTime.now();
        estadoPedidoHistoricoCadastro.estado = "Pedido em espera";

        val estadoPedidoHistorico = this.cadastrar(estadoPedidoHistoricoCadastro, null);

        val listaProdutosRetorno:MutableList<EstadoPedidoHistorico> = mutableListOf();
        listaProdutosRetorno.add(estadoPedidoHistorico);

        return listaProdutosRetorno;
    }

    override fun cadastrar(dto: EstadoPedidoHistorico, exemplo: EstadoPedidoHistorico?): EstadoPedidoHistorico {
        if (exemplo != null) {
            val estaDuplicado: Boolean = repositorio.exists(super.combinadorFiltro(exemplo, IgnorarFormatacaoEnum.INATIVO));
            if (estaDuplicado) throw DadoDuplicadoExcecao(exemplo, super.getEntidade(dto));
        }
        val cadastro = repositorio.save(dto);

        return cadastro;
    }

    fun listarEstadoPedidoHistorico(id:Int): List<EstadoPedidoHistorico> {
        val filtro = EstadoPedidoHistorico(pedido = id);
        val listaPedidoUnitario = super.listarEntidade(filtro, IgnorarFormatacaoEnum.INATIVO);

        return listaPedidoUnitario;
    }

    fun atualizar(estadoPedidoHistorico: EstadoPedidoHistorico): EstadoPedidoHistorico {
        val estadoPedidoDto = EstadoPedidoHistorico();
        estadoPedidoDto.estado = estadoPedidoHistorico.estado;
        estadoPedidoDto.pedido = estadoPedidoHistorico.pedido;
        estadoPedidoDto.hora = LocalDateTime.now();

        val estadoPedido = estadoPedidoHistoricoRepositorio.save(estadoPedidoDto);

        return estadoPedido;
    }
}