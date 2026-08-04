package raizes_do_nordeste_api.Entity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import raizes_do_nordeste_api.Entity.models.Produto;

import java.util.List;
import java.util.UUID;

public interface ProdutoRepository extends JpaRepository<Produto, UUID> {

    List<Produto> findByAtivoTrue();
    List<Produto> findByCategoria(String categoria);
}
