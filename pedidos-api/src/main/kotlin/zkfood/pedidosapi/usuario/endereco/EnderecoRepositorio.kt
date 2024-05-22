package zkfood.pedidosapi.usuario.endereco

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import zkfood.pedidosapi.usuario.endereco.enderecoDado.Endereco

@Repository
interface EnderecoRepositorio:JpaRepository<Endereco, Int> {
    fun findByUsuarioId(id: Int?): List<Endereco>
}