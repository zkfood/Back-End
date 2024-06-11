package zkfood.pedidosapi.produtos

import jakarta.validation.Valid
import jakarta.websocket.server.PathParam
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import zkfood.pedidosapi.produtos.produtoDado.Produto
import zkfood.pedidosapi.produtos.produtoDado.ProdutoCadastro

@RestController
@RequestMapping("/produtos")
class ProdutoControlador (
    val produtoServico:ProdutoServico
){
    @PostMapping
    fun cadastrarProduto(@RequestBody @Valid produtoCadastro: ProdutoCadastro):ResponseEntity<Produto>{
        val novoProduto:Produto = produtoServico.cadastrarProduto(produtoCadastro);

        return ResponseEntity.status(201).body(novoProduto);
    }

    @GetMapping
    fun listarProdutos(@RequestParam tipo:String?, @RequestParam disponibilidade:Boolean?):ResponseEntity<List<Produto>>{
        val lista:List<Produto> = produtoServico.listarProdutos(tipo, disponibilidade);

        return ResponseEntity.status(200).body(lista);
    }

    @GetMapping("/{id}")
    fun acharProdutoPorId(@PathVariable id:Int):ResponseEntity<Produto>{
        val produto: Produto = produtoServico.acharPorId(id);

        return ResponseEntity.status(200).body(produto);
    }

    @DeleteMapping("/{id}")
    fun deletar(@PathVariable id:Int):ResponseEntity<Void>{
        produtoServico.deletarPorId(id);

        return ResponseEntity.status(204).build();
    }

    @PatchMapping("/{id}")
    fun atualizar(@PathVariable id: Int, @RequestBody produto: Produto):ResponseEntity<Produto>{
        val produtoAtualizado = produtoServico.atualizar(id, produto);

        return ResponseEntity.status(200).body(produtoAtualizado);
    }

    @PatchMapping(value = ["/imagem/{id}"], consumes = ["image/*"], produces = ["image/jpeg"])
//        consumes = ["image/jpeg", "image/png", "image/gif"])
    fun cadastrarImagem (@PathVariable id: Int, @RequestBody novaImagem: ByteArray):ResponseEntity<Void> {
        produtoServico.cadastrarImagem(id, novaImagem);

        return ResponseEntity.status(204).build();
    }

    @GetMapping(value = ["/imagem/{id}"], produces = ["image/jpeg"])
    fun recuperarImagem(@PathVariable id:Int):ResponseEntity<ByteArray> {
        val imagem = produtoServico.recuperarImagem(id);

        return ResponseEntity.status(200).body(imagem);
    }
}