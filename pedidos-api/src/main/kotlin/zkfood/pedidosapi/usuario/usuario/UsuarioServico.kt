package zkfood.pedidosapi.usuario.usuario

import org.springframework.stereotype.Service
import zkfood.pedidosapi.nucleo.CrudServico
import zkfood.pedidosapi.usuario.usuario.usuarioDado.Usuario
import zkfood.pedidosapi.usuario.usuario.usuarioDado.UsuarioCadastro
import org.modelmapper.ModelMapper
import org.springframework.http.HttpStatusCode
import org.springframework.web.server.ResponseStatusException
import zkfood.pedidosapi.nucleo.EnviarEmail
import zkfood.pedidosapi.nucleo.enums.IgnorarFormatacaoEnum
import zkfood.pedidosapi.nucleo.erros.DadoDuplicadoExcecao
import zkfood.pedidosapi.usuario.usuario.usuarioDado.Login
import zkfood.pedidosapi.usuario.usuario.usuarioDado.NovaSenha
import zkfood.pedidosapi.usuario.usuario.usuarioErros.LoginOuSenhaNaoInformados
import java.security.SecureRandom

@Service
class UsuarioServico(
    val usuarioRepositorio: UsuarioRepositorio,
    val enviarEmail: EnviarEmail,
    val mapper: ModelMapper
) : CrudServico<Usuario>(usuarioRepositorio) {
    fun cadastrarDeDto(novoUsuario: UsuarioCadastro): Usuario {
        val usuarioDto: Usuario = mapper.map(novoUsuario, Usuario::class.java);
        val presencialComEmail = !novoUsuario.eCadastroOnline && novoUsuario.email.isNotEmpty();

        if (presencialComEmail) {
            if (novoUsuario.senha.isNullOrEmpty()) {
                throw LoginOuSenhaNaoInformados();
            }
            val senhaAleatoria = gerarSenhaAleatoria();
            usuarioDto.senha = senhaAleatoria;
        }

        val filtro = Usuario(cpf = novoUsuario.cpf);
        val cadastro = this.cadastrar(usuarioDto, filtro);
        if (presencialComEmail) enviarEmailSenha(usuarioDto);

        return cadastro;
    }

    override fun cadastrar(dto: Usuario, exemplo: Usuario?): Usuario {
        if (exemplo != null) {
            val estaDuplicado: Boolean = repositorio.exists(super.combinadorFiltro(exemplo, IgnorarFormatacaoEnum.INATIVO));
            if (estaDuplicado) throw DadoDuplicadoExcecao(exemplo, super.getEntidade(dto));
        }
        val cadastro = repositorio.save(dto);

        return cadastro;
    }

    private fun gerarSenhaAleatoria(): String {
        val chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        val random = SecureRandom();

        return (1..12)
            .map { chars[random.nextInt(chars.length)] }
            .joinToString("");
    }
    // método não validado
    private fun enviarEmailSenha(usuario: Usuario){
        val destinatario = usuario.email;
        val assunto = "Zeka Food - Sua Senha";
        val corpo = """
            Olá ${usuario.nome}, é um prazer ter você com a gente
            
            Aqui está a sua senha: ${usuario.senha}
        """.trimIndent();

        enviarEmail.enviarEmail(destinatario!!, assunto, corpo);
    }
    // método não validado
    fun recuperarSenha(email: String){
        if (email.isEmpty()) throw ResponseStatusException(HttpStatusCode.valueOf(400));

        val filtro = Usuario(email = email);
        val usuario:Usuario = super.listarEntidade(filtro, IgnorarFormatacaoEnum.INATIVO)[0];

        val senhaAleatoria = gerarSenhaAleatoria();
        usuario.senha = senhaAleatoria;

        enviarEmailSenha(usuario);
    }

    fun alterarSenha(id: Int, novaSenha: NovaSenha): Usuario {
        val usuario = acharPorId(id);
        usuario.senha = novaSenha.senha;

        val usuarioAtualizado: Usuario = super.atualizar(usuario.id!!, usuario);
        return usuarioAtualizado;
    }

//    fun inativarUsuario(id: Int): Usuario {
//        val usuario = acharPorId(id);
//        usuario.autenticado = false;
//
//        return usuarioRepositorio.save(usuario);
//    }

    fun entrar(login: Login): Usuario{
        if (login.email.isNullOrEmpty() || login.senha.isNullOrEmpty()) {
            throw LoginOuSenhaNaoInformados();
        }
        val filtro = Usuario(email = login.email, senha = login.senha)
        val usuario:Usuario = super.listarEntidade(filtro, IgnorarFormatacaoEnum.INATIVO)[0];

        usuario.autenticado = true
        super.atualizar(usuario.id!!, usuario)

        return usuario;
    }

    fun sair(id: Int) {
        if (id == null) throw ResponseStatusException(HttpStatusCode.valueOf(400));

        val usuario: Usuario = acharPorId(id);
        usuario.autenticado = false;
        atualizar(usuario.id!!, usuario);
    }
}