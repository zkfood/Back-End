package zkfood.pedidosapi.avaliacao

import jakarta.transaction.Transactional
import org.modelmapper.ModelMapper
import org.springframework.data.domain.Example
import org.springframework.data.domain.ExampleMatcher
import org.springframework.stereotype.Service
import zkfood.pedidosapi.avaliacao.avaliacaoDado.Avaliacao
import zkfood.pedidosapi.avaliacao.avaliacaoDado.AvaliacaoDto
import zkfood.pedidosapi.avaliacao.avaliacaoDado.AvaliacaoId
import zkfood.pedidosapi.produtos.ProdutoRepositorio
import zkfood.pedidosapi.produtos.ProdutoServico
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

    fun listarAvaliacoes(filtro: AvaliacaoDto): List<Avaliacao> {
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

        return avaliacaoRepositorio.findAll(exemplo);
    }

    fun deletar(usuario: Int, produto: Int) {
        val avaliacaoId = AvaliacaoId(
            usuario = usuario,
            produto = produto
        );

        avaliacaoRepositorio.deleteById(avaliacaoId);
    }
}