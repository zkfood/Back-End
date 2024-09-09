package zkfood.pedidosapi.pedidos.pedido

import org.modelmapper.ModelMapper
import org.springframework.stereotype.Service
import zkfood.pedidosapi.nucleo.CrudServico
import zkfood.pedidosapi.nucleo.dtos.EnderecoSimplesRespostaDto
import zkfood.pedidosapi.nucleo.dtos.EstadoPedidoHistoricoSimpesRespostaDto
import zkfood.pedidosapi.nucleo.dtos.TelefoneSimplesRespostaDto
import zkfood.pedidosapi.nucleo.dtos.UsuarioSimplesRespostaDto
import zkfood.pedidosapi.nucleo.enums.EstadoPedidoEnum
import zkfood.pedidosapi.nucleo.enums.IgnorarFormatacaoEnum
import zkfood.pedidosapi.nucleo.enums.TipoEntregaEnum
import zkfood.pedidosapi.nucleo.erros.DadoDuplicadoExcecao
import zkfood.pedidosapi.pedidos.estadoPedidoHistorico.EstadoPedidoHistoricoDado.EstadoPedidoHistorico
import zkfood.pedidosapi.pedidos.estadoPedidoHistorico.EstadoPedidoHistoricoServico
import zkfood.pedidosapi.pedidos.pedido.pedidoDado.Pedido
import zkfood.pedidosapi.pedidos.pedido.pedidoDado.PedidoCadastro
import zkfood.pedidosapi.pedidos.pedido.pedidoDado.PedidoCompletoResposta
import zkfood.pedidosapi.pedidos.pedidoUnitario.PedidoUnitarioDado.PedidoUnitario
import zkfood.pedidosapi.pedidos.pedidoUnitario.PedidoUnitarioServico
import zkfood.pedidosapi.produtos.ProdutoServico
import zkfood.pedidosapi.produtos.produtoDado.ProdutoSimplesRespostaDto
import zkfood.pedidosapi.usuario.endereco.EnderecoServico
import zkfood.pedidosapi.usuario.telefone.TelefoneServico
import zkfood.pedidosapi.usuario.usuario.UsuarioServico

