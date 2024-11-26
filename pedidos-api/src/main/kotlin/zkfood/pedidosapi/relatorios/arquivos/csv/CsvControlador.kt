package zkfood.pedidosapi.relatorios.arquivos.csv

import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/relatorios/csv")
class CsvControlador(
    val csvServico: CsvServico
) {
    @PostMapping("/top-pratos")
    fun topPratos(
        @RequestParam mes: Int,
        @RequestParam ano: Int
    ): ResponseEntity<ByteArray> {
        val arquivo = csvServico.topReceitasMesAno(mes, ano);

        val cabecalho = HttpHeaders().apply {
            contentType = MediaType("text", "csv");
            setContentDispositionFormData("attachment", "zkfood.csv");
        }

        return ResponseEntity(arquivo, cabecalho, HttpStatus.OK);
    }

    @PostMapping("/saidas-do-dia")
    fun relatorioSaidasDoDia(@RequestParam data: String): ResponseEntity<ByteArray> {
        val arquivo = csvServico.relatorioSaidasDoDia(data);

        val cabecalho = HttpHeaders().apply {
            contentType = MediaType("text", "csv");
            setContentDispositionFormData("attachment", "zkfood.csv");
        }

        return ResponseEntity(arquivo, cabecalho, HttpStatus.OK);
    }

    @PostMapping("/upload-motoboy")
    fun uploadMotoboy(@RequestParam("file") file: MultipartFile): ResponseEntity<String> {
        csvServico.uploadMotoboy(file);
        return ResponseEntity.status(200).body("Dados dos motoboy cadastrados com sucesso");
    }
}