package zkfood.pedidosapi.usuario.usuario.endereco

import org.modelmapper.ModelMapper
import org.springframework.stereotype.Service
import zkfood.pedidosapi.usuario.usuario.endereco.enderecoDado.Endereco
import zkfood.pedidosapi.usuario.usuario.endereco.enderecoDado.EnderecoCadastro
import zkfood.pedidosapi.nucleo.CrudServico

@Service
class EnderecoServico(
    val enderecoRepositorio: EnderecoRepositorio,
    val mapper:ModelMapper = ModelMapper()
):CrudServico<Endereco>(enderecoRepositorio) {
    fun cadastrar(novoEndereco: EnderecoCadastro): Endereco {
        val enderecoDto: Endereco = mapper.map(novoEndereco, Endereco::class.java);

        val filtro: Endereco = Endereco(cep = novoEndereco.cep);

        val cadastro: Endereco = super.cadastrar(enderecoDto, filtro);

        return cadastro
    }

    fun deletar(idEndereco: Int) {
        val enderecoExistente = enderecoRepositorio.findById(idEndereco)
            .orElseThrow { NoSuchElementException("Endereço não encontrado com o ID: $idEndereco") }

        enderecoRepositorio.delete(enderecoExistente)
    }
}