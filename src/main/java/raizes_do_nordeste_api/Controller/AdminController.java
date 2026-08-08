package raizes_do_nordeste_api.Controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import raizes_do_nordeste_api.Controller.dtos.PedidoResponseDTO;
import raizes_do_nordeste_api.Entity.models.Estoque;
import raizes_do_nordeste_api.Entity.models.Pedido;
import raizes_do_nordeste_api.Entity.models.Produto;
import raizes_do_nordeste_api.Entity.models.Unidade;
import raizes_do_nordeste_api.Service.EstoqueService;
import raizes_do_nordeste_api.Service.PedidoService;
import raizes_do_nordeste_api.Service.ProdutoService;
import raizes_do_nordeste_api.Service.UnidadeService;
import raizes_do_nordeste_api.enums.CanalPedido;
import raizes_do_nordeste_api.enums.StatusPedido;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {
    private final PedidoService pedidoService;
    private final EstoqueService estoqueService;
    private final UnidadeService unidadeService;
    private final ProdutoService produtoService;

    @GetMapping("/pedidos")
    public ResponseEntity<List<PedidoResponseDTO>> getAllPedidos(@RequestParam(required = false) CanalPedido canalPedido,
                                                                 @RequestParam(required = false)StatusPedido statusPedido){
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
            pedidos = pedidoService.findAll()
                    .stream()
                    .map(PedidoResponseDTO::fromEntity)
                    .collect(Collectors.toList());
        }
        return ResponseEntity.ok(pedidos);
    }

    @PostMapping("/estoque")
    public ResponseEntity<Estoque> registerEstoque(@RequestParam UUID unidadeId,
                                                   @RequestParam UUID produtoId,
                                                   @RequestParam Integer quantidade) {
        Unidade unidade = unidadeService.findById(unidadeId);
        Produto produto = produtoService.findById(produtoId);

        return ResponseEntity.status(201).body(estoqueService.insert(unidade, produto, quantidade));
    }

    @PatchMapping("/estoque/aumentar")
    public ResponseEntity<String> increaseStock(@RequestParam UUID unidadeId,
                                                @RequestParam UUID produtoId,
                                                @RequestParam Integer quantidade) {
        estoqueService.increase(unidadeId, produtoId, quantidade);
        return ResponseEntity.ok("Estoque aumentado com sucesso!");
    }

    @GetMapping("/estoque")
    public ResponseEntity<Integer> consultStock(@RequestParam UUID unidadeId,
                                                @RequestParam UUID produtoId) {
        return ResponseEntity.ok(
                estoqueService.consultQuantidade(unidadeId, produtoId)
        );
    }
}
