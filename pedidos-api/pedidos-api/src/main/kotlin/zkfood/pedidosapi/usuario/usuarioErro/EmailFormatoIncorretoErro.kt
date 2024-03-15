package zkfood.pedidosapi.usuario.usuarioErro

abstract class EmailFormatoIncorretoErro(): Exception() {
    val mensagem:String = "Email"
}

//TODO: fazer uma enum????