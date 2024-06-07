package zkfood.pedidosapi.produtos.tipo

import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import zkfood.pedidosapi.produtos.tipo.tipoDado.Tipo
import zkfood.pedidosapi.produtos.tipo.tipoDado.TipoCadastro

@RestController
@RequestMapping("/tipos")
class TipoControlador (
    val tipoServico: TipoServico
){
    @PostMapping
    fun cadastrar(@RequestBody @Valid tipoCadastro: TipoCadastro):ResponseEntity<Tipo>{
        val novoTipo:Tipo = tipoServico.cadastrar(tipoCadastro);

        return ResponseEntity.status(201).body(novoTipo)
    }

    @GetMapping
    fun listar():ResponseEntity<List<Tipo>>{
        val lista = tipoServico.listar()

        if(lista.isEmpty()) {
            return ResponseEntity.status(204).build()
        }

        return ResponseEntity.status(200).body(lista)
    }
}