package zkfood.pedidosapi.nucleo.enums

import org.springframework.http.HttpStatusCode
import org.springframework.web.server.ResponseStatusException

enum class TipoEntregaEnum (val tipo: String) {
    BALCAO("Balcão"),
    ENTREGA("Entrega"),
    PRESENCIAL("Presencial");

    companion object {
        fun identificarTipo(tipo: String):TipoEntregaEnum {
            TipoEntregaEnum.entries.forEach {
                if (it.tipo == tipo) return it;
            }
            throw ResponseStatusException(HttpStatusCode.valueOf(422));
        }
    }
}