package zkfood.pedidosapi.produtos

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
import zkfood.pedidosapi.produtos.produtoDado.NovoValorDTO
import zkfood.pedidosapi.produtos.produtoDado.Produto
import zkfood.pedidosapi.produtos.produtoDado.ProdutoCadastro

@RestController
@RequestMapping("/produtos")
class ProdutoControlador (
    val produtoServico:ProdutoServico
){
    @PostMapping("/tipo/{idTipo}")
    fun cadastrarProduto(
        @PathVariable idTipo:Int,
        @RequestBody @Valid produtoCadastro: ProdutoCadastro):ResponseEntity<Produto>{
        val novoProduto:Produto = produtoServico.cadastrarProduto(produtoCadastro, idTipo)

        return ResponseEntity.status(201).body(novoProduto);
    }

    @GetMapping
    fun listarProdutos():ResponseEntity<List<Produto>>{
        val lista:List<Produto> = produtoServico.listarProdutos()

        if(lista.isEmpty()) return ResponseEntity.status(204).build()

        return ResponseEntity.status(200).body(lista)
    }

    @GetMapping("/tipo/{idTipo}")
    fun listarProdutosPorTipo(@PathVariable idTipo:Int):ResponseEntity<List<Produto>>{
        val lista:List<Produto> = produtoServico.listarProdutosPorTipo(idTipo)

        if(lista.isEmpty()) return ResponseEntity.status(204).build()

        return ResponseEntity.status(200).body(lista)
    }

    @GetMapping("/{idProduto}")
    fun listarProdutoPorId(@PathVariable idProduto:Int):ResponseEntity<Produto>{
        val produto: Produto = produtoServico.listarProdutoPorId(idProduto)

        return ResponseEntity.status(200).body(produto);
    }

    @GetMapping("/disponiveis")
    fun listarProdutosDisponiveis():ResponseEntity<List<Produto>>{
        val lista:List<Produto> = produtoServico.listarProdutosDisponiveis()

        if(lista.isEmpty()) return ResponseEntity.status(204).build()

        return ResponseEntity.status(200).body(lista)
    }

    @GetMapping("/indisponiveis")
    fun listarProdutosIndisponiveis():ResponseEntity<List<Produto>>{
        val lista:List<Produto> = produtoServico.listarProdutosIndisponiveis()

        if(lista.isEmpty()) return ResponseEntity.status(204).build()

        return ResponseEntity.status(200).body(lista)
    }

    @GetMapping("/disponiveis/tipo/{idTipo}")
    fun listarProdutosDisponiveisPorTipo(@PathVariable idTipo:Int):ResponseEntity<List<Produto>>{
        val lista:List<Produto> = produtoServico.listarProdutosDisponiveisPorTipo(idTipo)

        if(lista.isEmpty()) return ResponseEntity.status(204).build()

        return ResponseEntity.status(200).body(lista)
    }

    @GetMapping("/indisponiveis/tipo/{idTipo}")
    fun listarProdutosIndisponiveisPorTipo(@PathVariable idTipo:Int):ResponseEntity<List<Produto>>{
        val lista:List<Produto> = produtoServico.listarProdutosIndisponiveisPorTipo(idTipo)

        if(lista.isEmpty()) return ResponseEntity.status(204).build()

        return ResponseEntity.status(200).body(lista)
    }

    @DeleteMapping("/{idProduto}")
    fun deletar(@PathVariable idProduto:Int):ResponseEntity<Void>{
        produtoServico.deletarProduto(idProduto)

        return ResponseEntity.status(204).build()
    }

    @PatchMapping("/{idProduto}")
    fun atualizarValor(@PathVariable idProduto: Int, @RequestBody dto: NovoValorDTO):ResponseEntity<Produto>{
        val produtoAtualizado = produtoServico.atualizarValorProduto(idProduto, dto)

        return ResponseEntity.status(200).body(produtoAtualizado)
    }

}