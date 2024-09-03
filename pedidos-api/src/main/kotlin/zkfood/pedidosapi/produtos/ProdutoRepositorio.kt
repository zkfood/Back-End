package zkfood.pedidosapi.produtos

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import zkfood.pedidosapi.produtos.produtoDado.Produto

interface ProdutoRepositorio:JpaRepository<Produto, Int>{
    @Query("select p.imagem from Produto p where p.id = ?1")
    fun recuperarImagem(id:Int):ByteArray;

    // TODO: Criar uma repository própria (pq é o modo mais correto)
    @Query("select * from view_produtos_mais_vendidos", nativeQuery = true)
    fun dashboardProdutos():List<Any>
}