package br.edu.app;
import java.math.BigDecimal;
import org.springframework.stereotype.Controller; import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*; import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
@Controller
public class ProdutoController {
    private final ProdutoService service;
    public ProdutoController(ProdutoService service){this.service=service;}
    @ModelAttribute("categorias") Object categorias(){return ProdutoService.CATEGORIAS;}
    @GetMapping("/") String inicio(){return "redirect:/produtos";}
    @GetMapping("/produtos") String listar(@RequestParam(defaultValue="") String busca,@RequestParam(defaultValue="") String categoria,Model model){
        model.addAttribute("produtos",service.listar(busca,categoria));model.addAttribute("busca",busca);model.addAttribute("categoria",categoria);return "lista";
    }
    @GetMapping("/produtos/novo") String novo(Model model){model.addAttribute("produto",new Formulario());return "cadastrar";}
    @PostMapping("/produtos") String criar(@ModelAttribute("produto") Formulario form,org.springframework.validation.BindingResult erros,Model model,RedirectAttributes redirect){
        if(erros.hasErrors()){model.addAttribute("erro","Preço e estoque devem ser números válidos.");return "cadastrar";}
        try{service.criar(form.getNome(),form.getCategoria(),form.getPreco(),form.getEstoque());}
        catch(ResponseStatusException e){model.addAttribute("erro",e.getReason());return "cadastrar";}
        redirect.addFlashAttribute("mensagem","Produto cadastrado.");return "redirect:/produtos";
    }
    @PostMapping("/produtos/{id}/remover") String remover(@PathVariable Long id,RedirectAttributes redirect){service.remover(id);redirect.addFlashAttribute("mensagem","Produto removido.");return "redirect:/produtos";}
    public static class Formulario {
        private String nome,categoria; private BigDecimal preco;private Integer estoque;
        public String getNome(){return nome;}public void setNome(String v){nome=v;}
        public String getCategoria(){return categoria;}public void setCategoria(String v){categoria=v;}
        public BigDecimal getPreco(){return preco;}public void setPreco(BigDecimal v){preco=v;}
        public Integer getEstoque(){return estoque;}public void setEstoque(Integer v){estoque=v;}
    }
}
