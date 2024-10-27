package zkfood.pedidosapi.usuario.usuario.usuarioErros

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
class LoginOuSenhaNaoInformados:RuntimeException {
    constructor():super("Login ou Senha não informados");
}