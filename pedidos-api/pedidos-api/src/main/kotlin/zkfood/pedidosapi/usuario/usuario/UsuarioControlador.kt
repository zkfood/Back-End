package zkfood.pedidosapi.usuario.usuario

import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import zkfood.pedidosapi.usuario.usuario.usuarioDado.Usuario
import zkfood.pedidosapi.usuario.usuario.usuarioDado.UsuarioCadastro
import zkfood.pedidosapi.nucleo.enums.IgnorarFormatacaoEnum
@RestController
@RequestMapping("/usuarios")
class UsuarioControlador(
    val usuarioServico: UsuarioServico
) {
    @PostMapping
    fun cadastrar(@RequestBody @Valid usuarioCadastro: UsuarioCadastro): ResponseEntity<Usuario> {
        val novoUsuario: Usuario = usuarioServico.cadastrar(usuarioCadastro)
        return ResponseEntity.status(HttpStatus.CREATED).body(novoUsuario)
    }

    @GetMapping("/{id}")
    fun buscarPorId(@PathVariable id: Int): ResponseEntity<Usuario> {
        val usuario: Usuario = usuarioServico.acharPorId(id)
        return ResponseEntity.ok(usuario)
    }

    @GetMapping
    fun listar(): ResponseEntity<List<Usuario>> {
        val listaUsuario: List<Usuario> = usuarioServico.listarEntidade(null, IgnorarFormatacaoEnum.ATIVO)
        return ResponseEntity.ok(listaUsuario)
    }

    @PatchMapping("/{id}")
    fun atualizar(@PathVariable id: Int, @RequestBody usuario: Usuario): ResponseEntity<Usuario> {
        val usuarioAtualizado: Usuario = usuarioServico.atualizar(id, usuario)
        return ResponseEntity.ok(usuarioAtualizado)
    }

    @DeleteMapping("/{id}")
    fun deletar(@PathVariable id: Int): ResponseEntity<Void> {
        usuarioServico.deletarPorId(id)
        return ResponseEntity.noContent().build()
    }

    //não esta no escopo
 /*   @GetMapping("/buscar-por-email")
    fun buscarPorEmail(@RequestParam email: String): ResponseEntity<Usuario> {
        val usuario: Usuario = usuarioServico.buscarPorEmail(email)
        return ResponseEntity.ok(usuario)
    }
*/

    @PatchMapping("/{id}/alterar-senha")
    fun alterarSenha(@PathVariable id: Int, @RequestBody novaSenha: String): ResponseEntity<Usuario> {
        val usuarioAtualizado: Usuario = usuarioServico.alterarSenha(id, novaSenha)
        return ResponseEntity.ok(usuarioAtualizado)
    }

    @GetMapping("/buscar-por-nome")
    fun buscarPorNome(@RequestParam nome: String): ResponseEntity<List<Usuario>> {
        val usuarioExemplo = Usuario(nome = nome)
        val usuarios: List<Usuario> = usuarioServico.listarEntidade(usuarioExemplo, IgnorarFormatacaoEnum.ATIVO)
        return ResponseEntity.ok(usuarios)
    }

    @GetMapping("/contar-usuarios")
    fun contarUsuarios(): ResponseEntity<Long> {
        val totalUsuarios: Long = usuarioServico.contarUsuarios()
        return ResponseEntity.ok(totalUsuarios)
    }

    @PatchMapping("/{id}/inativar")
    fun inativarUsuario(@PathVariable id: Int): ResponseEntity<Usuario> {
        val usuarioInativado: Usuario = usuarioServico.inativarUsuario(id)
        return ResponseEntity.ok(usuarioInativado)
    }
}
