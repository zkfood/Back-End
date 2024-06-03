package zkfood.pedidosapi.produto

import org.springframework.data.jpa.repository.JpaRepository
import zkfood.pedidosapi.produto.produtoDado.Produto
import java.util.Optional

interface ProdutoRepositorio:JpaRepository<Produto, Int>{
    fun findById(id:Int?):Optional<Produto>
}