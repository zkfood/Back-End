package zkfood.pedidosapi.usuario.endereco

import org.modelmapper.ModelMapper
import org.springframework.stereotype.Service
import zkfood.pedidosapi.usuario.endereco.enderecoDado.Endereco
import zkfood.pedidosapi.usuario.endereco.enderecoDado.EnderecoCadastro
import zkfood.pedidosapi.nucleo.CrudServico
import zkfood.pedidosapi.nucleo.enums.IgnorarFormatacaoEnum
import zkfood.pedidosapi.nucleo.erros.NaoEncontradoPorIdExcecao
import zkfood.pedidosapi.usuario.telefone.telefoneDado.Telefone
import zkfood.pedidosapi.usuario.usuario.UsuarioServico
import zkfood.pedidosapi.usuario.usuario.usuarioDado.Usuario

@Service
class EnderecoServico(
    val enderecoRepositorio: EnderecoRepositorio,
    val mapper:ModelMapper = ModelMapper(),
    val usuarioServico: UsuarioServico
): CrudServico<Endereco>(enderecoRepositorio) {
    private fun validarUsuario(idUsuarioParametro:Int): Usuario{
        if (idUsuarioParametro == null) throw NaoEncontradoPorIdExcecao(idUsuarioParametro);

        return usuarioServico.acharPorId(idUsuarioParametro);
    }

    fun cadastrar(
        novoEndereco: EnderecoCadastro,
        idUsuario: Int
    ): Endereco {
        val usuarioEncontrado = validarUsuario(idUsuario);

        val endereco: Endereco = mapper.map(novoEndereco, Endereco::class.java);

        endereco.usuario = usuarioEncontrado

        val cadastro: Endereco = super.cadastrar(endereco, null);

        return cadastro;
    }

    fun listarEnderecos(idUsuario: Int): List<Endereco>{
        val usuario:Usuario = validarUsuario(idUsuario);

        val listaEndereco: List<Endereco> = enderecoRepositorio.findByUsuarioId(usuario.id);

        return listaEndereco;
    }

    fun acharPorId(id: Int, idUsuario: Int): Endereco{
        validarUsuario(idUsuario);

        val endereco: Endereco = super.acharPorId(id);

        return endereco;
    }

    fun atualizar(id: Int, dto: Endereco, idUsuario: Int): Endereco {
        validarUsuario(idUsuario);

        val endereco: Endereco = super.atualizar(id, dto);

        return endereco;
    }

    fun deletarPorId(id: Int, idUsuario: Int) {
        validarUsuario(idUsuario);

        super.deletarPorId(id);
    }
}