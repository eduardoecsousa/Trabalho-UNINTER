package raizes_do_nordeste_api.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import raizes_do_nordeste_api.Entity.models.Unidade;
import raizes_do_nordeste_api.Entity.repository.UnidadeRepository;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UnidadeService {
    private final UnidadeRepository unidadeRepository;

    public Unidade insert(Unidade unidade){
        return unidadeRepository.save(unidade);
    }

    public List<Unidade> listActive(){
        return unidadeRepository.findByAtivoTrue();
    }

    public Unidade findById(UUID id) {
        return unidadeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Unidade não encontrada"));
    }

    public Unidade update(Unidade unidadeUpdate, UUID id) {
        Unidade unidade = findById(id);
        unidade.setNome(unidadeUpdate.getNome());
        unidade.setTelefone(unidadeUpdate.getTelefone());
        unidade.setEndereco(unidade.getEndereco());
        return unidadeRepository.save(unidade);
    }

    public void disableUnidade(UUID id) {
        Unidade unidade = findById(id);
        unidade.setAtivo(false);
        unidadeRepository.save(unidade);
    }
}
