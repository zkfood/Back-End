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

@RestController
@RequestMapping("/usuarios/{idUsuario}/telefones")
class TelefoneControlador (
    val telefoneServico: TelefoneServico
){
    @PostMapping
    fun cadastrar(
        @PathVariable idUsuario: Int,
        @RequestBody @Valid telefoneCadastro: TelefoneCadastro
    ):ResponseEntity<Telefone>{
        val novoTelefone: Telefone = telefoneServico.cadastrarDeDTO(telefoneCadastro, idUsuario);

        return ResponseEntity.status(201).body(novoTelefone);
    }

    @GetMapping
    fun listar(@PathVariable idUsuario: Int):ResponseEntity<List<Telefone>>{
        val listaTelefone:List<Telefone> = telefoneServico.listarTelefones(idUsuario);

        return ResponseEntity.status(200).body(listaTelefone);
    }

    @GetMapping("/{id}")
    fun acharPorId(
        @PathVariable id:Int,
        @PathVariable idUsuario: Int
    ):ResponseEntity<Telefone>{
        val telefone: Telefone = telefoneServico.acharPorId(id, idUsuario);

        return ResponseEntity.status(200).body(telefone);
    }

    @PatchMapping("/{id}")
    fun atualizar(
        @PathVariable id:Int,
        @RequestBody dto: Telefone,
        @PathVariable idUsuario: Int
    ):ResponseEntity<Telefone>{
        val telefone: Telefone = telefoneServico.atualizar(id, dto, idUsuario);

        return ResponseEntity.status(200).body(telefone);
    }

    @DeleteMapping("/{id}")
    fun deletar(
        @PathVariable id:Int,
        @PathVariable idUsuario: Int
    ): ResponseEntity<Void> {
        telefoneServico.deletarPorId(id, idUsuario);

        return ResponseEntity.status(204).build();
    }
}