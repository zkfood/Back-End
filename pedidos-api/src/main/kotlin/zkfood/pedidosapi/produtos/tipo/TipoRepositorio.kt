package zkfood.pedidosapi.produtos.tipo

import org.springframework.data.jpa.repository.JpaRepository
import zkfood.pedidosapi.produtos.tipo.tipoDado.Tipo

interface TipoRepositorio:JpaRepository<Tipo, Int> {
}