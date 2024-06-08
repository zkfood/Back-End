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
import zkfood.pedidosapi.produtos.tipo.tipoDado.TipoCadastro
import kotlin.reflect.KProperty1
import kotlin.reflect.full.memberProperties

@Service
// T -> tipo genérico, ou seja, pode ser um usuário, pedido, email (ORM, ou seja, a q tem o @Entity)
// usamos isso para nossa classe aceitar um Tipo(Classe, no kotlin uma Classe é um Tipo, ex Int, String)
abstract class CrudServico<T : Any>(
    // além de recebermos no construtor o repositório jpa
    val repositorio: JpaRepository<T, Int>,
) {
    fun listarEntidade(
        // exemplo é oq o o springboot usa para fazer o filtro, mas basicamente é um dto
        // de uma classe (no nosso caso serão apenas as ORM)
        exemplo:T?,
        // nessa classe é uma enum com ativo ou inativo (true ou false) para ativar esse fitro em especifico
        ignorarFormatacaoEnum:IgnorarFormatacaoEnum?
    ):List<T> {
        // se um deles estiver null, faz o findAll sem filtro
        if (exemplo == null || ignorarFormatacaoEnum == null){
            val lista:List<T> = repositorio.findAll();
            // valida a lista para saber se ela está vazia
            ListaUtil().validarLista(lista);

            return lista;
        }
        // Chama o metodo combinadorFiltro passando o exemplo e mais um filtro, para criar um Example
        val filtro:Example<T> = combinadorFiltro(exemplo, ignorarFormatacaoEnum);

        // faz o findAll com filtro
        val lista:List<T> = repositorio.findAll(filtro);
        ListaUtil().validarLista(lista);

        return lista;
    }

    private fun combinadorFiltro(exemplo:T, ignorarFormatacaoEnum:IgnorarFormatacaoEnum):Example<T>{
        // obtém todas as chaves da classe que nós passarmos
        val propriedades = exemplo::class.members.filterIsInstance<KProperty1<T, *>>();
        // instancia a classe Example, para depois adicionarmos valores
        val combinador:ExampleMatcher = ExampleMatcher.matching();

        // se estiver ativo irá ignorar a formatação
        if (ignorarFormatacaoEnum == IgnorarFormatacaoEnum.ATIVO) combinador.withIgnoreCase();

        // para cada chave que tivermos faremos algo
        for (propriedade in propriedades){
            // pega o valor de propriedade, ex { nome: "teste" }, você tera o teste
            if (propriedade.get(exemplo) != null) {
                // pega o nome da propriedade, então com o exemplo de cima pegaria "nome"
                // e coloca no filtro do Exemple
                // aq em baixo ele já está fazendo com contains oq não é para fazer
                combinador.withMatcher(propriedade.name, ExampleMatcher.GenericPropertyMatchers.contains());
            }
        }
        // cria o Example que será utilizado no findAll
        val filtro:Example<T> = Example.of(exemplo, combinador);

        return filtro;
    }
    fun acharPorId(id:Int):T {
        val entidade = repositorio.findById(id);

        if (entidade.isPresent) return entidade.get();
        throw NaoEncontradoPorIdExcecao(id);
    }
    fun cadastrar(dto:T, exemplo:T?):T {
        // aqui valida se o dado existe apartir de um filtro, e a formatação é padrão inativo, pois
        // AaA é diferente de AAA, mas esse filtro será refeito para uma forma melhor
        if (exemplo != null) {
            val estaDuplicado: Boolean = repositorio.exists(combinadorFiltro(exemplo, IgnorarFormatacaoEnum.INATIVO));
            if (estaDuplicado) throw DadoDuplicadoExcecao(exemplo, getEntidade(dto));
        }
        val cadastro:T = repositorio.save(dto);

        return cadastro;
    }
    fun atualizar(id:Int, dto: T):T {
        val entidade:T = acharPorId(id);
        val classe:Class<*>?= getEntidade(entidade).classe;

        for (propriedade in classe!!.kotlin.memberProperties) {
            val valor = (propriedade as KProperty1<T, *>).call(dto);
            if (valor != null) {
                try {
                    val chave = classe.getDeclaredField(propriedade.name);
                    chave.isAccessible = true;
                    chave.set(entidade, valor);
                } catch (erro: RuntimeException) {
                    throw EntidadeImprocessavelExcecao(classe);
                }
            }
        }
        return repositorio.save(entidade)
    }
    fun deletarPorId(id:Int):T{
        val dto:T = acharPorId(id);
        repositorio.deleteById(id);

        return dto;
    }
    private fun getEntidade(entidade:T):EntidadesEnum{
        // recebe um dto que é do tipo que recebemos inicialmente (ORM)
        // chamamos o metodo fromClasse da nossa EntidadesEnum para descobrirmos sobre qual ORM estamos falando
        // o .javaClass faz isso por nós
        val entidadesEnum:EntidadesEnum? = EntidadesEnum.fromClasse(entidade.javaClass);
        if (entidadesEnum == null) throw EntidadeImprocessavelExcecao(entidade.javaClass);

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