package zkfood.pedidosapi.usuario.telefone.telefoneDado

import jakarta.persistence.*
import zkfood.pedidosapi.usuario.usuario.usuarioDado.Usuario

@Entity
data class Telefone (
    @field:Id @field:GeneratedValue(strategy = GenerationType.IDENTITY)
    var id:Int? = null,

    @Column(name = "numero", columnDefinition = "CHAR(9)")
    var numero:String? = null,

    @field:ManyToOne
    var usuario:Usuario? = null
)