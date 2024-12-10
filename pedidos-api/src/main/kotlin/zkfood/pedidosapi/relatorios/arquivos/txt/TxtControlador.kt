package zkfood.pedidosapi.relatorios.arquivos.txt

import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/relatorios/txt")
class TxtControlador(
    val txtServico: TxtServico
) {
    @PostMapping("/saidas-do-dia")
    fun relatorioSaidasDoDia(@RequestParam data: String): ResponseEntity<ByteArray> {
        val arquivo = txtServico.relatorioSaidasDoDia(data);

        val cabecalho = HttpHeaders().apply {
            contentType = MediaType("text", "txt");
            setContentDispositionFormData("attachment", "zkfood.txt");
        }

        return ResponseEntity(arquivo, cabecalho, HttpStatus.OK);
    }
}