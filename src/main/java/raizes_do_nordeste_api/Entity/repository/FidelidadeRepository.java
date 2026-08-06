package raizes_do_nordeste_api.Entity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import raizes_do_nordeste_api.Entity.models.Fidelidade;

import java.util.Optional;
import java.util.UUID;

public interface FidelidadeRepository extends JpaRepository<Fidelidade, UUID> {
    Optional<Fidelidade> findByUsuarioId(UUID id);
}
