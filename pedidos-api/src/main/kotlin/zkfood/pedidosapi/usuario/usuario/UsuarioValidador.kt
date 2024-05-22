package zkfood.pedidosapi.usuario.usuario

import org.springframework.http.HttpStatusCode
import org.springframework.web.server.ResponseStatusException

object UsuarioValidador {
    fun emailValido(email: String) {
        if (!email.matches(Regex("^[A-Za-z0-9+_.-]+@(.+)$"))) {
            throw ResponseStatusException(HttpStatusCode.valueOf(400));
        }
    }
}