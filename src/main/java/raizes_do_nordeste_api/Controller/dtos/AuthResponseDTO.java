package raizes_do_nordeste_api.Controller.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AuthResponseDTO {
    private String accessToken;
    private String tokenType = "Bearer";
    private String nome;
    private String role;

    public AuthResponseDTO(String accessToken, String nome, String role) {
        this.accessToken = accessToken;
        this.nome = nome;
        this.role = role;
    }
}
