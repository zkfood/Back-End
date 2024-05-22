package zkfood.pedidosapi.nucleo.utilidade

import org.springframework.http.HttpStatusCode
import org.springframework.web.server.ResponseStatusException

class ListaUtil {
     fun validarLista(list: List<*>){
        if (list.isEmpty()){
            throw ResponseStatusException(HttpStatusCode.valueOf(204));
        }
    }
}