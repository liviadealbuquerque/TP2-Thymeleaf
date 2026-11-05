package br.edu.app;
import java.util.List; import java.util.Locale; import java.math.BigDecimal;
import org.springframework.stereotype.Service; import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;
@Service
public class ProdutoService {
    public static final List<String> CATEGORIAS=List.of("Eletrônico","Vestuário","Alimento","Outros");
    private final ProdutoRepository repository;
    public ProdutoService(ProdutoRepository repository){this.repository=repository;}
    public List<Produto> listar(String busca,String categoria){
        String termo=busca==null?"":busca.trim().toLowerCase(Locale.ROOT);
        return repository.findAll().stream().filter(p->p.getNome().toLowerCase(Locale.ROOT).contains(termo))
            .filter(p->categoria==null||categoria.isBlank()||p.getCategoria().equals(categoria)).toList();
    }
    public Produto criar(String nome,String categoria,BigDecimal preco,Integer estoque){
        if(nome==null||nome.isBlank()||!CATEGORIAS.contains(categoria)||preco==null||preco.signum()<0||preco.compareTo(new BigDecimal("9999999999.99"))>0||preco.stripTrailingZeros().scale()>2||estoque==null||estoque<0)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Preencha o nome, escolha a categoria e informe preço e estoque não negativos. Preço: até duas casas decimais.");
        Produto p=new Produto();p.setNome(nome.trim());p.setCategoria(categoria);p.setPreco(preco);p.setEstoque(estoque);
        return repository.saveAndFlush(p);
    }
    public void remover(Long id){if(!repository.existsById(id))throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Produto não encontrado");repository.deleteById(id);}
}
