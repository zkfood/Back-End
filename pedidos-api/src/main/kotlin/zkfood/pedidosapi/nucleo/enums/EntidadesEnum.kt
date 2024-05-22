package zkfood.pedidosapi.nucleo.enums

import zkfood.pedidosapi.usuario.telefone.telefoneDado.Telefone
import zkfood.pedidosapi.usuario.endereco.enderecoDado.Endereco
import zkfood.pedidosapi.usuario.usuario.usuarioDado.Usuario

enum class EntidadesEnum(val entidade: String, val classe: Class<*>? = null) {
    // aqui declaramos quais ORM vamos usar
    USUARIO("Usuários", Usuario::class.java),
    TELEFONE("Telefones", Telefone::class.java),
    ENDERECO("Endereços", Endereco::class.java),
    PEDIDOS("Pedidos");

    companion object {
        fun fromClasse(classeBusca:Class<*>):EntidadesEnum? {
            // aqui recebemos a classe que foi passada e descobrimos se ela está na nossa enum
            entries.forEach {
                if (it.classe == classeBusca) return it;
            }
            return null;
        }
    }
}