package zkfood.pedidosapi.usuario.usuario

import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import zkfood.pedidosapi.usuario.usuario.usuarioDado.Usuario
import zkfood.pedidosapi.usuario.usuario.usuarioDado.UsuarioCadastro
import zkfood.pedidosapi.nucleo.enums.IgnorarFormatacaoEnum
import zkfood.pedidosapi.usuario.usuario.usuarioDado.Login
import zkfood.pedidosapi.usuario.usuario.usuarioDado.NovaSenha
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
@RestController
@RequestMapping("/usuarios")
class UsuarioControlador(
    val usuarioServico: UsuarioServico
) {
    @PostMapping
    @Operation(summary = "Cadastrar um novo usuário")
    @ApiResponses(value = [
        ApiResponse(responseCode = "201", description = "Usuário cadastrado com sucesso"),
        ApiResponse(responseCode = "400", description = "Requisição inválida")
    ])
    fun cadastrar(@RequestBody @Valid usuarioCadastro: UsuarioCadastro): ResponseEntity<Usuario> {
        val novoUsuario: Usuario = usuarioServico.cadastrar(usuarioCadastro)
        return ResponseEntity.status(201).body(novoUsuario)
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar usuário por ID")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Usuário encontrado"),
        ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    ])
    fun buscarPorId(@PathVariable id: Int): ResponseEntity<Usuario> {
        val usuario: Usuario = usuarioServico.acharPorId(id)
        return ResponseEntity.status(200).body(usuario)
    }

    @GetMapping
    @Operation(summary = "Listar todos os usuários")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Lista de usuários retornada com sucesso")
    ])
    fun listar(): ResponseEntity<List<Usuario>> {
        val listaUsuario: List<Usuario> = usuarioServico.listarEntidade(null, IgnorarFormatacaoEnum.ATIVO)
        return ResponseEntity.status(200).body(listaUsuario)
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Atualizar usuário por ID")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Usuário atualizado com sucesso"),
        ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    ])
    fun atualizar(@PathVariable id: Int, @RequestBody usuario: Usuario): ResponseEntity<Usuario> {
        val usuarioAtualizado: Usuario = usuarioServico.atualizar(id, usuario)
        return ResponseEntity.status(200).body(usuarioAtualizado)
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar usuário por ID")
    @ApiResponses(value = [
        ApiResponse(responseCode = "204", description = "Usuário deletado com sucesso"),
        ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    ])
    fun deletar(@PathVariable id: Int): ResponseEntity<Void> {
        usuarioServico.deletarPorId(id)
        return ResponseEntity.status(204).build()
    }

    @PatchMapping("/{id}/alterar-senha")
    @Operation(summary = "Alterar senha do usuário")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Senha alterada com sucesso"),
        ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    ])
    fun alterarSenha(@PathVariable id: Int, @RequestBody novaSenha: NovaSenha): ResponseEntity<Usuario> {
        val usuarioAtualizado: Usuario = usuarioServico.alterarSenha(id, novaSenha)
        return ResponseEntity.status(200).body(usuarioAtualizado)
    }

    @PostMapping("/entrar")
    @Operation(summary = "Login do usuário")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Login bem sucedido"),
        ApiResponse(responseCode = "401", description = "Credenciais inválidas")
    ])
    fun entrar(@RequestBody @Valid login: Login): ResponseEntity<Usuario> {
        val usuario: Usuario = usuarioServico.entrar(login)
        return ResponseEntity.status(200).body(usuario)
    }

    @PostMapping("/{id}/sair")
    @Operation(summary = "Logout do usuário")
    @ApiResponses(value = [
        ApiResponse(responseCode = "204", description = "Logout bem sucedido"),
        ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    ])
    fun sair(@PathVariable id: Int): ResponseEntity<Void> {
        usuarioServico.sair(id)
        return ResponseEntity.status(204).build()
    }

    @PostMapping("/recuperar-senha")
    @Operation(summary = "Recuperar senha do usuário")
    @ApiResponses(value = [
        ApiResponse(responseCode = "204", description = "E-mail de recuperação enviado com sucesso"),
        ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    ])
    fun recuperarSenha(@RequestBody email: String): ResponseEntity<Void> {
        usuarioServico.recuperarSenha(email)
        return ResponseEntity.status(204).build()
    }
}