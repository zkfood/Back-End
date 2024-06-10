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
            pedidoUnitario.idPedido = idPedido;
            pedidoUnitario.idProduto = it.id;
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
        val chaveCompostaPedidoUnitario = ChaveCompostaPedidoUnitario(idPedido = dto.idPedido, idProduto = dto.idProduto);
        val entidade = pedidoUnitarioRepositorio.findById(chaveCompostaPedidoUnitario).get();

        entidade.quantidade = dto.quantidade;
        entidade.observacao = dto.observacao;

        val entidadeAtualizada = pedidoUnitarioRepositorio.save(entidade);

        return entidadeAtualizada;
    }
}