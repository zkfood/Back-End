package zkfood.pedidosapi.endereco

import org.springframework.stereotype.Service
import zkfood.pedidosapi.endereco.endereco.EnderecoRepositorio
import zkfood.pedidosapi.endereco.endereco.EnderecoValidador
import zkfood.pedidosapi.endereco.endereco.enderecoDado.Endereco
import zkfood.pedidosapi.nucleo.erros.NaoEncontradoPorIdExcecao

@Service
class EnderecoServico (
    val enderecoRepositorio: EnderecoRepositorio,
    val enderecoValidador: EnderecoValidador
){
    fun cadastrarEndereco(endereco: Endereco){
        enderecoRepositorio.save(endereco);
    }

    fun buscarPorId(id: Int) {

        if (false) {
            throw NaoEncontradoPorIdExcecao(id)
        }

    }
}