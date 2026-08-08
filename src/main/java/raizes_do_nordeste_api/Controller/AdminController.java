package raizes_do_nordeste_api.Controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import raizes_do_nordeste_api.Entity.models.Pedido;
import raizes_do_nordeste_api.Service.PedidoService;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {
    private final PedidoService pedidoService;

}
