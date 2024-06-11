package zkfood.pedidosapi.produtos

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import zkfood.pedidosapi.produtos.produtoDado.Produto

interface ProdutoRepositorio:JpaRepository<Produto, Int>{
    @Query("select p.imagem from Produto p where p.id = ?1")
    fun recuperarImagem(id:Int):ByteArray;
}