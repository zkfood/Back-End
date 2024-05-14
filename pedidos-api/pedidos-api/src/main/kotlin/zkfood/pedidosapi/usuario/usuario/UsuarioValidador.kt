package zkfood.pedidosapi.usuario.usuario

object UsuarioValidador {
    fun emailValido(email: String) {
        if (!email.matches(Regex("^[A-Za-z0-9+_.-]+@(.+)$"))) {
            throw IllegalArgumentException("422")
        }
    }
}
