package zkfood.pedidosapi.usuario.usuario

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import zkfood.pedidosapi.usuario.usuario.usuarioDado.Usuario
import zkfood.pedidosapi.usuario.usuario.usuarioDado.UsuarioCadastro
import zkfood.pedidosapi.nucleo.enums.IgnorarFormatacaoEnum
import zkfood.pedidosapi.usuario.usuario.usuarioDado.Login
import zkfood.pedidosapi.usuario.usuario.usuarioDado.NovaSenha

@RestController
@RequestMapping("/usuarios")
class UsuarioControlador(
    val usuarioServico: UsuarioServico
) {
    @PostMapping
    fun cadastrar(@RequestBody @Valid usuarioCadastro: UsuarioCadastro): ResponseEntity<Usuario> {
        val novoUsuario: Usuario = usuarioServico.cadastrar(usuarioCadastro);

        return ResponseEntity.status(201).body(novoUsuario);
    }

    @GetMapping("/{id}")
    fun buscarPorId(@PathVariable id: Int): ResponseEntity<Usuario> {
        val usuario: Usuario = usuarioServico.acharPorId(id);

        return ResponseEntity.status(200).body(usuario);
    }

    @GetMapping
    fun listar(): ResponseEntity<List<Usuario>> {
        val listaUsuario: List<Usuario> = usuarioServico.listarEntidade(null, IgnorarFormatacaoEnum.ATIVO);

        return ResponseEntity.status(200).body(listaUsuario);
    }

    @PatchMapping("/{id}")
    fun atualizar(@PathVariable id: Int, @RequestBody usuario: Usuario): ResponseEntity<Usuario> {
        val usuarioAtualizado: Usuario = usuarioServico.atualizar(id, usuario);

        return ResponseEntity.status(200).body(usuarioAtualizado);
    }

    // TODO: Validar se esse método faz sentido
    @DeleteMapping("/{id}")
    fun deletar(@PathVariable id: Int): ResponseEntity<Void> {
        usuarioServico.deletarPorId(id);

        return ResponseEntity.status(204).build();
    }

    @PatchMapping("/{id}/alterar-senha")
    fun alterarSenha(@PathVariable id: Int, @RequestBody novaSenha: NovaSenha): ResponseEntity<Usuario> {
        val usuarioAtualizado: Usuario = usuarioServico.alterarSenha(id, novaSenha);

        return ResponseEntity.status(200).body(usuarioAtualizado);
    }

//    talvez iremos usar no futuro
//    @PatchMapping("/{id}/inativar")
//    fun inativarUsuario(@PathVariable id: Int): ResponseEntity<Usuario> {
//        val usuarioInativado: Usuario = usuarioServico.inativarUsuario(id);
//
//        return ResponseEntity.status(200).body(usuarioInativado);
//    }


    @PostMapping("/entrar")
    fun entrar(@RequestBody @Valid login: Login):ResponseEntity<Usuario>{
        val usuario: Usuario = usuarioServico.entrar(login);

        return ResponseEntity.status(200).body(usuario);
    }

    @PostMapping("/{id}/sair")
    fun sair (@PathVariable id: Int): ResponseEntity<Void>{
        usuarioServico.sair(id);

        return ResponseEntity.status(204).build();
    }

    // método não validado
    @PostMapping("/recuperar-senha")
    fun recuperarSenha (@RequestBody email:String): ResponseEntity<Void>{
        usuarioServico.recuperarSenha(email);

        return ResponseEntity.status(204).build();
    }
}