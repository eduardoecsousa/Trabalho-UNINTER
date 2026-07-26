package raizes_do_nordeste_api.Entity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import raizes_do_nordeste_api.Entity.models.Unidade;

import java.util.UUID;

public interface UnidadeRepository extends JpaRepository<Unidade, UUID> {
}
