package zkfood.pedidosapi.produto

import io.swagger.v3.oas.annotations.parameters.RequestBody
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import zkfood.pedidosapi.produto.produtoDado.NovoValor
import zkfood.pedidosapi.produto.produtoDado.Produto
import zkfood.pedidosapi.produto.produtoDado.ProdutoCadastro

@RestController
@RequestMapping("/produtos")
class ProdutoControlador(
    val produtoServico: ProdutoServico
) {
    @PostMapping
    fun cadastrarProduto(@RequestBody @Valid produtoCadastro:ProdutoCadastro):ResponseEntity<Produto>{
        val novoProduto:Produto = produtoServico.cadastrar(produtoCadastro);

        return ResponseEntity.status(201).body(novoProduto)
    }

    @GetMapping
    fun listarProdutos():ResponseEntity<List<Produto>>{
        val lista = produtoServico.listarProdutos()

        return ResponseEntity.status(200).body(lista)
    }

    @GetMapping("/{id}")
    fun buscarPorId(@PathVariable id:Int):ResponseEntity<Produto>{
       val produto:Produto = produtoServico.acharPorId(id)

       return ResponseEntity.status(200).body(produto)
    }

    @PatchMapping("/{id}")
    fun atualizarValorProduto(@PathVariable id:Int, @RequestBody novoValor:NovoValor):ResponseEntity<Produto>{
        val produtoAtualizado: Produto = produtoServico.alterarValorUnitario(id, novoValor);

        return ResponseEntity.status(200).body(produtoAtualizado)
    }

    @DeleteMapping("/{id}")
    fun deletarProduto(@PathVariable id:Int):ResponseEntity<Void>{
        produtoServico.deletarProduto(id)

        return ResponseEntity.status(204).build();
    }


}