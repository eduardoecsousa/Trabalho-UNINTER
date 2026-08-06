package raizes_do_nordeste_api.Entity.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import raizes_do_nordeste_api.enums.StatusPagamento;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "pagamentos")
@Getter
@Setter
@NoArgsConstructor
public class Pagamento {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @OneToOne
    @JoinColumn(name = "pedido_id", nullable = false)
    private Pedido pedido;

    @Column(nullable = false)
    private StatusPagamento statusPagamento;

    private BigDecimal valor;

    private LocalDateTime createdAt;

    public Pagamento(Pedido pedido, String paymentMethod, BigDecimal total) {
    }

    @PrePersist
    protected void onCreated(){
        this.createdAt = LocalDateTime.now();
    }

    public Pagamento(StatusPagamento statusPagamento, Pedido pedido, LocalDateTime createdAt, BigDecimal valor) {
        this.statusPagamento = statusPagamento;
        this.pedido = pedido;
        this.createdAt = createdAt;
        this.valor = valor;
    }
}
