package zkfood.pedidosapi.validadorDistancia

import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import zkfood.pedidosapi.validadorDistancia.validadorDistanciaDado.DistanceMatrixResposta
import zkfood.pedidosapi.validadorDistancia.validadorDistanciaErros.DistanciaMaiorDoQueOPermitidoExcecao

@Service
class ValidadorDistanciaServico(
) {
    fun validarDistancia(cep: String) {
        val webClient = WebClient.create("https://maps.googleapis.com")
        val response = webClient.get()
            .uri {
                it.path("/maps/api/distancematrix/json")
                    .queryParam("origins", "$cep, BR")
                    .queryParam("destinations", "08490-490, BR")
                    .queryParam("key", "AIzaSyA9L9aRnOxFEPUZqUOXdoeMYrI5YgTj65k")
                    .build()
            }
            .retrieve()
            .bodyToMono(DistanceMatrixResposta::class.java)
            .block()

        val distancia = response?.rows!![0].elements[0].distance.text.split(" ")[0].toDouble()

//        if (distancia > 3.0) {
//            throw DistanciaMaiorDoQueOPermitidoExcecao(cep, distancia);
//        }
    }
}