package zkfood.pedidosapi.validadorDistancia

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.reactive.function.client.WebClient
import zkfood.pedidosapi.validadorDistancia.validadorDistanciaDado.DistanceMatrixResposta

@RestController
@RequestMapping("/validador-distancia")
class ValidadorDistanciaControlador(
    val validadorDistanciaServico: ValidadorDistanciaServico
) {

    @PostMapping("/{cep}")
    fun validarDistancia(@PathVariable cep: String): ResponseEntity<Unit> {
        validadorDistanciaServico.validarDistancia(cep);

        return ResponseEntity.status(204).build();
    }
}