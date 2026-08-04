package raizes_do_nordeste_api.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import raizes_do_nordeste_api.Entity.models.Estoque;
import raizes_do_nordeste_api.Entity.models.Produto;
import raizes_do_nordeste_api.Entity.models.Unidade;
import raizes_do_nordeste_api.Entity.repository.EstoqueRepository;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EstoqueService {
    private final EstoqueRepository estoqueRepository;

    public Estoque findByUnidadeAndProduto(UUID unidadeId, UUID produtoId) {
        return estoqueRepository.findByUnidadeIdAndProdutoId(unidadeId, produtoId)
                .orElseThrow(() -> new RuntimeException("Estoque não encontrado na unidade desejada."));
    }

    public void validateEstoque(UUID unidadeId, UUID produtoId, Integer quantidade) {
        Estoque estoque = findByUnidadeAndProduto(unidadeId, produtoId);
        boolean haveStock = estoque.getQuantidade() >= quantidade;
        if(!haveStock){
            throw new RuntimeException("Estoque insuficiente! Disponivel: " + estoque.getQuantidade());
        }
    }

    public void decrease(UUID unidadeId, UUID produtoId, Integer quantidade) {
        Estoque estoque = findByUnidadeAndProduto(unidadeId, produtoId);
        validateEstoque(unidadeId, produtoId, quantidade);
        estoque.setQuantidade(estoque.getQuantidade() - quantidade);
        estoqueRepository.save(estoque);
    }

    public void increase(UUID unidadeId, UUID produtoId, Integer quantidade) {
        Estoque estoque = findByUnidadeAndProduto(unidadeId, produtoId);
        estoque.setQuantidade(estoque.getQuantidade() + quantidade);
        estoqueRepository.save(estoque);
    }

    public Estoque insert(Unidade unidade, Produto produto, Integer quantidade) {
        Estoque estoque = new Estoque(unidade, produto, quantidade);
        return estoqueRepository.save(estoque);
    }

    public Integer consultQuantidade(UUID unidadeId, UUID produtoId) {
        Estoque estoque = findByUnidadeAndProduto(unidadeId, produtoId);
        return estoque.getQuantidade();
    }
}
