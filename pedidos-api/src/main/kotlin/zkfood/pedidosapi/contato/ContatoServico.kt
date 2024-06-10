package zkfood.pedidosapi.contato

import org.springframework.stereotype.Service
import zkfood.pedidosapi.contato.contatoDado.Contato
import zkfood.pedidosapi.contato.contatoDado.ContatoCadastro

@Service
class ContatoServico(val repositorio: ContatoRepositorio) {

    fun listar(): List<Contato> = repositorio.findAll()

    fun encontrarPorId(id: Long): Contato? = repositorio.findById(id).orElse(null)

    fun criar(contatoCadastro: ContatoCadastro): Contato {
        val contato = Contato(nome = contatoCadastro.nome, telefone = contatoCadastro.telefone, email = contatoCadastro.email)
        return repositorio.save(contato)
    }

    fun atualizar(id: Long, contatoCadastro: ContatoCadastro): Contato? {
        val contatoExistente = encontrarPorId(id) ?: return null
        val contatoAtualizado = contatoExistente.copy(nome = contatoCadastro.nome, telefone = contatoCadastro.telefone, email = contatoCadastro.email)
        return repositorio.save(contatoAtualizado)
    }

    fun deletar(id: Long) {
        repositorio.deleteById(id)
    }
}
