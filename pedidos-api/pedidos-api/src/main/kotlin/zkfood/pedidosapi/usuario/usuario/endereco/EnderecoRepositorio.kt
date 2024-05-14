package zkfood.pedidosapi.usuario.usuario.endereco

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import zkfood.pedidosapi.usuario.usuario.endereco.enderecoDado.Endereco

@Repository
interface EnderecoRepositorio:JpaRepository<Endereco, Int> {
}