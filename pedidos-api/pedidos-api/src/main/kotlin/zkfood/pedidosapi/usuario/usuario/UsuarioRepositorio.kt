package zkfood.pedidosapi.usuario.usuario

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import zkfood.pedidosapi.usuario.usuario.usuarioDado.Usuario

@Repository
interface UsuarioRepositorio:JpaRepository<Usuario, Int>