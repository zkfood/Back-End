package zkfood.pedidosapi.produtos.tipoProduto

import org.springframework.data.jpa.repository.JpaRepository
import zkfood.pedidosapi.produtos.tipoProduto.tipoDado.TipoProdudo

interface TipoProdutoRepositorio:JpaRepository<TipoProdudo, Int>