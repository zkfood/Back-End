package zkfood.pedidosapi.avaliacao

import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import zkfood.pedidosapi.avaliacao.avaliacaoDado.Avaliacao
import zkfood.pedidosapi.avaliacao.avaliacaoDado.AvaliacaoId
import zkfood.pedidosapi.produto.ProdutoRepositorio
import zkfood.pedidosapi.usuario.usuario.UsuarioRepositorio

@Service
class AvaliacaoServico (
    val avaliacaoRepositorio: AvaliacaoRepositorio,
    val usuarioRepositorio: UsuarioRepositorio,
    val produtoRepositorio: ProdutoRepositorio
){
    @Transactional
    fun cadastrarOuAtualiazar(usuarioId: Int, produtoId:Int, favorito:Boolean, qtdEstrelas:Int, descricao:String):Avaliacao{
        val usuario = usuarioRepositorio.findById(usuarioId).orElseThrow{RuntimeException("Usuário não encontrado")}
        val produto = produtoRepositorio.findById(produtoId).orElseThrow{RuntimeException("Produto não encontrado")}

        val avaliacaoId = AvaliacaoId(usuarioId,produtoId)
        val avaliacaoExistente = avaliacaoRepositorio.findById(avaliacaoId)
        val status = if (avaliacaoExistente.isPresent) 200 else 201

        val avaliacao = Avaliacao(
            usuarioId = usuarioId,
            produtoId = produtoId,
            favorito = favorito,
            qtdEstrelas = qtdEstrelas,
            descricao = descricao
        )

        avaliacaoRepositorio.save(avaliacao)
        return avaliacao
    }

    fun listarAvaliacoes(): List<Avaliacao> {
        return avaliacaoRepositorio.findAll()
    }

    fun listarAvaliacaoPorUsuario(usuarioId: Int):List<Avaliacao>{
        return avaliacaoRepositorio.findAll().filter { it.usuarioId == usuarioId }
    }

    fun listarAvaliacaoPorId(usuarioId: Int, produtoId: Int): Avaliacao? {
        return avaliacaoRepositorio.findById(AvaliacaoId(usuarioId, produtoId)).orElse(null)
    }

    fun deletar(usuarioId: Int, produtoId: Int) {
        avaliacaoRepositorio.deleteById(AvaliacaoId(usuarioId, produtoId))
    }
}