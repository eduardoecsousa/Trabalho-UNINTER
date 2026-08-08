package raizes_do_nordeste_api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String, Object>> handleRuntimeException(
            RuntimeException ex) {

        Map<String, Object> erro = new HashMap<>();
        erro.put("timestamp", LocalDateTime.now());
        erro.put("mensagem", ex.getMessage());

        // Define o status baseado na mensagem
        if (ex.getMessage().contains("não encontrado") ||
                ex.getMessage().contains("não encontrada")) {
            erro.put("status", 404);
            erro.put("erro", "Não encontrado");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(erro);
        }

        if (ex.getMessage().contains("insuficiente") ||
                ex.getMessage().contains("não pode ser cancelado")) {
            erro.put("status", 409);
            erro.put("erro", "Conflito de regra de negócio");
            return ResponseEntity.status(HttpStatus.CONFLICT).body(erro);
        }

        if (ex.getMessage().contains("já cadastrado")) {
            erro.put("status", 409);
            erro.put("erro", "Conflito");
            return ResponseEntity.status(HttpStatus.CONFLICT).body(erro);
        }

        erro.put("status", 400);
        erro.put("erro", "Erro na requisição");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
    }
}