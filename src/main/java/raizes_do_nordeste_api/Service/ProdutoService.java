package raizes_do_nordeste_api.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import raizes_do_nordeste_api.Entity.models.Produto;
import raizes_do_nordeste_api.Entity.repository.ProdutoRepository;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProdutoService {

    private final ProdutoRepository produtoRepository;

    public Produto insert(Produto produto) {
        return produtoRepository.save(produto);
    }

    public List<Produto> findAllActive(){
        return produtoRepository.findByAtivoTrue();
    }

    public List<Produto> findByCategoria(String categoria) {
        return produtoRepository.findByCategoria(categoria);
    }

    public Produto findById (UUID id) {
        return produtoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));
    }

    public Produto update (UUID id, Produto produtoUpdate) {
        Produto produto = findById(id);
        produto.setNome(produtoUpdate.getNome());
        produto.setDescricao(produtoUpdate.getDescricao());
        produto.setPreco(produtoUpdate.getPreco());
        produto.setCategoria(produtoUpdate.getCategoria());
        return produtoRepository.save(produto);
    }

    public void disableProduto(UUID idProduto){
        Produto produto = findById(idProduto);
        produto.setAtivo(false);
        produtoRepository.save(produto);
    }

}
