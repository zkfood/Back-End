package zkfood.pedidosapi.avaliacao.avaliacaoDado

import jakarta.persistence.Column
import jakarta.persistence.Embeddable
import java.io.Serializable

@Embeddable
data class AvaliacaoId(
    @Column(name = "usuario_id")
    var usuario: Int = 0,

    @Column(name = "produto_id")
    var produto: Int = 0
) : Serializable