@Service
class PedidoServico(
    val pedidoRepositorio: PedidoRepositorio,
    val mapper: ModelMapper,
    val pedidoUnitarioServico: PedidoUnitarioServico,
    val produtoServico: ProdutoServico,
    val usuarioServico: UsuarioServico,
    val telefoneServico: TelefoneServico,
    val enderecoServico: EnderecoServico,
    val estadoPedidoHistoricoServico: EstadoPedidoHistoricoServico
):CrudServico<Pedido>(pedidoRepositorio) {
    fun procurarPorId(id: Int): PedidoCompletoResposta{
        val pedido = super.acharPorId(id);
        val pedidoResposta = mapper.map(pedido, PedidoCompletoResposta::class.java);
        val listaProdutos = pedidoUnitarioServico.listarPedidoUnitario(id);
        val listaEstadoPedidoHistorico = estadoPedidoHistoricoServico.listarEstadoPedidoHistorico(id);
        pedidoResposta.estado = listaEstadoPedidoHistorico.last().estado;

        pedidoResposta.produtos = conversorDeProdutos(pedidoResposta, listaProdutos);
        pedidoResposta.estadoPedidoHistorico = conversorDeEstadoPedidoHistorico(pedidoResposta, listaEstadoPedidoHistorico);
        pedidoResposta.usuario = conversorDeUsuario(pedido.usuario);
        pedidoResposta.telefone = conversorDeTelefone(pedido.telefone);
        pedidoResposta.endereco = conversorDeEndereco(pedido.endereco);

        return pedidoResposta;
    }

    fun listarPedidos(id: Int): List<PedidoCompletoResposta>{
        val filtro = Pedido(usuario = id);
        val listaPedido = super.listarEntidade(filtro, IgnorarFormatacaoEnum.INATIVO);

        val listaPedidoCompletoResposta:MutableList<PedidoCompletoResposta> = mutableListOf();
        listaPedido.forEach {
            val pedido = this.procurarPorId(it.id!!);
            listaPedidoCompletoResposta.add(pedido);
        }

        return listaPedidoCompletoResposta;
    }

    fun cadastrarDeDTO (novoPedido: PedidoCadastro): PedidoCompletoResposta {
        PedidoValidador.validarIds(novoPedido);
        TipoEntregaEnum.identificarTipo(novoPedido.tipoEntrega);
        EstadoPedidoEnum.identificarEstado("Pedido em espera");

        val pedido = mapper.map(novoPedido, Pedido::class.java);
        val cadastro = this.cadastrar(pedido, null);
        val cadastroRetorno = mapper.map(cadastro, PedidoCompletoResposta::class.java);

        val listaProdutos = pedidoUnitarioServico.cadastrar(cadastro.id!!, novoPedido.produtos);
        cadastroRetorno.produtos = conversorDeProdutos(cadastroRetorno, listaProdutos);

        val listaEstadoPedidoHistorico = estadoPedidoHistoricoServico.cadastrarDeDTO(cadastro.id!!);
        cadastroRetorno.estadoPedidoHistorico = conversorDeEstadoPedidoHistorico(cadastroRetorno, listaEstadoPedidoHistorico);
        cadastroRetorno.estado = "Pedido em espera";

        cadastroRetorno.usuario = conversorDeUsuario(novoPedido.usuario);
        cadastroRetorno.telefone = conversorDeTelefone(novoPedido.telefone);
        cadastroRetorno.endereco = conversorDeEndereco(novoPedido.endereco);

        return cadastroRetorno;
    }

    override fun cadastrar(dto: Pedido, exemplo: Pedido?): Pedido {
        if (exemplo != null) {
            val estaDuplicado: Boolean = repositorio.exists(super.combinadorFiltro(exemplo, IgnorarFormatacaoEnum.INATIVO));
            if (estaDuplicado) throw DadoDuplicadoExcecao(exemplo, super.getEntidade(dto));
        }
        val cadastro = repositorio.save(dto);

        return cadastro;
    }

    fun atualizarPedido(id:Int, pedido:Pedido): PedidoCompletoResposta{
        super.atualizar(id, pedido);
        val dto = this.procurarPorId(id);

        return dto;
    }

    private fun conversorDeProdutos(
        pedidoRetorno:PedidoCompletoResposta,
        listaProdutos:List<PedidoUnitario>
    ):MutableList<ProdutoSimplesRespostaDto>? {
        listaProdutos.forEach {
            val produtoRetorno = ProdutoSimplesRespostaDto();
            val produto = produtoServico.acharPorId(it.id!!.produto!!);

            produtoRetorno.id = it.id!!.produto!!;
            produtoRetorno.quantidade = it.quantidade;
            produtoRetorno.observacao = it.observacao;
            produtoRetorno.nome = produto.nome;
            produtoRetorno.valor = produto.valor;
            produtoRetorno.descricao = produto.descricao;
            produtoRetorno.qtdPessoas = produto.qtdPessoas;

            pedidoRetorno.produtos = mutableListOf();
            pedidoRetorno.produtos!!.add(produtoRetorno);
        }

        return pedidoRetorno.produtos;
    }
    private fun conversorDeUsuario(usuarioId:Int?): UsuarioSimplesRespostaDto?{
        val usuario = if(usuarioId != null) usuarioServico.acharPorId(usuarioId) else null;
        val usuarioSimplesRespostaDto = if(usuario != null) mapper.map(usuario, UsuarioSimplesRespostaDto::class.java) else null;
        return usuarioSimplesRespostaDto;
    }
    private fun conversorDeTelefone(telefoneId:Int?): TelefoneSimplesRespostaDto?{
        val telefone = if(telefoneId != null) telefoneServico.acharPorId(telefoneId) else null;
        val telefoneSimplesRespostaDto = if(telefone != null) mapper.map(telefone, TelefoneSimplesRespostaDto::class.java) else null;
        return telefoneSimplesRespostaDto;
    }
    private fun conversorDeEndereco(enderecoId:Int?): EnderecoSimplesRespostaDto?{
        val endereco = if(enderecoId != null) enderecoServico.acharPorId(enderecoId) else null;
        val enderecoSimplesRespostaDto = if(endereco != null) mapper.map(endereco, EnderecoSimplesRespostaDto::class.java) else null;
        return enderecoSimplesRespostaDto;
    }
    private fun conversorDeEstadoPedidoHistorico(
        pedidoRetorno:PedidoCompletoResposta,
        listaEstadoPedidoHistorico:List<EstadoPedidoHistorico>
    ): MutableList<EstadoPedidoHistoricoSimpesRespostaDto>?{
        pedidoRetorno.estadoPedidoHistorico = mutableListOf();
        listaEstadoPedidoHistorico.forEach {
            val estadoPedidoHistorico =  estadoPedidoHistoricoServico.acharPorId(it.id!!);
            val estadoPedidoHistoricoRetorno = mapper.map(estadoPedidoHistorico, EstadoPedidoHistoricoSimpesRespostaDto::class.java);

            pedidoRetorno.estadoPedidoHistorico!!.add(estadoPedidoHistoricoRetorno);
        }

        return pedidoRetorno.estadoPedidoHistorico;
    }
}