package raizes_do_nordeste_api.Controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import raizes_do_nordeste_api.Controller.dtos.AuthResponseDTO;
import raizes_do_nordeste_api.Controller.dtos.LoginRequestDTO;
import raizes_do_nordeste_api.Controller.dtos.RegisterRequestDTO;
import raizes_do_nordeste_api.Entity.models.Usuario;
import raizes_do_nordeste_api.Service.FidelidadeService;
import raizes_do_nordeste_api.Service.UsuarioService;
import raizes_do_nordeste_api.security.JwtUtil;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UsuarioService usuarioService;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;
    private final FidelidadeService fidelidadeService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponseDTO> register(@RequestBody RegisterRequestDTO dto) {
        Usuario usuario = usuarioService.insert(
                dto.getNome(),
                dto.getEmail(),
                dto.getSenha(),
                dto.isConsentimentoLgpd()
        );

        fidelidadeService.insert(usuario);

        String token = jwtUtil.generateToken(usuario);
        return ResponseEntity.status(201).body(
                new AuthResponseDTO(token,
                usuario.getNome(),
                usuario.getRole().name())
        );
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody LoginRequestDTO dto){
        Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getSenha()));

        Usuario usuario = (Usuario) auth.getPrincipal();
        assert usuario != null;
        String token = jwtUtil.generateToken(usuario);

        return ResponseEntity.ok(
                new AuthResponseDTO(
                        token,
                        usuario.getNome(),
                        usuario.getRole().name()
                )
        );
    }
}
