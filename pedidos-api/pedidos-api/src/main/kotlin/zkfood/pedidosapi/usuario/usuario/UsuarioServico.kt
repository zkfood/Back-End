package zkfood.pedidosapi.usuario.usuario

import org.springframework.stereotype.Service
import zkfood.pedidosapi.nucleo.CrudServico
import zkfood.pedidosapi.usuario.usuario.usuarioDado.Usuario
import zkfood.pedidosapi.usuario.usuario.usuarioDado.UsuarioCadastro
import org.modelmapper.ModelMapper
import java.security.SecureRandom

@Service
class UsuarioServico(
    val usuarioRepositorio: UsuarioRepositorio,
    val mapper: ModelMapper = ModelMapper()
) : CrudServico<Usuario>(usuarioRepositorio) {

    fun cadastrar(novoUsuario: UsuarioCadastro): Usuario {
        // Validar se o e-mail é válido
        UsuarioValidador.emailValido(novoUsuario.email)

        val usuarioDto: Usuario = mapper.map(novoUsuario, Usuario::class.java)

        // Verificar se é cadastro online
        if (novoUsuario.eCadastroOnline) {
            // Se for cadastro online, validar se a senha foi fornecida
            if (novoUsuario.senha.isNullOrEmpty()) {
                throw SenhaInvalidaException("Senha é obrigatória para cadastro online")
            }
        } else {
            // Se não for cadastro online, gerar uma senha aleatória
            val senhaAleatoria = gerarSenhaAleatoria()
            usuarioDto.senha = senhaAleatoria
        }

        // Chamar o método de cadastro da classe pai (CrudServico)
        val filtro = Usuario(cpf = novoUsuario.cpf)
        val cadastro = super.cadastrar(usuarioDto, filtro)

        return cadastro
    }

    private fun gerarSenhaAleatoria(): String {
        val chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789"
        val random = SecureRandom()
        return (1..12)
            .map { chars[random.nextInt(chars.length)] }
            .joinToString("")
    }


    //não esta no escopo
  /*  fun buscarPorEmail(email: String): Usuario {
        return usuarioRepositorio.findByEmail(email) ?: throw NaoEncontradoPorIdExcecao(email)
    }
    */

    fun buscarPorNome(nome: String): List<Usuario> {
        return usuarioRepositorio.findByNomeContainingAndAtivoTrue(nome)
    }


    fun alterarSenha(id: Int, novaSenha: String): Usuario {
        val usuario = acharPorId(id)
        usuario.senha = novaSenha
        return usuarioRepositorio.save(usuario)
    }

    fun inativarUsuario(id: Int): Usuario {
        val usuario = acharPorId(id)
        usuario.ativo = false
        return usuarioRepositorio.save(usuario)
    }

    fun contarUsuarios(): Long {
        return usuarioRepositorio.countByAtivoTrue()
    }
}
