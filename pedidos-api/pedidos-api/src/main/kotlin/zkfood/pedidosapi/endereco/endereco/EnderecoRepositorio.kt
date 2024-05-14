package zkfood.pedidosapi.endereco.endereco

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import zkfood.pedidosapi.endereco.endereco.enderecoDado.Endereco

@Repository
interface EnderecoRepositorio:JpaRepository<Endereco, Int> {
}