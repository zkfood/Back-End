package zkfood.pedidosapi.contato

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import zkfood.pedidosapi.contato.contatoDado.Contato
import zkfood.pedidosapi.contato.contatoDado.ContatoCadastro

@RestController
@RequestMapping("/contatos")
class ContatoControlador(val servico: ContatoServico) {

    @GetMapping
    fun listar(): List<Contato> = servico.listar()

    @GetMapping("/{id}")
    fun encontrarPorId(@PathVariable id: Long): ResponseEntity<Contato> {
        val contato = servico.encontrarPorId(id) ?: return ResponseEntity.notFound().build()
        return ResponseEntity.ok(contato)
    }

    @PostMapping
    fun criar(@RequestBody contatoCadastro: ContatoCadastro): Contato = servico.criar(contatoCadastro)

    @PutMapping("/{id}")
    fun atualizar(@PathVariable id: Long, @RequestBody contatoCadastro: ContatoCadastro): ResponseEntity<Contato> {
        val contato = servico.atualizar(id, contatoCadastro) ?: return ResponseEntity.notFound().build()
        return ResponseEntity.ok(contato)
    }

    @DeleteMapping("/{id}")
    fun deletar(@PathVariable id: Long): ResponseEntity<Void> {
        servico.deletar(id)
        return ResponseEntity.noContent().build()
    }
}
