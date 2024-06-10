package zkfood.pedidosapi.contato

import org.springframework.data.jpa.repository.JpaRepository
import zkfood.pedidosapi.contato.contatoDado.Contato

interface ContatoRepositorio : JpaRepository<Contato, Long>

