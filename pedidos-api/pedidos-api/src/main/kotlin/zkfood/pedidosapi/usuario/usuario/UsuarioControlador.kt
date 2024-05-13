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
        val listaUsuario: List<Usuario> = usuarioServico.listarEntidade(null, null)
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

    //usuário por e-mail
    @GetMapping("/buscar-por-email")
    fun buscarPorEmail(@RequestParam email: String): ResponseEntity<Usuario> {
        val usuario: Usuario = usuarioServico.buscarPorEmail(email)
        return ResponseEntity.ok(usuario)
    }

    // CPF
    @GetMapping("/validar-cpf")
    fun validarCpf(@RequestParam cpf: String): ResponseEntity<String> {
        val mensagem: String = if (usuarioServico.validarCpf(cpf)) {
            "CPF válido"
        } else {
            "CPF inválido"
        }
        return ResponseEntity.ok(mensagem)
    }

    //ALTERAR senha, nn sei se vai funcionar na pratica
    @PatchMapping("/{id}/alterar-senha")
    fun alterarSenha(@PathVariable id: Int, @RequestParam novaSenha: String): ResponseEntity<Usuario> {
        val usuarioAtualizado: Usuario = usuarioServico.alterarSenha(id, novaSenha)
        return ResponseEntity.ok(usuarioAtualizado)
    }

    //buscar por nome
    @GetMapping("/buscar-por-nome")
    fun buscarPorNome(@RequestParam nome: String): ResponseEntity<List<Usuario>> {
        val usuarios: List<Usuario> = usuarioServico.buscarPorNome(nome)
        return ResponseEntity.ok(usuarios)
    }

    //classe servico eu te amo

    //total de usuarios, metrica de vaidade eu sei
    @GetMapping("/contar-usuarios")
    fun contarUsuarios(): ResponseEntity<Long> {
        val totalUsuarios: Long = usuarioServico.contarUsuarios()
        return ResponseEntity.ok(totalUsuarios)
    }

    //inativar usuário
    @PatchMapping("/{id}/inativar")
    fun inativarUsuario(@PathVariable id: Int): ResponseEntity<Usuario> {
        val usuarioInativado: Usuario = usuarioServico.inativarUsuario(id)
        return ResponseEntity.ok(usuarioInativado)
    }



}
