package raizes_do_nordeste_api.Entity.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "unidades")
@Getter
@Setter
@NoArgsConstructor
public class Unidade {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String endereco;

    @Column(nullable = false)
    private String telefone;

    @Column(nullable = false)
    private Boolean ativo;

    public Unidade(String nome, String endereco, Boolean ativo, String telefone) {
        this.nome = nome;
        this.endereco = endereco;
        this.ativo = true;
        this.telefone = telefone;
    }
}
