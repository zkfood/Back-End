package zkfood.pedidosapi.usuario.usuario

import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import zkfood.pedidosapi.usuario.usuario.usuarioDado.Usuario
import zkfood.pedidosapi.usuario.usuario.usuarioDado.UsuarioCadastro

@RestController
@RequestMapping("/usuarios")
class UsuarioControlador(
    val usuarioServico: UsuarioServico
) {
    @PostMapping
    fun cadastar(@RequestBody @Valid usuarioCadastro: UsuarioCadastro):ResponseEntity<Usuario>{
        val novoUsuario: Usuario = usuarioServico.cadastrar(usuarioCadastro);

        return ResponseEntity.status(201).body(novoUsuario);
    }
    @GetMapping
    fun listar():ResponseEntity<List<Usuario>>{
        val listaUsuario:List<Usuario> = usuarioServico.listarEntidade(null,null)

        return ResponseEntity.status(200).body(listaUsuario);
    }
    @PatchMapping("/{id}")
    fun atualizar(@PathVariable id:Int, @RequestBody dto:Usuario):ResponseEntity<Usuario>{
        val usuario:Usuario = usuarioServico.atualizar(id, dto);

        return ResponseEntity.status(200).body(usuario);
    }
}