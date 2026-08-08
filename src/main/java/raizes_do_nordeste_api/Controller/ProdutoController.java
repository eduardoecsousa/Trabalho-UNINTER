package raizes_do_nordeste_api.Controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import raizes_do_nordeste_api.Entity.models.Produto;
import raizes_do_nordeste_api.Service.ProdutoService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/produtos")
@RequiredArgsConstructor
public class ProdutoController {
    private final ProdutoService produtoService;

    @GetMapping
    public ResponseEntity<List<Produto>> getAllCategoria(@RequestParam(required = false) String categoria) {
        if (categoria != null) {
            return ResponseEntity.ok(produtoService.findByCategoria(categoria));
        }
        return ResponseEntity.ok(produtoService.findAllActive());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Produto> getForId(@PathVariable UUID id){
        return ResponseEntity.ok(produtoService.findById(id));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Produto> register(@RequestParam Produto produto) {
        return ResponseEntity.status(201).body(
                produtoService.insert(produto)
        );
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Produto> update(@PathVariable UUID id, @RequestBody Produto produto){
        return ResponseEntity.ok(produtoService.update(id, produto));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> disable(@PathVariable UUID id){
        produtoService.disableProduto(id);
        return ResponseEntity.noContent().build();
    }

}
