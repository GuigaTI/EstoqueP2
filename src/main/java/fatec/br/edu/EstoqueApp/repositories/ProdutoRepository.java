package fatec.br.edu.EstoqueApp.repositories;

import fatec.br.edu.EstoqueApp.models.Loja;
import fatec.br.edu.EstoqueApp.models.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface ProdutoRepository extends JpaRepository<Produto, String> {
    //Derivadas
    List<Produto> findByNome(String nome);
    Optional<Produto> findByCodigo(String codigo);
    List<Produto> findByNomeAndCodigo(String nome, String codigo);
    //JPQL
    @Query("SELECT p FROM Produto p ORDER BY p.nome ASC")
    List<Produto> listarTodosOrdenadosPorNome();

    @Query("SELECT p FROM Produto p ORDER BY p.preco ASC")
    List<Produto> listarTodosOrdenadosPorPreco();
    //Nativas
    @Query(value = "SELECT SUM(quantidade * preco) FROM produto", nativeQuery = true)
    Double obterValorTotalEstoque();

    @Query(value = "SELECT p FROM Produto p JOIN p.lojas l WHERE l.id = :lojaId")
    List<Produto> listarProdutosPorLoja(@Param("lojaId") Long lojaId);

    @Query(value = "SELECT SUM(p.quantidade * p.preco) FROM Produto p JOIN p.lojas l WHERE l.id = :lojaId")
    Double calcularValorEstoquePorLoja(@Param("lojaId") Long lojaId);

    @Query(value = "SELECT p FROM Produto p JOIN p.lojas l WHERE l.id = :lojaId AND p.codigo = :codigo")
    Produto buscarProdutoPorCodigoNaLoja(@Param("lojaId") Long lojaId, @Param("codigo") String codigo);

    @Query("SELECT p FROM Produto p JOIN p.lojas l WHERE p.nome LIKE %:nome% AND l.id = :lojaId")
    List<Produto> findByNomeAndLoja(@Param("nome") String nome, @Param("lojaId") Long lojaId);

    @Query("SELECT p FROM Produto p JOIN p.lojas l WHERE p.codigo = :codigo AND l.id = :lojaId")
    Optional<Produto> findByCodigoAndLoja(@Param("codigo") String codigo, @Param("lojaId") Long lojaId);

    @Query("SELECT p FROM Produto p JOIN p.lojas l WHERE l.id = :lojaId ORDER BY p.nome")
    List<Produto> findAllByLojaOrderByNome(@Param("lojaId") Long lojaId);

    @Query("SELECT p FROM Produto p JOIN p.lojas l WHERE l.id = :lojaId ORDER BY p.preco")
    List<Produto> findAllByLojaOrderByPreco(@Param("lojaId") Long lojaId);

    @Query("SELECT p FROM Produto p ORDER BY p.preco")
    List<Produto> findAllOrderByPreco();

    @Query("SELECT SUM(p.preco * p.quantidade) FROM Produto p JOIN p.lojas l WHERE l.id = :lojaId")
    BigDecimal calcularValorTotalPorLoja(@Param("lojaId") Long lojaId);

    @Query("SELECT l FROM Loja l LEFT JOIN FETCH l.produtos WHERE l.id = :id")
    Optional<Loja> findByIdWithProdutos(@Param("id") Long id);


}

