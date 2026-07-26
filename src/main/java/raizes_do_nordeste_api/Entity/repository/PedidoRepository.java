package raizes_do_nordeste_api.Entity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import raizes_do_nordeste_api.Entity.models.Pedido;

import java.util.UUID;

public interface PedidoRepository extends JpaRepository<Pedido, UUID> {
}
