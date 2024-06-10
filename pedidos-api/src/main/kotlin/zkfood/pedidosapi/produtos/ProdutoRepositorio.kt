package zkfood.pedidosapi.produtos

import org.springframework.data.jpa.repository.JpaRepository
import zkfood.pedidosapi.produtos.produtoDado.Produto

interface ProdutoRepositorio:JpaRepository<Produto, Int>