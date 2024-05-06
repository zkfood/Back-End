package zkfood.pedidosapi.usuarios

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import zkfood.pedidosapi.usuario.UsuarioServico
import zkfood.pedidosapi.usuario.UsuarioValidador
import zkfood.pedidosapi.usuario.usuarioDado.Usuario

@RestController
@RequestMapping("/usuarios")
class UsuarioControlador {
    val usuarioServico:UsuarioServico = UsuarioServico();

    @PostMapping
    fun cadastar(@RequestBody usuario:Usuario){
        return usuarioServico.cadastrarUsuario(usuario);
    }
}