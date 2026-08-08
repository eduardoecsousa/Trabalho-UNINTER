package raizes_do_nordeste_api.Controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import raizes_do_nordeste_api.Entity.models.Unidade;
import raizes_do_nordeste_api.Service.UnidadeService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/unit")
@RequiredArgsConstructor
public class UnidadeController {
    private final UnidadeService unidadeService;

    @GetMapping
    public ResponseEntity<List<Unidade>> getAllActive() {
        return ResponseEntity.ok(unidadeService.listActive());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Unidade> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(unidadeService.findById(id));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Unidade> register(@RequestBody Unidade unidade){
        return ResponseEntity.status(201).body(
                unidadeService.insert(unidade)
        );
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Unidade> update(@PathVariable UUID id, @RequestBody Unidade unidade) {
        return ResponseEntity.ok(unidadeService.update(unidade, id));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> disable(@PathVariable UUID id) {
        unidadeService.disableUnidade(id);
        return ResponseEntity.noContent().build();
    }
}
