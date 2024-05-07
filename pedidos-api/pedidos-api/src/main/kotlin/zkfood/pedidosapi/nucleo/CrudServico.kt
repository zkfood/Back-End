package zkfood.pedidosapi.nucleo

import org.springframework.data.domain.ExampleMatcher
import org.springframework.data.domain.Example
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Service
import zkfood.pedidosapi.nucleo.enums.EntidadesEnum
import zkfood.pedidosapi.nucleo.enums.IgnorarFormatacaoEnum
import zkfood.pedidosapi.nucleo.erros.DadoDuplicadoExcecao
import zkfood.pedidosapi.nucleo.erros.EntidadeImprocessavelExcecao
import zkfood.pedidosapi.nucleo.erros.NaoEncontradoPorIdExcecao
import zkfood.pedidosapi.nucleo.utilidade.ListaUtil
import kotlin.reflect.KProperty1
import kotlin.reflect.full.memberProperties

@Service
abstract class CrudServico<T : Any>(val repository: JpaRepository<T, Int>) {
    protected fun listarEntidade(
        exemplo:T?,
        ignorarFormatacaoEnum:IgnorarFormatacaoEnum?
    ):List<T> {
        if (exemplo == null || ignorarFormatacaoEnum == null){
            val lista:List<T> = repository.findAll();
            ListaUtil().validarLista(lista);

            return lista;
        }
        val filtro = combinadorFiltro(exemplo, ignorarFormatacaoEnum);

        val lista:List<T> = repository.findAll(filtro);
        ListaUtil().validarLista(lista);

        return lista;
    }
    private fun combinadorFiltro(exemplo:T, ignorarFormatacaoEnum:IgnorarFormatacaoEnum):Example<T>{
        // obtém todas as chaves da classe que nós passarmos
        val propriedades = exemplo::class.members.filterIsInstance<KProperty1<T, *>>();
        // instancia a classe Example, para depois adicionarmos valores
        val combinador = ExampleMatcher.matching();

        // se estiver ativo irá ignorar a formatação
        if (ignorarFormatacaoEnum == IgnorarFormatacaoEnum.ATIVO) combinador.withIgnoreCase();

        // para cada chave que tivermos faremos algo
        for (propriedade in propriedades){
            // pega o valor de propriedade, ex { nome: "teste" }, você tera o teste
            if (propriedade.get(exemplo) != null) {
                // pega o nome da propriedade, então com o exemplo de cima pegaria "nome"
                // e coloca no filtro do Exemple
                combinador.withMatcher(propriedade.name, ExampleMatcher.GenericPropertyMatchers.contains());
            }
        }
        // cria o Example que será utilizado no findAll
        val filtro:Example<T> = Example.of(exemplo, combinador);

        return filtro;
    }
    protected fun acharPorId(id:Int):T {
        val entidade = repository.findById(id);

        if (entidade.isPresent) return entidade.get();
        throw NaoEncontradoPorIdExcecao(id);
    }
    protected fun cadastrar(
        dto:T,
        exemplo:T
    ):T {
        val estaDuplicado:Boolean = repository.exists(combinadorFiltro(exemplo, IgnorarFormatacaoEnum.INATIVO));
        if (estaDuplicado) throw DadoDuplicadoExcecao(dto,getEntidade(dto));

        val cadastro:T = repository.save(dto);

        return cadastro;
    }
    fun atualizar(id: Int, dto: T) {
        // TODO: validar este método
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
        acharPorId(id);

        return repository.deleteById(id);
    }
    private fun getEntidade(entidade:T):EntidadesEnum{
        val entidadesEnum = EntidadesEnum.fromClasse(entidade.javaClass);
        if (entidadesEnum == null) throw EntidadeImprocessavelExcecao(entidade);

        return entidadesEnum;
    }
}
// coisas para usuar futuramente ou comentários
//    private fun getId(objeto: T):Any? {
//        val propriedades = objeto::class.members.filterIsInstance<KProperty1<T, *>>();
//        for (propriedade in propriedades) {
//            if (propriedade.name == "Id") return propriedade.get(objeto);
//        }
//        return null;
//    }
// typeParameters: <T> na frente da assinatura do método ou classe,
// serve para dizer para aquela função em especifico
// qual tipo genérico irá receber