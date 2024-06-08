package zkfood.pedidosapi.avaliacao

import jakarta.transaction.Transactional
import org.modelmapper.ModelMapper
import org.springframework.stereotype.Service
import zkfood.pedidosapi.avaliacao.avaliacaoDado.Avaliacao
import zkfood.pedidosapi.avaliacao.avaliacaoDado.AvaliacaoId
import zkfood.pedidosapi.avaliacao.dto.AvaliacaoDTO
import zkfood.pedidosapi.produtos.ProdutoRepositorio
import zkfood.pedidosapi.usuario.usuario.UsuarioRepositorio

@Service
class AvaliacaoServico (
    val avaliacaoRepositorio: AvaliacaoRepositorio,
    val usuarioRepositorio: UsuarioRepositorio,
    val produtoRepositorio: ProdutoRepositorio,
    val mapper:ModelMapper = ModelMapper()
){
    @Transactional
    fun cadastrarOuAtualiazar(idProduto: Int, idUsuario: Int, avaliacaoDTO: AvaliacaoDTO): Avaliacao {
        val usuario = usuarioRepositorio.findById(idUsuario).orElseThrow { RuntimeException("Usuário não encontrado") }
        val produto = produtoRepositorio.findById(idProduto).orElseThrow { RuntimeException("Produto não encontrado") }

        val avaliacaoId = AvaliacaoId(usuario.id!!, produto.id!!)
        val avaliacaoExistente = avaliacaoRepositorio.findById(avaliacaoId)

        val avaliacao = avaliacaoExistente.orElseGet {
            Avaliacao(
                usuarioId = usuario.id!!,
                produtoId = produto.id!!
            )
        }

        // Mapear os valores do DTO para o objeto existente
        mapper.map(avaliacaoDTO, avaliacao)

        // Salvar a avaliação no banco de dados
        return avaliacaoRepositorio.save(avaliacao)
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