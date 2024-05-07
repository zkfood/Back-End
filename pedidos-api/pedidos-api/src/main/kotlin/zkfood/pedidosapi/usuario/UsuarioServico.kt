package zkfood.pedidosapi.usuario

import org.modelmapper.ModelMapper
import org.springframework.stereotype.Service
import zkfood.pedidosapi.nucleo.CrudServico
import zkfood.pedidosapi.usuario.usuarioDado.UsuarioCadastro
import zkfood.pedidosapi.nucleo.erros.NaoEncontradoPorIdExcecao
import zkfood.pedidosapi.usuario.usuarioDado.Usuario

@Service
class UsuarioServico(
    val usuarioRepositorio:UsuarioRepositorio,
    val mapper: ModelMapper = ModelMapper()
):CrudServico<Usuario>(usuarioRepositorio){

    fun cadastrar(novoUsuario:UsuarioCadastro):Usuario{
        // fazer validação de email, se ele existe
        // fazer mais validações no geral, se necessário
        val usuarioDto = mapper.map(novoUsuario, Usuario::class.java);

        val filtro = Usuario(email = novoUsuario.email);

        val cadastro = super.cadastrar(usuarioDto, filtro);

        return cadastro;
    }

    fun listar():List<Usuario>{
        val listaUsuario:List<Usuario> = super.listarEntidade(null, null);

        return listaUsuario;
    }
}