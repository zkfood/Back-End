package zkfood.pedidosapi.nucleo

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Service
import zkfood.pedidosapi.nucleo.enums.EntidadesEnum
import zkfood.pedidosapi.nucleo.enums.FiltroEnum
import zkfood.pedidosapi.nucleo.erros.DadoDuplicadoExcecao
import zkfood.pedidosapi.nucleo.erros.EntidadeImprocessavelExcecao
import zkfood.pedidosapi.nucleo.erros.NaoEncontradoPorIdExcecao
import zkfood.pedidosapi.usuario.usuarioDado.Usuario
import kotlin.reflect.KProperty1
import kotlin.reflect.full.memberProperties



@Service
abstract class CrudServico<T : Any>(val repository: JpaRepository<T, Int>) {

    protected fun listarEntidade(entidade:T, filtro:FiltroEnum?):List<T>{
        if (filtro != null) return repository.findAll(filtro);
        return repository.findAll();
    }
    protected fun acharPorId(id:Int):T{
        val dto = repository.findById(id);

        if (dto.isPresent) return dto.get();
        throw NaoEncontradoPorIdExcecao(id);
    }
    //findBy sem nada apenas passando no construtor funciona sera?
    protected fun cadastrar(dto:T):T{
        try {
            val insert:T = repository.save(dto);
            return insert;
        } catch (erro:DadoDuplicadoExcecao){
            throw DadoDuplicadoExcecao(dto,getEntidade(dto));
        }
    }
    fun atualizar(id: Int, dto: T) {
        val entidadeExistente: T = acharPorId(id)
        val entidadeClass = entidadeExistente::class.java

        for (propriedade in entidadeClass.kotlin.memberProperties) {
            val valorDTO = (propriedade as KProperty1<T, *>).call(dto)

            if (valorDTO != null) {
                try {
                    val campoEntidade = entidadeClass.getDeclaredField(propriedade.name)
                    campoEntidade.isAccessible = true
                    campoEntidade.set(entidadeExistente, valorDTO)
                } catch (ex: NoSuchFieldException) {
                    // caso o campo nn exista na entidade
                }
            }
        }

        repository.save(entidadeExistente)
    }



    protected fun deletarPorId(id:Int){
        val dto:T = acharPorId(id);

        return repository.delete(dto);
    }
    private fun getEntidade(entidade:T):EntidadesEnum{
        when(entidade){
            is Usuario -> return EntidadesEnum.USUARIO;
            else -> throw EntidadeImprocessavelExcecao(entidade);
        }
    }
    private fun getId(objeto: T):Any? {
        val propriedades = objeto::class.members.filterIsInstance<KProperty1<T, *>>();
        for (propriedade in propriedades) {
            if (propriedade.name == "Id") return propriedade.get(objeto);
        }
        return null;
    }

    private fun obterChaves(objeto:T) {
        val propriedades = objeto::class.members.filterIsInstance<KProperty1<T, *>>();
        for (propriedade in propriedades){

        }
    }
}
// typeParameters: <T> na frente da assinatura da função, serve para dizer para aquela função em especifico
// qual tipo genérico irá receber