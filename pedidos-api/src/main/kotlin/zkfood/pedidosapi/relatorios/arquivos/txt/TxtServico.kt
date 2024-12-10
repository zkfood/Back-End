package zkfood.pedidosapi.relatorios.arquivos.txt

import org.springframework.stereotype.Service

@Service
class TxtServico {
    fun relatorioSaidasDoDia(data: String): ByteArray{
        return data.toByteArray()
    }
}