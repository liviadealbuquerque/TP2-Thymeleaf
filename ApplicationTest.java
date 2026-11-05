package br.edu.app;
import org.junit.jupiter.api.*;import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;import static org.hamcrest.Matchers.*;
@SpringBootTest(properties={"spring.datasource.url=jdbc:h2:mem:produtos;DB_CLOSE_DELAY=-1","spring.jpa.hibernate.ddl-auto=create-drop"}) @AutoConfigureMockMvc
class ApplicationTest {
@Autowired MockMvc mvc; @Autowired ProdutoRepository repository;
@BeforeEach void limpar(){repository.deleteAll();}
@Test void fluxoHtml() throws Exception {
mvc.perform(get("/produtos/novo")).andExpect(status().isOk()).andExpect(content().string(containsString("Novo produto")));
mvc.perform(post("/produtos").param("nome","Mouse").param("categoria","Eletrônico").param("preco","29.90").param("estoque","3")).andExpect(status().is3xxRedirection());
mvc.perform(get("/produtos")).andExpect(status().isOk()).andExpect(content().string(containsString("Mouse")));
mvc.perform(get("/produtos").param("busca","MOUSE")).andExpect(content().string(containsString("Mouse")));
mvc.perform(get("/produtos").param("categoria","Outros")).andExpect(content().string(containsString("Nenhum produto encontrado")));
long id=repository.findAll().get(0).getId();mvc.perform(post("/produtos/"+id+"/remover")).andExpect(status().is3xxRedirection());Assertions.assertEquals(0,repository.count());
}
@Test void invalidoNaoPersiste() throws Exception {
mvc.perform(post("/produtos").param("nome","Mouse").param("categoria","Eletrônico").param("preco","-1").param("estoque","3")).andExpect(status().isOk()).andExpect(content().string(containsString("não negativos")));
mvc.perform(post("/produtos").param("nome","Mouse").param("categoria","Eletrônico").param("preco","abc").param("estoque","3")).andExpect(status().isOk()).andExpect(content().string(containsString("números válidos")));Assertions.assertEquals(0,repository.count());
}
@Test void api() throws Exception {
mvc.perform(post("/api/produtos").contentType("application/json").content("{\"nome\":\"Livro\",\"categoria\":\"Outros\",\"preco\":10,\"estoque\":2}")).andExpect(status().isCreated());
mvc.perform(get("/api/produtos")).andExpect(status().isOk()).andExpect(jsonPath("$[0].nome").value("Livro"));
mvc.perform(delete("/api/produtos/"+repository.findAll().get(0).getId())).andExpect(status().isNoContent());
}
}
