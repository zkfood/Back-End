package zkfood.pedidosapi.validadorDistancia.validadorDistanciaErros

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(code = HttpStatus.I_AM_A_TEAPOT)
class DistanciaMaiorDoQueOPermitidoExcecao:RuntimeException {
    constructor(cep: String, distancia: Double):super("Infelizmente seu cep $cep, tem distancia de ${distancia}Km do restaurante, não aceitamos pedidos online depois de 3Km!");
}