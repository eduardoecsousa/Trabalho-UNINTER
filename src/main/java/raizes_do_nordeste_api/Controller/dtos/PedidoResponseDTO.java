package raizes_do_nordeste_api.Controller.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import raizes_do_nordeste_api.Entity.models.Pedido;
import raizes_do_nordeste_api.enums.CanalPedido;
import raizes_do_nordeste_api.enums.StatusPedido;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@AllArgsConstructor
public class PedidoResponseDTO {
    private UUID id;
    private CanalPedido canalPedido;
    private StatusPedido statusPedido;
    private BigDecimal total;
    private LocalDateTime createdAt;

    public static PedidoResponseDTO fromEntity(Pedido pedido) {
        return new PedidoResponseDTO(
                pedido.getId(),
                pedido.getCanalPedido(),
                pedido.getStatus(),
                pedido.getTotal(),
                pedido.getCreatedAt()
        );
    }
}
