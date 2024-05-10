package zkfood.pedidosapi.usuario.usuario

abstract class UsuarioValidador {
    companion object{
        fun emailValido(email:String){
            if (!email.contains("@")) return
            return
        }
    }
}