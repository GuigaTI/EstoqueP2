package fatec.br.edu.EstoqueApp.repositories;

import fatec.br.edu.EstoqueApp.models.Loja;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface LojaRepository extends JpaRepository<Loja, Long> {
    @Query("SELECT l FROM Loja l LEFT JOIN FETCH l.produtos WHERE l.id = :id")
    Loja findByIdComProdutos(@Param("id") Long id);
}