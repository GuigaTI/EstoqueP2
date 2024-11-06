package fatec.br.edu.EstoqueApp.repositories;

import fatec.br.edu.EstoqueApp.models.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProdutoRepository extends JpaRepository<Produto, String> {
    //Derivadas
    List<Produto> findByNome(String nome);
    List<Produto> findByCodigo(String codigo);
    List<Produto> findByNomeAndCodigo(String nome, String codigo);
    //JPQL
    @Query("SELECT p FROM Produto p ORDER BY p.nome ASC")
    List<Produto> listarTodosOrdenadosPorNome();

    @Query("SELECT p FROM Produto p ORDER BY p.preco ASC")
    List<Produto> listarTodosOrdenadosPorPreco();
    //Nativas
    @Query(value = "SELECT SUM(quantidade * preco) FROM produto", nativeQuery = true)
    Double obterValorTotalEstoque();
}

