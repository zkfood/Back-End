package zkfood.pedidosapi.produtos.tipoProduto

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
import zkfood.pedidosapi.produtos.tipoProduto.tipoDado.TipoProduto
import zkfood.pedidosapi.produtos.tipoProduto.tipoDado.TipoCadastro

@RestController
@RequestMapping("/tipo-produtos")
class TipoProdutoControlador (
    val tipoProdutoServico: TipoProdutoServico
){
    @PostMapping
    fun cadastrar(@RequestBody @Valid tipoCadastro: TipoCadastro):ResponseEntity<TipoProduto>{
        val novoTipoProduto:TipoProduto = tipoProdutoServico.cadastrar(tipoCadastro);

        return ResponseEntity.status(201).body(novoTipoProduto);
    }

    @GetMapping
    fun listar():ResponseEntity<List<TipoProduto>>{
        val lista = tipoProdutoServico.listarEntidade(null, null);

        return ResponseEntity.status(200).body(lista);
    }

    @GetMapping("/{id}")
    fun buscarPorId(@PathVariable id:Int):ResponseEntity<TipoProduto>{
        val tipoProduto: TipoProduto = tipoProdutoServico.acharPorId(id);

        return ResponseEntity.status(200).body(tipoProduto);
    }

    @PatchMapping("/{id}")
    fun atualizarTipo(@PathVariable id:Int, @RequestBody tipoProduto: TipoProduto):ResponseEntity<TipoProduto>{
        val atualizado: TipoProduto = tipoProdutoServico.atualizar(id, tipoProduto);

        return ResponseEntity.status(200).body(atualizado);
    }

    @DeleteMapping("/{id}")
    fun deletar(@PathVariable id:Int):ResponseEntity<Void> {
        tipoProdutoServico.deletarPorId(id);

        return ResponseEntity.status(204).build();
    }
}