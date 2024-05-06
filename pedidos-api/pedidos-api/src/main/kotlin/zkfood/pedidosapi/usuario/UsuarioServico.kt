package zkfood.pedidosapi.usuario

import org.springframework.stereotype.Service
import zkfood.pedidosapi.nucleo.erros.NaoEncontradoException
import zkfood.pedidosapi.usuario.usuarioDado.Usuario

@Service
class UsuarioServico(
    val usuarioRepositorio:UsuarioRepositorio
) {
    fun cadastrarUsuario(usuario: Usuario){
        UsuarioValidador.emailValido(usuario.email);

        usuarioRepositorio.save(usuario);
    }

    fun buscarPorId(id: Int) {
        // fiz a busca no banco e não encontrou

        if (false) { // finge que é resultado da busca
            throw NaoEncontradoException("Usuario")
        }

        //ex de como usar try catch
        // tentei pegar um atributo de um objeto mas tava nulo, quero evitar nullpointer pq não sei o que vem
    }
}