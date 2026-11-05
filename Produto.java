package br.edu.app;
import jakarta.persistence.*;
@Entity
public class Produto {
@Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
@Column(nullable=false) private String nome;
@Column(nullable=false) private String categoria;
@Column(nullable=false,precision=12,scale=2) private java.math.BigDecimal preco;
@Column(nullable=false) private Integer estoque;
public Produto() {}
public Long getId(){return id;}
public String getNome(){return nome;} public void setNome(String valor){this.nome=valor;}
public String getCategoria(){return categoria;} public void setCategoria(String valor){this.categoria=valor;}
public java.math.BigDecimal getPreco(){return preco;} public void setPreco(java.math.BigDecimal valor){this.preco=valor;}
public Integer getEstoque(){return estoque;} public void setEstoque(Integer valor){this.estoque=valor;}
}
