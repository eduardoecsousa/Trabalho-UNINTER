package raizes_do_nordeste_api.Entity.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "fidelidades")
@Getter
@Setter
@NoArgsConstructor
public class Fidelidade {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @OneToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    private int pontosAcumulados;

    private int pontosResgatados;

    private LocalDateTime updateAt;

    public Fidelidade(Usuario usuario, int pontosAcumulados, int pontosResgatados) {
        this.usuario = usuario;
        this.pontosAcumulados = pontosAcumulados;
        this.pontosResgatados = pontosResgatados;
    }
}
