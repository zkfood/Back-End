package zkfood.pedidosapi.produtos.tipo

import org.modelmapper.ModelMapper
import org.springframework.stereotype.Service
import zkfood.pedidosapi.nucleo.CrudServico
import zkfood.pedidosapi.produtos.tipo.tipoDado.Tipo
import zkfood.pedidosapi.produtos.tipo.tipoDado.TipoCadastro

@Service
class TipoServico(
    val tipoRepositorio: TipoRepositorio,
    val mapper:ModelMapper
):CrudServico<Tipo>(tipoRepositorio){

    fun cadastrar(novoTipo:TipoCadastro): Tipo {
        val tipo:Tipo = mapper.map(novoTipo, Tipo::class.java);

        val filtro = Tipo(nome = novoTipo.nome);
        val cadastro: Tipo = super.cadastrar(tipo, filtro);

        return cadastro
    }

    fun listar():List<Tipo>{
        val listaTipos: List<Tipo> = tipoRepositorio.findAll();

        return listaTipos
    }

    fun buscarPorId(idTipo:Int):Tipo{
        val tipo:Tipo = super.acharPorId(idTipo);

        return tipo
    }

    /*
    fun atualizarTipo(id:Int, dto: TipoCadastro):Tipo{
        val tipo: Tipo = mapper.map(dto, Tipo::class.java)

        return super.atualizar(id, tipo)
    }
     */
}