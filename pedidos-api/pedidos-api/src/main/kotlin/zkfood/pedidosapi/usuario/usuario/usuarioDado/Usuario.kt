package zkfood.pedidosapi.usuario.usuario.usuarioDado

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

@Entity
data class Usuario (
    @field:Id @field:GeneratedValue(strategy = GenerationType.IDENTITY)
    var id:Int? = null,

    var nome:String? = null,

    var email:String? = null,

    @JsonIgnore
    var senha:String? = null,

    var cpf:String? = null
    //var telefone:MutableList<Telefone>// usar many to one só q na classe Telefone
    //var endereco:MutableList<Endereco>// usar many to one só q na classe Endereco
    //var nivelAcesso:MutableList<NivelAcesso>// usar many to one
) {
    constructor(paramEmail:String):this(email = paramEmail);
}