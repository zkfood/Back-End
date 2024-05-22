package zkfood.pedidosapi.usuario.endereco.enderecoDado

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.ManyToOne
import zkfood.pedidosapi.usuario.usuario.usuarioDado.Usuario

@Entity
data class Endereco(
    @field:Id @field:GeneratedValue(strategy = GenerationType.IDENTITY)
    var id:Int? = null,

    var cep:String? = null,

    var rua:String? = null,

    var bairro:String? = null,

    var numero:String? = null,

    var complemento:String? = null,

    @field:ManyToOne
    var usuario:Usuario? = null
)