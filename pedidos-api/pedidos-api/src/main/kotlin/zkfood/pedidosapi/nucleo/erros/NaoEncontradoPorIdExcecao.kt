package zkfood.pedidosapi.nucleo.erros

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(code = HttpStatus.NOT_FOUND)
class NaoEncontradoPorIdExcecao:RuntimeException {
    constructor(id: Int) : super("Entidade com $id não encontrada!");
}