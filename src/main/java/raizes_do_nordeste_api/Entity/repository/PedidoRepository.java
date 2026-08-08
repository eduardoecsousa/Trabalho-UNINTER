package raizes_do_nordeste_api.Entity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import raizes_do_nordeste_api.Entity.models.Pedido;
import raizes_do_nordeste_api.enums.CanalPedido;
import raizes_do_nordeste_api.enums.StatusPedido;

import java.util.List;
import java.util.UUID;

public interface PedidoRepository extends JpaRepository<Pedido, UUID> {
    public List<Pedido> findByUsuarioId(UUID id);
    public List<Pedido> findByCanalPedido(CanalPedido canalPedido);

    public List<Pedido> findByStatus(StatusPedido statusPedido);
}
