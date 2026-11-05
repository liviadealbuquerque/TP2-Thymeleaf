# 08 — Cadastro de produtos com Thymeleaf

Aplicação Spring Boot MVC com Thymeleaf, serviço separado, Spring Data JPA e H2.
JDK 17+ e Maven 3.6.3+: `mvn test`, depois `mvn spring-boot:run`.
Abra `http://localhost:8080/produtos`. O H2 em arquivo (`data/`) preserva os produtos
entre reinícios. A aplicação de demonstração escuta somente o computador local.

Os fontes estão na raiz e o pom configura explicitamente a compilação. Os HTMLs
são copiados para `classpath:templates` durante o build. Não há lista estática:
ProdutoService concentra as regras e ProdutoRepository persiste os registros.

## Exercícios do final do PDF
10. Atributo adicional **estoque**, no cadastro e na tabela.
11. CSS com tipografia, cores, bordas e formulários.
12. Total de produtos abaixo da tabela (quantidade exibida após filtros).
13. Select de categoria: Eletrônico, Vestuário, Alimento e Outros.
14. Preço negativo é recusado no servidor e exibido como mensagem no formulário.
15. Ação Remover por POST com o ID do produto.
16. Classe @Service separada do controller.
17. Busca sem diferenciar maiúsculas/minúsculas por nome e filtro por categoria.
18. Spring Data JPA com H2 embarcado em arquivo.

## Como a página funciona e trafega dados
GET /produtos chama o controller, que consulta o serviço e coloca a lista em Model.
O Thymeleaf processa `th:each`, `th:text` e `th:if` no servidor, gerando o HTML
entregue ao navegador. Os campos vinculados por `th:object` e `th:field` produzem
um formulário com os nomes de propriedades esperados pelo Spring.
O navegador envia POST /produtos como formulário; o controller vincula os valores,
valida pelo serviço, grava via JPA e redireciona para a listagem. Entrada inválida
volta ao formulário com mensagem e valores preenchidos. `th:action` e `th:href`
geram as URLs. A exclusão também usa POST e depois redireciona.

O fluxo MVC não precisa fazer uma chamada HTTP para a própria API JSON: HTML e API
compartilham o mesmo ProdutoService. Para clientes externos, existem GET e POST
/api/produtos e DELETE /api/produtos/{id}. GET aceita `busca` e `categoria`.
POST recebe JSON como abaixo e responde 201; inválidos respondem 400:
```json
{"nome":"Mouse","categoria":"Eletrônico","preco":29.90,"estoque":3}
```
Ao inserir pela API, o produto aparece na próxima consulta da página e vice-versa,
pois ambos usam o mesmo banco. Não há JavaScript obrigatório para cadastrar.

## Verificação
Os testes verificam renderização dos templates, cadastro, busca, filtro, exclusão,
rejeição de preço negativo/não numérico e integração da API JSON com o repositório.
Para conferir persistência manualmente, cadastre um produto, reinicie o programa
sem apagar `data/` e volte à listagem.
