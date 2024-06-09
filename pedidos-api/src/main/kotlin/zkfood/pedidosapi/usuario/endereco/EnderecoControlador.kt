package zkfood.pedidosapi.usuario.endereco

import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import zkfood.pedidosapi.usuario.usuario.UsuarioServico
import zkfood.pedidosapi.usuario.endereco.enderecoDado.Endereco
import zkfood.pedidosapi.usuario.endereco.enderecoDado.EnderecoCadastro
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses

@RestController
@RequestMapping("/usuarios/{idUsuario}/enderecos")
class EnderecoControlador (
    val enderecoServico: EnderecoServico,
){
    @PostMapping
    @Operation(summary = "Cadastrar um novo endereço para o usuário")
    @ApiResponses(value = [
        ApiResponse(responseCode = "201", description = "Endereço cadastrado com sucesso"),
        ApiResponse(responseCode = "400", description = "Requisição inválida")
    ])
    fun cadastrar(
        @PathVariable idUsuario: Int,
        @RequestBody @Valid enderecoCadastro: EnderecoCadastro
    ): ResponseEntity<Endereco> {
        val novoEndereco: Endereco = enderecoServico.cadastrar(enderecoCadastro, idUsuario)
        return ResponseEntity.status(201).body(novoEndereco)
    }

    @GetMapping
    @Operation(summary = "Listar todos os endereços do usuário")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Lista de endereços retornada com sucesso")
    ])
    fun listar(@PathVariable idUsuario:Int):ResponseEntity<List<Endereco>>{
        val listaEndereco:List<Endereco> = enderecoServico.listarEnderecos(idUsuario)
        return ResponseEntity.status(200).body(listaEndereco)
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar endereço por ID")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Endereço encontrado"),
        ApiResponse(responseCode = "404", description = "Endereço não encontrado")
    ])
    fun acharPorId(
        @PathVariable id:Int,
        @PathVariable idUsuario: Int
    ):ResponseEntity<Endereco>{
        val endereco: Endereco = enderecoServico.acharPorId(id, idUsuario)
        return ResponseEntity.status(200).body(endereco)
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Atualizar endereço por ID")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Endereço atualizado com sucesso"),
        ApiResponse(responseCode = "404", description = "Endereço não encontrado")
    ])
    fun atualizar(
        @PathVariable id:Int,
        @RequestBody dto: Endereco,
        @PathVariable idUsuario:Int
    ):ResponseEntity<Endereco>{
        val endereco: Endereco = enderecoServico.atualizar(id, dto, idUsuario)
        return ResponseEntity.status(200).body(endereco)
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar endereço por ID")
    @ApiResponses(value = [
        ApiResponse(responseCode = "204", description = "Endereço deletado com sucesso"),
        ApiResponse(responseCode = "404", description = "Endereço não encontrado")
    ])
    fun deletar(
        @PathVariable id:Int,
        @PathVariable idUsuario: Int
    ): ResponseEntity<Void> {
        enderecoServico.deletarPorId(id, idUsuario)
        return ResponseEntity.status(204).build()
    }
}