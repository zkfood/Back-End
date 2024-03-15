package zkfood.pedidosapi.usuario

abstract class UsuarioValidador {
    fun emailValido(email:String){
        //if (!email.contains("@")) throw Exception
        //lançar erro EmailFormatoIncorretoErro
    }
}