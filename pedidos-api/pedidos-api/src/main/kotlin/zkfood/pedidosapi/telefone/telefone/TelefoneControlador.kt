package zkfood.pedidosapi.telefone.telefone

import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import zkfood.pedidosapi.telefone.telefone.telefoneDado.Telefone
import zkfood.pedidosapi.telefone.telefone.telefoneDado.TelefoneCadastro

@RestController
@RequestMapping("/usuarios/{idUsuario}/telefone")
class TelefoneControlador (
    val telefoneServico: TelefoneServico
){
    @PostMapping
    fun cadastrar(@RequestBody @Valid telefoneCadastro: TelefoneCadastro):ResponseEntity<Telefone>{
        val novoTelefone: Telefone = telefoneServico.cadastrar(telefoneCadastro)

        return ResponseEntity.status(201).body(novoTelefone)
    }

    @GetMapping
    fun listar():ResponseEntity<List<Telefone>>{
        val listaTelefone:List<Telefone> = telefoneServico.listarEntidade(null, null)

        return ResponseEntity.status(200).body(listaTelefone)
    }

    @PatchMapping("/{id}")
    fun atualizar(@PathVariable id:Int, @RequestBody dto:Telefone):ResponseEntity<Telefone>{
        val telefone:Telefone = telefoneServico.atualizar(id, dto)

        return ResponseEntity.status(200).body(telefone)
    }
}