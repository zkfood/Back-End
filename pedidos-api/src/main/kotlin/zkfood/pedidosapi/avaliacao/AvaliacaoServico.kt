package zkfood.pedidosapi.avaliacao

import jakarta.transaction.Transactional
import org.modelmapper.ModelMapper
import org.springframework.data.domain.Example
import org.springframework.data.domain.ExampleMatcher
import org.springframework.stereotype.Service
import zkfood.pedidosapi.avaliacao.avaliacaoDado.Avaliacao
import zkfood.pedidosapi.avaliacao.avaliacaoDado.AvaliacaoDto
import zkfood.pedidosapi.avaliacao.avaliacaoDado.AvaliacaoId
import zkfood.pedidosapi.avaliacao.dto.AvaliacaoRespostaDto
import zkfood.pedidosapi.produtos.ProdutoRepositorio
import zkfood.pedidosapi.produtos.ProdutoServico
import zkfood.pedidosapi.produtos.produtoDado.ProdutoSimplesRespostaDto
import zkfood.pedidosapi.usuario.usuario.UsuarioRepositorio
import zkfood.pedidosapi.usuario.usuario.UsuarioServico

@Service
class AvaliacaoServico (
    val avaliacaoRepositorio: AvaliacaoRepositorio,
    val usuarioServico: UsuarioServico,
    val produtoServico: ProdutoServico,
    val mapper:ModelMapper = ModelMapper()
){
    @Transactional
    fun salvar(novaAvaliacao: AvaliacaoDto): Avaliacao {
        usuarioServico.acharPorId(novaAvaliacao.usuario!!);
        produtoServico.acharPorId(novaAvaliacao.produto!!);

        val avaliacaoId = AvaliacaoId(novaAvaliacao.usuario, novaAvaliacao.produto);
        val avaliacaoExistente = avaliacaoRepositorio.findById(avaliacaoId);

        val avaliacao = avaliacaoExistente.orElseGet {
            Avaliacao(id = avaliacaoId);
        }

        avaliacao.favorito = novaAvaliacao.favorito ?: avaliacao.favorito;
        avaliacao.qtdEstrelas = novaAvaliacao.qtdEstrelas ?: avaliacao.qtdEstrelas;
        avaliacao.descricao = novaAvaliacao.descricao ?: avaliacao.descricao;

        return avaliacaoRepositorio.save(avaliacao)
    }

    fun listarAvaliacoes(filtro: AvaliacaoDto): List<AvaliacaoRespostaDto> {
        val combinador: ExampleMatcher = ExampleMatcher.matching();

        val avaliacao = Avaliacao();
        val avaliacaoId = AvaliacaoId();

        if (filtro.favorito != null) {
            combinador.withMatcher("favorito", ExampleMatcher.GenericPropertyMatchers.contains());
            avaliacao.favorito = filtro.favorito!!;
        }
        if (filtro.qtdEstrelas != null) {
            combinador.withMatcher("qtdEstrelas", ExampleMatcher.GenericPropertyMatchers.contains());
            avaliacao.qtdEstrelas = filtro.qtdEstrelas;
        }
        if (filtro.descricao != null) {
            combinador.withMatcher("descricao", ExampleMatcher.GenericPropertyMatchers.contains());
            avaliacao.descricao = filtro.descricao;
        }
        if (filtro.usuario != null) {
            combinador.withMatcher("id.usuario", ExampleMatcher.GenericPropertyMatchers.exact())
            avaliacaoId.usuario = filtro.usuario
        }
        if (filtro.produto != null) {
            combinador.withMatcher("id.produto", ExampleMatcher.GenericPropertyMatchers.exact())
            avaliacaoId.produto = filtro.produto
        }

        avaliacao.id = avaliacaoId

        val exemplo: Example<Avaliacao> = Example.of(avaliacao, combinador);
        println(exemplo)

        val avaliacoes = avaliacaoRepositorio.findAll(exemplo);
        println(avaliacoes)

        val resposta = mutableListOf<AvaliacaoRespostaDto>()

        avaliacoes.map {
            val produto = produtoServico.acharPorId(it.id?.produto!!)

            val avaliacaoResposta = AvaliacaoRespostaDto();

            avaliacaoResposta.id = it.id;
            avaliacaoResposta.favorito = it.favorito;
            avaliacaoResposta.descricao = it.descricao;
            avaliacaoResposta.qtdEstrelas = it?.qtdEstrelas;

            val produtoResposta = ProdutoSimplesRespostaDto();

            produtoResposta.id = produto.id;
            produtoResposta.descricao = produto.descricao;
            produtoResposta.nome = produto.nome;
            produtoResposta.valor = produto.valor;
            produtoResposta.qtdPessoas = produto.qtdPessoas;

            avaliacaoResposta.produto = produtoResposta;

            resposta.add(avaliacaoResposta);
        }

        return resposta;
    }

    fun deletar(usuario: Int, produto: Int) {
        val avaliacaoId = AvaliacaoId(
            usuario = usuario,
            produto = produto
        );

        avaliacaoRepositorio.deleteById(avaliacaoId);
    }

    // relatórios

    fun acharTop5PratosCom4ou5Estrelas(): List<Map<String, Any>> {
        return avaliacaoRepositorio.acharTop5PratosCom4ou5Estrelas();
    }

    fun acharTop5PratosCom1ou2Estrelas(): List<Map<String, Any>> {
        return avaliacaoRepositorio.acharTop5PratosCom1ou2Estrelas();
    }

    fun quantidadeAvaliacoesPorEstrelas(): List<Map<String, Any>> {
        return avaliacaoRepositorio.quantidadeAvaliacoesPorEstrelas();
    }

    fun nuvemDePalavras(): List<String> {
        return avaliacaoRepositorio.nuvemDePalavras();
    }
}