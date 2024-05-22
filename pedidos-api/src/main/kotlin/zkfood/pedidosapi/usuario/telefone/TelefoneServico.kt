package zkfood.pedidosapi.usuario.telefone

import org.modelmapper.ModelMapper
import org.springframework.stereotype.Service
import zkfood.pedidosapi.nucleo.CrudServico
import zkfood.pedidosapi.nucleo.erros.NaoEncontradoPorIdExcecao
import zkfood.pedidosapi.usuario.usuario.UsuarioServico
import zkfood.pedidosapi.usuario.telefone.telefoneDado.Telefone
import zkfood.pedidosapi.usuario.telefone.telefoneDado.TelefoneCadastro
import zkfood.pedidosapi.usuario.usuario.usuarioDado.Usuario

@Service
class TelefoneServico(
    val telefoneRepositorio: TelefoneRepositorio,
    val mapper:ModelMapper = ModelMapper(),
    val usuarioServico: UsuarioServico
): CrudServico<Telefone>(telefoneRepositorio) {
    private fun validarUsuario(idUsuarioParametro:Int): Usuario{
        if (idUsuarioParametro == null) throw NaoEncontradoPorIdExcecao(idUsuarioParametro);

        return usuarioServico.acharPorId(idUsuarioParametro);
    }

    fun cadastrar(
        novoTelefone: TelefoneCadastro,
        idUsuario: Int
    ): Telefone {
        val usuarioEncontrado = validarUsuario(idUsuario);

        val telefone: Telefone = mapper.map(novoTelefone, Telefone::class.java);

        telefone.usuario = usuarioEncontrado

        val cadastro: Telefone = super.cadastrar(telefone, null);

        return cadastro;
    }

    fun listarTelefones(idUsuario: Int): List<Telefone>{
        val usuario: Usuario = validarUsuario(idUsuario);

        val listaTelefone: List<Telefone> = telefoneRepositorio.findByUsuarioId(usuario.id);

        return listaTelefone;
    }

    fun acharPorId(id: Int, idUsuario: Int): Telefone{
        validarUsuario(idUsuario);

        val telefone: Telefone = super.acharPorId(id);

        return telefone;
    }

    fun atualizar(id: Int, dto: Telefone, idUsuario: Int): Telefone {
        validarUsuario(idUsuario);

        val telefone: Telefone = super.atualizar(id, dto);

        return telefone;
    }

    fun deletarPorId(id: Int, idUsuario: Int) {
        validarUsuario(idUsuario);

        super.deletarPorId(id);
    }
}