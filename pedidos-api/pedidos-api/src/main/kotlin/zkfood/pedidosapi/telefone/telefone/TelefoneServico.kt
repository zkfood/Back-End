package zkfood.pedidosapi.telefone.telefone

import org.modelmapper.ModelMapper
import org.springframework.stereotype.Service
import zkfood.pedidosapi.telefone.telefone.telefoneDado.Telefone
import zkfood.pedidosapi.telefone.telefone.telefoneDado.TelefoneCadastro

@Service
class TelefoneServico(
    val telefoneRepositorio: TelefoneRepositorio,
    val mapper:ModelMapper = ModelMapper()
): CrudServico<Telefone>(telefoneRepositorio) {
    fun cadastrar(novoTelefone: TelefoneCadastro): Telefone {
        val telefoneDto: Telefone = mapper.map(novoTelefone, Telefone::class.java)

        val filtro: Telefone = Telefone(novoTelefone.numero)

        val cadastro: Telefone = super.cadastrar(telefoneDto, filtro)

        return cadastro
    }
}