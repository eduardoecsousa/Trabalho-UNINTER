package raizes_do_nordeste_api.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import raizes_do_nordeste_api.Entity.models.*;
import raizes_do_nordeste_api.Entity.repository.PedidoRepository;
import raizes_do_nordeste_api.enums.CanalPedido;
import raizes_do_nordeste_api.enums.StatusPedido;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PedidoService {
    private final PedidoRepository pedidoRepository;
    private final EstoqueService estoqueService;
    private final PagamentoService pagamentoService;
    private final FidelidadeService fidelidadeService;
    private final ProdutoService produtoService;
    private final UnidadeService unidadeService;

    public Pedido insert(UUID usuarioId, UUID unidadeId,
                         CanalPedido canalPedido,
                         List<ItemPedidoRequest> itens,
                         String formaDePagamento,
                         Usuario usuario) {
        Unidade unidade = unidadeService.findById(unidadeId);

        for(ItemPedidoRequest item: itens) {
            estoqueService.validateEstoque(
                    unidadeId, item.getProdutoId(), item.getQuantidade()
            );
        }

        Pedido pedido = new Pedido();
        pedido.setUsuario(usuario);
        pedido.setUnidade(unidade);
        pedido.setCanalPedido(canalPedido);
        pedido.setStatus(StatusPedido.PENDENTE);

        for(ItemPedidoRequest item : itens) {
            Produto produto = produtoService.findById(item.getProdutoId());
            ItemPedido itemPedido = new ItemPedido(
                    produto, pedido,
                    item.getQuantidade(),
                    produto.getPreco()
            );
              pedido.getItens().add(itemPedido);
        }

        pedido.calcularTotal();

        Pedido pedidoSalvo = pedidoRepository.save(pedido);

        var pagamento = pagamentoService.mockPaymente(
                pedidoSalvo, formaDePagamento
        );

        if(pagamento.getStatusPagamento().name().equals("APROVADO")){
            pedidoSalvo.setStatus(StatusPedido.EM_PREPARO);

            for(ItemPedidoRequest item : itens) {
                estoqueService.decrease(unidadeId, item.getProdutoId(), item.getQuantidade());
            }
            int pontos = pedidoSalvo.getTotal().intValue();
            fidelidadeService.accumulatePoints(usuario.getId(), pontos);
        } else {
            pedidoSalvo.setStatus(StatusPedido.AGUARDANDO_PAGAMENTO);
        }

        return pedidoRepository.save(pedidoSalvo);
    }
    public List<Pedido> findByIdUsuario(UUID usuarioId) {
        return pedidoRepository.findByUsuarioId(usuarioId);
    }

    public List<Pedido> findByIdCanal(CanalPedido canalPedido) {
        return pedidoRepository.findByCanalPedido(canalPedido);
    }

    public List<Pedido> findByIdStatus(StatusPedido status) {
        return pedidoRepository.findByStatus(status);
    }

    public Pedido findById(UUID id) {
        return pedidoRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Pedido não encontrado!"));
    }

    public Pedido cancel(UUID pedidoId, UUID unidadeId) {
        Pedido pedido = findById(pedidoId);

        if (!pedido.getStatus().equals(StatusPedido.PENDENTE) &&
                !pedido.getStatus().equals(StatusPedido.AGUARDANDO_PAGAMENTO)) {
            throw new RuntimeException(
                    "Pedido não pode ser cancelado no status: "
                            + pedido.getStatus()
            );
        }

        for (ItemPedido item : pedido.getItens()) {
            estoqueService.increase(
                    unidadeId,
                    item.getProduto().getId(),
                    item.getQuantidade()
            );
        }

        pedido.cancelar();
        return pedidoRepository.save(pedido);
    }

    public Pedido updateStatus(UUID pedidoId, StatusPedido novoStatus) {
        Pedido pedido = findById(pedidoId);
        pedido.setStatus(novoStatus);
        return pedidoRepository.save(pedido);
    }


    public static class ItemPedidoRequest {
        private UUID produtoId;
        private Integer quantidade;

        public UUID getProdutoId() { return produtoId; }
        public Integer getQuantidade() { return quantidade; }
        public void setProdutoId(UUID produtoId) { this.produtoId = produtoId; }
        public void setQuantidade(Integer quantidade) { this.quantidade = quantidade; }
    }
}
