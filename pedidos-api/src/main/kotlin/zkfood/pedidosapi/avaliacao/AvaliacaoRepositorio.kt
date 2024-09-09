package zkfood.pedidosapi.avaliacao

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.jpa.repository.query.Procedure
import zkfood.pedidosapi.avaliacao.avaliacaoDado.Avaliacao
import zkfood.pedidosapi.avaliacao.avaliacaoDado.AvaliacaoId

interface AvaliacaoRepositorio:JpaRepository<Avaliacao, AvaliacaoId>{
    @Query("""
        SELECT 
            p.nome AS prato,
            COUNT(*) AS quantidade
        FROM 
            avaliacao a
        JOIN 
            produto p ON a.produto_id = p.id
        WHERE 
            a.qtd_estrelas IN (4, 5)
        GROUP BY 
            p.nome
        ORDER BY 
            quantidade DESC
        LIMIT 5
    """, nativeQuery = true)
    fun acharTop5PratosCom4ou5Estrelas(): List<Map<String, Any>>

    @Query("""
        SELECT 
            p.nome AS prato,
            COUNT(*) AS quantidade
        FROM 
            avaliacao a
        JOIN 
            produto p ON a.produto_id = p.id
        WHERE 
            a.qtd_estrelas IN (1, 2)
        GROUP BY 
            p.nome
        ORDER BY 
            quantidade DESC
        LIMIT 5
    """, nativeQuery = true)
    fun acharTop5PratosCom1ou2Estrelas(): List<Map<String, Any>>

    @Query("""
    SELECT
        star_counts.estrelas AS estrela,
        COALESCE(COUNT(a.qtd_estrelas), 0) AS quantidade
    FROM 
        (SELECT 1 AS estrelas UNION ALL
         SELECT 2 UNION ALL
         SELECT 3 UNION ALL
         SELECT 4 UNION ALL
         SELECT 5) AS star_counts
    LEFT JOIN 
        avaliacao a ON a.qtd_estrelas = star_counts.estrelas
    GROUP BY 
        star_counts.estrelas
    ORDER BY 
        estrela
""", nativeQuery = true)
    fun quantidadeAvaliacoesPorEstrelas(): List<Map<String, Any>>

    @Query("SELECT a.descricao FROM Avaliacao a")
    fun nuvemDePalavras(): List<String>
}