package zkfood.pedidosapi.pedidos.pedido

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import zkfood.pedidosapi.pedidos.pedido.pedidoDado.Pedido
import zkfood.pedidosapi.relatorios.financeiro.RelatorioFinanceiroDado.TopReceitasRespostaDto
import java.time.LocalDateTime

@Repository
interface PedidoRepositorio: JpaRepository<Pedido, Int>{

    @Query("""
        SELECT 
        p.tipo_entrega,
        SUM(pr.valor * pu.quantidade) AS receita
    FROM pedido p
    JOIN pedido_unitario pu ON p.id = pu.pedido_id
    JOIN produto pr ON pu.produto_id = pr.id
    JOIN estado_pedido_historico eph ON p.id = eph.pedido_id
    WHERE DATE(eph.hora) = DATE(:data)
    GROUP BY p.tipo_entrega
    """, nativeQuery = true)
    fun financeiroKpis(
        data: LocalDateTime
    ): List<Map<String, Number>>

    @Query("""
    SELECT 
        MONTH(eph.hora) AS mes,
        p.tipo_entrega,
        SUM(pr.valor * pu.quantidade) AS receita
    FROM pedido p
    JOIN pedido_unitario pu ON p.id = pu.pedido_id
    JOIN produto pr ON pu.produto_id = pr.id
    JOIN estado_pedido_historico eph ON p.id = eph.pedido_id
    WHERE YEAR(eph.hora) = :ano
    GROUP BY MONTH(eph.hora), p.tipo_entrega
    ORDER BY mes, p.tipo_entrega
""", nativeQuery = true)
    fun receitaAnoMeses(
        ano: Int
    ): List<List<String>>

    @Query("""
        SELECT 
        YEAR(eph.hora) AS ano,
        p.tipo_entrega as tipoEntrega,
        SUM(pr.valor * pu.quantidade) AS receita
    FROM pedido p
    JOIN pedido_unitario pu ON p.id = pu.pedido_id
    JOIN produto pr ON pu.produto_id = pr.id
    JOIN estado_pedido_historico eph ON p.id = eph.pedido_id
    GROUP BY YEAR(eph.hora), p.tipo_entrega
    ORDER BY ano, p.tipo_entrega
    """, nativeQuery = true)
    fun receitaPorAnos(): List<List<String>>

    @Query("""
    SELECT 
        pr.nome AS produto,
        SUM(pu.quantidade) AS quantidadeVendida,
        SUM(pu.quantidade * pr.valor) AS receita
    FROM pedido p
    JOIN pedido_unitario pu ON p.id = pu.pedido_id
    JOIN produto pr ON pu.produto_id = pr.id
    JOIN estado_pedido_historico eph ON p.id = eph.pedido_id
    WHERE MONTH(eph.hora) = :mes AND YEAR(eph.hora) = :ano
    GROUP BY pr.nome
    ORDER BY receita DESC
""", nativeQuery = true)
    fun topReceitas(
        mes: Int,
        ano: Int
    ): List<List<String>>

}