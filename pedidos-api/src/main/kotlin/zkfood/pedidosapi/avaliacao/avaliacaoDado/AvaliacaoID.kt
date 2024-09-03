package zkfood.pedidosapi.avaliacao.avaliacaoDado

import jakarta.persistence.Column
import jakarta.persistence.Embeddable
import java.io.Serializable

@Embeddable
data class AvaliacaoId(
    @Column(name = "usuario_id")
    var usuario: Int? = null,

    @Column(name = "produto_id")
    var produto: Int? = null
) : Serializable