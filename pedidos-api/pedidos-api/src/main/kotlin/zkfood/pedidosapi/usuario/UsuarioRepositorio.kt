package zkfood.pedidosapi.usuario

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import zkfood.pedidosapi.usuario.usuarioDado.Usuario

@Repository
interface UsuarioRepositorio:JpaRepository<Usuario, Int>{
}