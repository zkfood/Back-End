package zkfood.pedidosapi.usuario.usuario.endereco

import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import zkfood.pedidosapi.usuario.usuario.UsuarioServico
import zkfood.pedidosapi.usuario.usuario.endereco.enderecoDado.Endereco
import zkfood.pedidosapi.usuario.usuario.endereco.enderecoDado.EnderecoCadastro
import zkfood.pedidosapi.usuario.usuario.usuarioDado.Usuario

@RestController
@RequestMapping("/usuarios/{idUsuario}/enderecos")
class EnderecoControlador (
    val enderecoServico: EnderecoServico,
    val usuarioServico: UsuarioServico
){
    @PostMapping
    fun cadastrar(@PathVariable idUsuario:Int, @RequestBody @Valid enderecoCadastro: EnderecoCadastro): ResponseEntity<Endereco> {
        usuarioServico.acharPorId(idUsuario)

        val novoEndereco: Endereco = enderecoServico.cadastrar(enderecoCadastro)

        return ResponseEntity.status(201).body(novoEndereco)
    }

    @GetMapping
    fun listar(@PathVariable idUsuario:Int):ResponseEntity<List<Endereco>>{
        usuarioServico.acharPorId(idUsuario)

        val listaEndereco:List<Endereco> = enderecoServico.listarEntidade(null, null)

        return ResponseEntity.status(200).body(listaEndereco)
    }

    @PatchMapping
    fun atualizar(@PathVariable idUsuario:Int, @PathVariable idEndereco:Int, @RequestBody dto: Endereco):ResponseEntity<Endereco>{
        usuarioServico.acharPorId(idUsuario)

        val endereco: Endereco = enderecoServico.atualizar(idEndereco, dto);

        return ResponseEntity.status(200).body(endereco);
    }

    @DeleteMapping
    fun deletar(@PathVariable idUsuario: Int, @PathVariable idEndereco: Int): ResponseEntity<Unit> {
        usuarioServico.acharPorId(idUsuario)

        enderecoServico.deletar(idEndereco)

        return ResponseEntity.status(204).build()
    }
}