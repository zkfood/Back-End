package zkfood.pedidosapi.pedidos.estadoPedidoHistorico

import org.springframework.stereotype.Service
import zkfood.pedidosapi.nucleo.CrudServico
import zkfood.pedidosapi.nucleo.enums.IgnorarFormatacaoEnum
import zkfood.pedidosapi.pedidos.estadoPedidoHistorico.EstadoPedidoHistoricoDado.EstadoPedidoHistorico
import zkfood.pedidosapi.pedidos.pedido.PedidoServico
import zkfood.pedidosapi.pedidos.pedido.pedidoDado.Pedido
import java.time.LocalDateTime

@Service
class EstadoPedidoHistoricoServico(
    val estadoPedidoHistoricoRepositorio: EstadoPedidoHistoricoRepositorio
): CrudServico<EstadoPedidoHistorico>(estadoPedidoHistoricoRepositorio) {
    fun cadastrar (idPedido:Int): MutableList<EstadoPedidoHistorico> {
        val estadoPedidoHistoricoCadastro = EstadoPedidoHistorico();
        estadoPedidoHistoricoCadastro.pedido = idPedido;
        estadoPedidoHistoricoCadastro.hora = LocalDateTime.now();
        estadoPedidoHistoricoCadastro.estado = "Pedido em espera";

        val estadoPedidoHistorico = super.cadastrar(estadoPedidoHistoricoCadastro, null);

        val listaProdutosRetorno:MutableList<EstadoPedidoHistorico> = mutableListOf();
        listaProdutosRetorno.add(estadoPedidoHistorico);

        return listaProdutosRetorno;
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