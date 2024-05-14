package zkfood.pedidosapi.endereco.endereco

import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import zkfood.pedidosapi.endereco.endereco.enderecoDado.Endereco
import zkfood.pedidosapi.endereco.endereco.enderecoDado.EnderecoCadastro
import zkfood.pedidosapi.usuario.usuario.usuarioDado.Usuario

@RestController
@RequestMapping("/endereco")
class EnderecoControlador (
    val enderecoServico: EnderecoServico
){
    @PostMapping
    fun cadastrar(@RequestBody @Valid enderecoCadastro:EnderecoCadastro): ResponseEntity<Endereco> {
        val novoEndereco: Endereco = enderecoServico.cadastrar(enderecoCadastro)

        return ResponseEntity.status(201).body(novoEndereco)
    }

    @GetMapping
    fun listar():ResponseEntity<List<Endereco>>{
        val listaEndereco:List<Endereco> = enderecoServico.listarEntidade(null, null)

        return ResponseEntity.status(200).body(listaEndereco)
    }

    @PatchMapping("/{id}")
    fun atualizar(@PathVariable id:Int, @RequestBody dto: Endereco):ResponseEntity<Endereco>{
        val endereco: Endereco = enderecoServico.atualizar(id, dto);

        return ResponseEntity.status(200).body(endereco);
    }
}