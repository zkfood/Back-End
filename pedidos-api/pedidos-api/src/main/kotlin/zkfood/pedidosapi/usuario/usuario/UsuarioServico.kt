package zkfood.pedidosapi.usuario.usuario

import org.modelmapper.ModelMapper
import org.springframework.stereotype.Service
import zkfood.pedidosapi.nucleo.CrudServico
import zkfood.pedidosapi.usuario.usuario.usuarioDado.UsuarioCadastro
import zkfood.pedidosapi.usuario.usuario.usuarioDado.Usuario

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
        val filtro: Usuario = Usuario(cpf = novoUsuario.cpf)
        val cadastro: Usuario = super.cadastrar(usuarioDto, filtro)

        return cadastro
    }

    private fun gerarSenhaAleatoria(): String {
        // Lógica para gerar senha aleatória
        // Retorne uma senha aleatória (pode ser implementado conforme necessário)
    }
}
