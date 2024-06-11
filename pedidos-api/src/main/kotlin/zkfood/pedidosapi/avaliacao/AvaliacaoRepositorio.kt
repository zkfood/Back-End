package zkfood.pedidosapi.avaliacao

import org.springframework.data.jpa.repository.JpaRepository
import zkfood.pedidosapi.avaliacao.avaliacaoDado.Avaliacao
import zkfood.pedidosapi.avaliacao.avaliacaoDado.AvaliacaoId

interface AvaliacaoRepositorio:JpaRepository<Avaliacao, AvaliacaoId>