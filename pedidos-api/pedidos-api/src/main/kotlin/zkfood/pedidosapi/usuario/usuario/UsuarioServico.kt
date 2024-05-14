package zkfood.pedidosapi.usuario.usuario

import org.modelmapper.ModelMapper
import org.springframework.stereotype.Service
import zkfood.pedidosapi.usuario.usuario.usuarioDado.UsuarioCadastro
import zkfood.pedidosapi.usuario.usuario.usuarioDado.Usuario

@Service
class UsuarioServico(
    val usuarioRepositorio:UsuarioRepositorio,
    val mapper:ModelMapper = ModelMapper()
):CrudServico<Usuario>(usuarioRepositorio){
    fun cadastrar(novoUsuario: UsuarioCadastro): Usuario {
        // fazer validação de email, se ele existe
        // fazer mais validações no geral, se necessário
        val usuarioDto: Usuario = mapper.map(novoUsuario, Usuario::class.java);

        val filtro: Usuario = Usuario(cpf = novoUsuario.cpf);

        val cadastro: Usuario = super.cadastrar(usuarioDto, filtro);

        return cadastro;
    }
}