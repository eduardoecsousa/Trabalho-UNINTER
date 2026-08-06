package raizes_do_nordeste_api.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import raizes_do_nordeste_api.Entity.models.Pagamento;
import raizes_do_nordeste_api.Entity.models.Pedido;
import raizes_do_nordeste_api.Entity.repository.PagamentoRepository;
import raizes_do_nordeste_api.enums.StatusPagamento;

import java.util.Random;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PagamentoService {
    private final PagamentoRepository pagamentoRepository;

    public Pagamento mockPaymente(Pedido pedido, String paymentMethod) {
        boolean aprovado = new Random().nextInt(10) < 8;

        Pagamento pagamento = new Pagamento(
                pedido,
                paymentMethod,
                pedido.getTotal()
        );

        if(aprovado){
            pagamento.setStatusPagamento(StatusPagamento.APROVADO);
        } else {
            pagamento.setStatusPagamento(StatusPagamento.RECUSADO);
        }

        return pagamentoRepository.save(pagamento);
    }

    public Pagamento findByIdPedido(UUID idPedido){
        return pagamentoRepository.findByPedidoId(idPedido)
                .orElseThrow(() -> new RuntimeException("Pagamento não encontrado"));
    }
}
