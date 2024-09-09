package zkfood.pedidosapi.usuario.endereco

import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import zkfood.pedidosapi.usuario.usuario.UsuarioServico
import zkfood.pedidosapi.usuario.endereco.enderecoDado.Endereco
import zkfood.pedidosapi.usuario.endereco.enderecoDado.EnderecoCadastro

@RestController
@RequestMapping("/usuarios/{idUsuario}/enderecos")
class EnderecoControlador (
    val enderecoServico: EnderecoServico,
){
    @PostMapping
    fun cadastrar(
        @PathVariable idUsuario: Int,
        @RequestBody @Valid enderecoCadastro: EnderecoCadastro
    ): ResponseEntity<Endereco> {
        val novoEndereco: Endereco = enderecoServico.cadastrarDeDTO(enderecoCadastro, idUsuario);

        return ResponseEntity.status(201).body(novoEndereco);
    }

    @GetMapping
    fun listar(@PathVariable idUsuario:Int):ResponseEntity<List<Endereco>>{
        val listaEndereco:List<Endereco> = enderecoServico.listarEnderecos(idUsuario);

        return ResponseEntity.status(200).body(listaEndereco);
    }

    @GetMapping("/{id}")
    fun acharPorId(
        @PathVariable id:Int,
        @PathVariable idUsuario: Int
    ):ResponseEntity<Endereco>{
        val endereco: Endereco = enderecoServico.acharPorId(id, idUsuario);

        return ResponseEntity.status(200).body(endereco);
    }

    @PatchMapping("/{id}")
    fun atualizar(
        @PathVariable id:Int,
        @RequestBody dto: Endereco,
        @PathVariable idUsuario:Int
    ):ResponseEntity<Endereco>{
        val endereco: Endereco = enderecoServico.atualizar(id, dto, idUsuario);

        return ResponseEntity.status(200).body(endereco);
    }

    @DeleteMapping("/{id}")
    fun deletar(
        @PathVariable id:Int,
        @PathVariable idUsuario: Int
    ): ResponseEntity<Void> {
        enderecoServico.deletarPorId(id, idUsuario);

        return ResponseEntity.status(204).build();
    }
}