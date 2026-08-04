package raizes_do_nordeste_api.Entity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import raizes_do_nordeste_api.Entity.models.Estoque;

import java.util.Optional;
import java.util.UUID;

public interface EstoqueRepository extends JpaRepository<Estoque, UUID> {
    Optional<Estoque> findByUnidadeIdAndProdutoId(UUID unidadeId, UUID produtoId);
}
