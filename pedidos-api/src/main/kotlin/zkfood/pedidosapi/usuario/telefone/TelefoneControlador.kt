package zkfood.pedidosapi.usuario.telefone

import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import zkfood.pedidosapi.usuario.telefone.telefoneDado.Telefone
import zkfood.pedidosapi.usuario.telefone.telefoneDado.TelefoneCadastro
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses

@RestController
@RequestMapping("/usuarios/{idUsuario}/telefones")
class TelefoneControlador (
    val telefoneServico: TelefoneServico
){
    @PostMapping
    @Operation(summary = "Cadastrar um novo telefone para o usuário")
    @ApiResponses(value = [
        ApiResponse(responseCode = "201", description = "Telefone cadastrado com sucesso"),
        ApiResponse(responseCode = "400", description = "Requisição inválida")
    ])
    fun cadastrar(
        @PathVariable idUsuario: Int,
        @RequestBody @Valid telefoneCadastro: TelefoneCadastro
    ):ResponseEntity<Telefone>{
        val novoTelefone: Telefone = telefoneServico.cadastrar(telefoneCadastro, idUsuario)

        return ResponseEntity.status(201).body(novoTelefone)
    }

    @GetMapping
    @Operation(summary = "Listar todos os telefones do usuário")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Lista de telefones retornada com sucesso")
    ])
    fun listar(@PathVariable idUsuario: Int):ResponseEntity<List<Telefone>>{
        val listaTelefone:List<Telefone> = telefoneServico.listarTelefones(idUsuario)

        return ResponseEntity.status(200).body(listaTelefone)
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar telefone por ID")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Telefone encontrado"),
        ApiResponse(responseCode = "404", description = "Telefone não encontrado")
    ])
    fun acharPorId(
        @PathVariable id:Int,
        @PathVariable idUsuario: Int
    ):ResponseEntity<Telefone>{
        val telefone: Telefone = telefoneServico.acharPorId(id, idUsuario)

        return ResponseEntity.status(200).body(telefone)
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Atualizar telefone por ID")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Telefone atualizado com sucesso"),
        ApiResponse(responseCode = "404", description = "Telefone não encontrado")
    ])
    fun atualizar(
        @PathVariable id:Int,
        @RequestBody dto: Telefone,
        @PathVariable idUsuario: Int
    ):ResponseEntity<Telefone>{
        val telefone: Telefone = telefoneServico.atualizar(id, dto, idUsuario)

        return ResponseEntity.status(200).body(telefone)
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar telefone por ID")
    @ApiResponses(value = [
        ApiResponse(responseCode = "204", description = "Telefone deletado com sucesso"),
        ApiResponse(responseCode = "404", description = "Telefone não encontrado")
    ])
    fun deletar(
        @PathVariable id:Int,
        @PathVariable idUsuario: Int
    ): ResponseEntity<Void> {
        telefoneServico.deletarPorId(id, idUsuario)

        return ResponseEntity.status(204).build()
    }
}