package raizes_do_nordeste_api.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import raizes_do_nordeste_api.Entity.models.Usuario;
import raizes_do_nordeste_api.Entity.repository.UsuarioRepository;
import raizes_do_nordeste_api.enums.Role;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public Usuario cadastrar (String nome, String email, String senha, Boolean consentimento) {
        if (usuarioRepository.existsByEmail(email)) {
            throw new RuntimeException("E-mail já cadastrado.");
        }
        Usuario usuario = new Usuario(nome, passwordEncoder.encode(senha), email, Role.CLIENTE, consentimento);
        return usuarioRepository.save(usuario);
    }

    public Usuario buscarEmail(String email) {
        return usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario não encontrado"));
    }
}
