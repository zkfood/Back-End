package zkfood.pedidosapi.pedidos.pedidoUnitario

import org.springframework.stereotype.Service
import zkfood.pedidosapi.nucleo.CrudServico
import zkfood.pedidosapi.pedidos.pedidoUnitario.PedidoUnitarioDado.PedidoUnitario
import zkfood.pedidosapi.pedidos.pedidoUnitario.PedidoUnitarioDado.ProdutoUnitarioCadastro
import java.util.concurrent.ArrayBlockingQueue

@Service
class PedidoUnitarioServico (
    val pedidoUnitarioRepositorio: PedidoUnitarioRepositorio
): CrudServico<PedidoUnitario>(pedidoUnitarioRepositorio) {
    fun cadastrarDeDto (idPedido:Int, listaProdutos:List<ProdutoUnitarioCadastro>): List<PedidoUnitario> {
        val listaProdutosRetorno:MutableList<PedidoUnitario> = mutableListOf();

        val fila = ArrayBlockingQueue<ProdutoUnitarioCadastro>(listaProdutos.size);

        listaProdutos.map {
            fila.add(it);
        }

        for (i in 1..listaProdutos.size){
            val produtoDaVez = fila.poll();

            val pedidoUnitario = PedidoUnitario();
            pedidoUnitario.pedido = idPedido;
            pedidoUnitario.produto = produtoDaVez.id;

            pedidoUnitario.quantidade = produtoDaVez.quantidade;
            pedidoUnitario.observacao = produtoDaVez.observacao;

            pedidoUnitarioRepositorio.save(pedidoUnitario);
            listaProdutosRetorno.add(pedidoUnitario);
        }
        return listaProdutosRetorno;
    }

    fun listarPedidoUnitario(id:Int): List<PedidoUnitario> {
        val listaPedidoUnitario = pedidoUnitarioRepositorio.findByPedido(id);

        return listaPedidoUnitario;
    }

    fun atualizar(dto: PedidoUnitario): PedidoUnitario {
        val entidade = pedidoUnitarioRepositorio.findById(dto.id!!).get();

        entidade.quantidade = dto.quantidade;
        entidade.observacao = dto.observacao;
        entidade.entregue = dto.entregue;

        val entidadeAtualizada = pedidoUnitarioRepositorio.save(entidade);

        return entidadeAtualizada;
    }

    override fun cadastrar(dto: PedidoUnitario, exemplo: PedidoUnitario?): PedidoUnitario {
        TODO("Não terá o método cadastrar,e le só está aqui, pois é obrigatório no crud serviço")
    }
}