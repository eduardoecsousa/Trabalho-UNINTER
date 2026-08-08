package raizes_do_nordeste_api.Controller.dtos;

import lombok.Getter;
import lombok.Setter;
import raizes_do_nordeste_api.enums.CanalPedido;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class PedidoRequestDTO {
    private UUID unidadeId;
    private CanalPedido canalPedido;
    private String formaPagamento;
    private List<ItemPedidoDTO> itens;

    @Getter
    @Setter
    public static class ItemPedidoDTO {
        private UUID produtoId;
        private Integer quantidade;
    }
}
