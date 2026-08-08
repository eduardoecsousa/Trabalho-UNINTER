package raizes_do_nordeste_api.Controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import raizes_do_nordeste_api.Controller.dtos.PedidoRequestDTO;
import raizes_do_nordeste_api.Controller.dtos.PedidoResponseDTO;
import raizes_do_nordeste_api.Entity.models.Pedido;
import raizes_do_nordeste_api.Entity.models.Usuario;
import raizes_do_nordeste_api.Service.PedidoService;
import raizes_do_nordeste_api.enums.CanalPedido;
import raizes_do_nordeste_api.enums.StatusPedido;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/pedidos")
@RequiredArgsConstructor
public class PedidoController {
    private final PedidoService pedidoService;

    @PostMapping
    public ResponseEntity<PedidoResponseDTO> create(@RequestBody PedidoRequestDTO dto, @AuthenticationPrincipal Usuario usuario){
        List<PedidoService.ItemPedidoRequest> itens = dto.getItens()
                .stream()
                .map(item -> {
                    PedidoService.ItemPedidoRequest req =
                            new PedidoService.ItemPedidoRequest();
                    req.setProdutoId(item.getProdutoId());
                    req.setQuantidade(item.getQuantidade());
                    return req;
                }).collect(Collectors.toList());

        var pedido = pedidoService.insert(
                usuario.getId(),
                dto.getUnidadeId(),
                dto.getCanalPedido(),
                itens,
                dto.getFormaPagamento(),
                usuario
        );

        return ResponseEntity.status(201)
                .body(PedidoResponseDTO.fromEntity(pedido));
    }

    @GetMapping
    public ResponseEntity<List<PedidoResponseDTO>> getAllPedidosStatusOrCanal(@RequestParam(required = false) CanalPedido canalPedido,
                                                                              @RequestParam(required = false)StatusPedido statusPedido,
                                                                              @AuthenticationPrincipal Usuario usuario){
        List<PedidoResponseDTO> pedidos;

        if (canalPedido != null) {
            pedidos = pedidoService.findByIdCanal(canalPedido)
                    .stream()
                    .map(PedidoResponseDTO::fromEntity)
                    .collect(Collectors.toList());
        } else if (statusPedido != null) {
            pedidos = pedidoService.findByIdStatus(statusPedido)
                    .stream()
                    .map(PedidoResponseDTO::fromEntity)
                    .collect(Collectors.toList());
        } else {
            pedidos = pedidoService.findByIdUsuario(usuario.getId())
                    .stream()
                    .map(PedidoResponseDTO::fromEntity)
                    .collect(Collectors.toList());
        }
        return ResponseEntity.ok(pedidos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PedidoResponseDTO> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(
                PedidoResponseDTO.fromEntity(pedidoService.findById(id))
        );
    }

    @PatchMapping("/{id}/cancelar")
    public ResponseEntity<PedidoResponseDTO> cancel(@PathVariable UUID id, @AuthenticationPrincipal Usuario usuario){
        var pedido = pedidoService.cancel(id, pedidoService.findById(id).getUnidade().getId());

        return ResponseEntity.ok(PedidoResponseDTO.fromEntity(pedido));
    }

    @PatchMapping("/{id}/atualizar-status")
    @PreAuthorize("hasAnyRole('ADMIN', 'ATENDENTE')")
    public ResponseEntity<PedidoResponseDTO> updateStatus(@PathVariable UUID id, @RequestParam StatusPedido newStatus){
        var pedido = pedidoService.updateStatus(id, newStatus);
        return ResponseEntity.ok(PedidoResponseDTO.fromEntity(pedido));
    }
}
