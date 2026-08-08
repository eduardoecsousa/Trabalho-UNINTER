package raizes_do_nordeste_api.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import raizes_do_nordeste_api.Entity.models.Fidelidade;
import raizes_do_nordeste_api.Entity.models.Usuario;
import raizes_do_nordeste_api.Entity.repository.FidelidadeRepository;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FidelidadeService {
    private final FidelidadeRepository fidelidadeRepository;

    public Fidelidade findByIdUsuario(UUID id) {
        return fidelidadeRepository.findByUsuarioId(id)
                .orElseThrow(() -> new RuntimeException("Usuario não encontrado!"));
    }

    public Fidelidade insert(Usuario usuario) {
        Fidelidade fidelidade = new Fidelidade(usuario);
        return fidelidadeRepository.save(fidelidade);
    }

    public Fidelidade accumulatePoints(UUID usuarioId, Integer pontos) {
        Fidelidade fidelidade = findByIdUsuario(usuarioId);
        fidelidade.setPontosAcumulados(fidelidade.getPontosAcumulados() + pontos);
        return fidelidadeRepository.save(fidelidade);
    }

    public boolean redeemPoints(UUID usuarioId, Integer pontos) {
        Fidelidade fidelidade = findByIdUsuario(usuarioId);
        Integer saldo = consultPoints(usuarioId);
        if (saldo < pontos) {
            return false;
        }
        fidelidade.setPontosResgatados(fidelidade.getPontosResgatados() + pontos);
        fidelidadeRepository.save(fidelidade);
        return true;
    }

    public Integer consultPoints(UUID usuarioId) {
        Fidelidade fidelidade = findByIdUsuario(usuarioId);
        Integer pontos = fidelidade.getPontosAcumulados() - fidelidade.getPontosResgatados();
        return pontos;
    }
}
