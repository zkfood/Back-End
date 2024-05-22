package zkfood.pedidosapi.usuario.usuario.usuarioDado

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.OneToMany
import zkfood.pedidosapi.usuario.telefone.telefoneDado.Telefone

@Entity
data class Usuario (
    @field:Id @field:GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null,

    var nome: String? = null,

    var email: String? = null,

//    @JsonIgnore
    var senha: String? = null,

    var cpf: String? = null,

    var autenticado: Boolean = false,
)