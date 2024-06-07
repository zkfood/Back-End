package zkfood.pedidosapi.produtos

import org.springframework.data.jpa.repository.JpaRepository
import zkfood.pedidosapi.produtos.produtoDado.Produto

interface ProdutoRepositorio:JpaRepository<Produto, Int> {
    fun findByTipoId(id: Int?): List<Produto>

    fun findByDisponibilidadeTrue(): List<Produto>

    fun findByDisponibilidadeFalse(): List<Produto>

    fun findByDisponibilidadeTrueAndTipoId(idTipo: Int): List<Produto>

    fun findByDisponibilidadeFalseAndTipoId(idTipo: Int): List<Produto>
}