package zkfood.pedidosapi.nucleo.enums

import zkfood.pedidosapi.usuario.usuarioDado.Usuario

enum class EntidadesEnum(val entidade: String, val classe: Class<*>? = null) {
    USUARIO("Usuários", Usuario::class.java),
    PEDIDOS("Pedidos");

    companion object {
        fun fromClasse(classeBusca:Class<*>):EntidadesEnum? {
            entries.forEach {
                if (it.classe == classeBusca) return it;
            }
            return null;
        }
    }
}