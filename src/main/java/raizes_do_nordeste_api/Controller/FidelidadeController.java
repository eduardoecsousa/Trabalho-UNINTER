package raizes_do_nordeste_api.Controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import raizes_do_nordeste_api.Entity.models.Fidelidade;
import raizes_do_nordeste_api.Entity.models.Usuario;
import raizes_do_nordeste_api.Service.FidelidadeService;

@RestController
@RequestMapping("/fidelity")
@RequiredArgsConstructor
public class FidelidadeController {
    private final FidelidadeService fidelidadeService;

    @GetMapping("/my-points")
    public ResponseEntity<Integer> getSaldo(@AuthenticationPrincipal Usuario usuario) {
        return ResponseEntity.ok(fidelidadeService.consultPoints(usuario.getId()));
    }

    @GetMapping("/my-historic")
    public ResponseEntity<Fidelidade> consultHistoric(@AuthenticationPrincipal Usuario usuario) {
        return ResponseEntity.ok(fidelidadeService.findByIdUsuario(usuario.getId()));
    }

    @PostMapping("/rescue")
    public ResponseEntity<String> rescuePoints(@AuthenticationPrincipal Usuario usuario, @RequestParam Integer pontos) {
        boolean resgatdado = fidelidadeService.redeemPoints(usuario.getId(), pontos);

        if(resgatdado){
            return ResponseEntity.ok("Resgate realizado! " + pontos + " pontos.");
        }

        return ResponseEntity.badRequest().body("Saldo insuficiente para resgatar " + pontos + " pontos.");
    }
}
