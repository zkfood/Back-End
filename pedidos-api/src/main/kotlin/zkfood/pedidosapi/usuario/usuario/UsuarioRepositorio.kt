package zkfood.pedidosapi.usuario.usuario

import org.springframework.data.jpa.repository.JpaRepository
import zkfood.pedidosapi.usuario.usuario.usuarioDado.Usuario
import org.springframework.stereotype.Repository

@Repository
interface UsuarioRepositorio : JpaRepository<Usuario, Int> {
}