package raizes_do_nordeste_api.Entity.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "produtos")
@Getter
@Setter
@NoArgsConstructor
public class Produto {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;

    @Column(nullable = false)
    private String nome;

    private String descricao;

    @Column(nullable = false)
    private BigDecimal preco;

    private String categoria;

    private Boolean ativo;

    public Produto(String nome, String descricao, BigDecimal preco, String categoria, Boolean ativo) {
        this.nome = nome;
        this.descricao = descricao;
        this.preco = preco;
        this.categoria = categoria;
        this.ativo = true;
    }

    public boolean isDisponivel() {
        return Boolean.TRUE.equals(this.ativo);
    }

}
