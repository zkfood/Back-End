package zkfood.pedidosapi.nucleo.erros

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(code = HttpStatus.UNPROCESSABLE_ENTITY)
class EntidadeImprocessavelExcecao:RuntimeException {
    constructor(classe:Class<*>):super("Não foi possível processar os dados de ${classe.name}");
}