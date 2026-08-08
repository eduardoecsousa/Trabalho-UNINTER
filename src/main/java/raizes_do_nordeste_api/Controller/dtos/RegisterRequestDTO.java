package raizes_do_nordeste_api.Controller.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterRequestDTO {
    private String nome;
    private String email;
    private String senha;
    private boolean consentimentoLgpd;
}
