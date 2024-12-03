package zkfood.pedidosapi.usuario.usuario.usuarioDado

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.*
import zkfood.pedidosapi.usuario.telefone.telefoneDado.Telefone

@Entity
data class Usuario (
    @field:Id @field:GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null,

    var nome: String? = null,

    var email: String? = null,

    @JsonIgnore
    var senha: String? = null,

    @Column(name = "cpf", columnDefinition = "CHAR(11)")
    var cpf: String? = null
)