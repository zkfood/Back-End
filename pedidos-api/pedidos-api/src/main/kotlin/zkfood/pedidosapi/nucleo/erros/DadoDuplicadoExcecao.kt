package zkfood.pedidosapi.nucleo.erros

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus
import zkfood.pedidosapi.nucleo.enums.EntidadesEnum

@ResponseStatus(code = HttpStatus.CONFLICT)
class DadoDuplicadoExcecao:RuntimeException {
    constructor(dto:Any, entidade:EntidadesEnum):super("Entrada $dto duplicada em ${entidade.entidade}!");
}