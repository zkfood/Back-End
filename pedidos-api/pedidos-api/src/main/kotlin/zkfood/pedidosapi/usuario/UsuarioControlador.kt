package zkfood.pedidosapi.usuarios

import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import zkfood.pedidosapi.usuario.UsuarioServico
import zkfood.pedidosapi.usuario.usuarioDado.Usuario
import zkfood.pedidosapi.usuario.usuarioDado.UsuarioCadastro

@RestController
@RequestMapping("/usuarios")
class UsuarioControlador(
    val usuarioServico:UsuarioServico
) {
    @PostMapping
    fun cadastar(@RequestBody @Valid usuarioCadastro:UsuarioCadastro):ResponseEntity<Usuario>{
        val novoUsuario:Usuario = usuarioServico.cadastrar(usuarioCadastro);

        return ResponseEntity.status(201).body(novoUsuario);
    }

    @GetMapping
    fun listar():ResponseEntity<List<Usuario>>{
        val listaUsuario:List<Usuario> = usuarioServico.listar();

        return ResponseEntity.status(200).body(listaUsuario);
    }
}