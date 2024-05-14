package zkfood.pedidosapi.endereco.endereco

import org.modelmapper.ModelMapper
import org.springframework.stereotype.Service
import zkfood.pedidosapi.endereco.endereco.enderecoDado.Endereco
import zkfood.pedidosapi.endereco.endereco.enderecoDado.EnderecoCadastro
import zkfood.pedidosapi.nucleo.CrudServico

@Service
class EnderecoServico(
    val enderecoRepositorio: EnderecoRepositorio,
    val mapper:ModelMapper = ModelMapper()
):CrudServico<Endereco>(enderecoRepositorio) {
    fun cadastrar(novoEndereco:EnderecoCadastro):Endereco{
        val enderecoDto:Endereco = mapper.map(novoEndereco, Endereco::class.java);

        val filtro: Endereco = Endereco(cep = novoEndereco.cep);

        val cadastro: Endereco = super.cadastrar(enderecoDto, filtro);

        return cadastro
    }
}