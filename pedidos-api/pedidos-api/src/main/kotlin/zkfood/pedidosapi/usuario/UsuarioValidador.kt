package zkfood.pedidosapi.usuario

import org.springframework.http.ResponseEntity

abstract class UsuarioValidador {
    companion object{
        fun emailValido(email:String){
            if (!email.contains("@")) return
            return
        }
    }
}