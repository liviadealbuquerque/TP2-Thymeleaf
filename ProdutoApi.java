package br.edu.app;
import java.math.BigDecimal; import java.net.URI;import java.util.List;
import org.springframework.http.ResponseEntity;import org.springframework.web.bind.annotation.*;
@RestController @RequestMapping("/api/produtos")
public class ProdutoApi {
    private final ProdutoService service;
    public ProdutoApi(ProdutoService service){this.service=service;}
    public record Dados(String nome,String categoria,BigDecimal preco,Integer estoque){}
    @GetMapping public List<Produto> listar(@RequestParam(defaultValue="") String busca,@RequestParam(defaultValue="") String categoria){return service.listar(busca,categoria);}
    @PostMapping public ResponseEntity<Produto> criar(@RequestBody Dados d){var p=service.criar(d.nome(),d.categoria(),d.preco(),d.estoque());return ResponseEntity.created(URI.create("/api/produtos/"+p.getId())).body(p);}
    @DeleteMapping("/{id}") public ResponseEntity<Void> remover(@PathVariable Long id){service.remover(id);return ResponseEntity.noContent().build();}
}
