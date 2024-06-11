package zkfood.pedidosapi.pedidos.pedidoUnitario

import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import zkfood.pedidosapi.nucleo.erros.NaoEncontradoPorIdExcecao
import zkfood.pedidosapi.pedidos.pedidoUnitario.PedidoUnitarioDado.ChaveCompostaPedidoUnitario
import zkfood.pedidosapi.pedidos.pedidoUnitario.PedidoUnitarioDado.PedidoUnitario
import zkfood.pedidosapi.pedidos.pedidoUnitario.PedidoUnitarioDado.ProdutoUnitarioCadastro

@Service
class PedidoUnitarioServico(
    val pedidoUnitarioRepositorio: PedidoUnitarioRepositorio
) {
    @Transactional
    fun cadastrar (idPedido:Int, listaProdutos:List<ProdutoUnitarioCadastro>): List<PedidoUnitario> {
        val listaProdutosRetorno:MutableList<PedidoUnitario> = mutableListOf();
        listaProdutos.forEach {
            val pedidoUnitario = PedidoUnitario();
            val chaveComposta = ChaveCompostaPedidoUnitario();
            chaveComposta.pedido = idPedido;
            chaveComposta.produto = it.id;

            pedidoUnitario.id = chaveComposta;
            pedidoUnitario.quantidade = it.quantidade;
            pedidoUnitario.observacao = it.observacao;

            pedidoUnitarioRepositorio.save(pedidoUnitario);
            listaProdutosRetorno.add(pedidoUnitario)
        }

        return listaProdutosRetorno;
    }

    fun listarPedidoUnitario(id:Int): List<PedidoUnitario> {
        val listaPedidoUnitario = pedidoUnitarioRepositorio.findByIdPedido(id);

        return listaPedidoUnitario;
    }

    fun atualizar(dto: PedidoUnitario): PedidoUnitario {
        val chaveCompostaPedidoUnitario = ChaveCompostaPedidoUnitario(pedido = dto.id!!.pedido, produto = dto.id!!.produto);
        val entidade = pedidoUnitarioRepositorio.findById(chaveCompostaPedidoUnitario).get();

        entidade.quantidade = dto.quantidade;
        entidade.observacao = dto.observacao;

        val entidadeAtualizada = pedidoUnitarioRepositorio.save(entidade);

        return entidadeAtualizada;
    }
}