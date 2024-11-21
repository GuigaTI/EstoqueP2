package fatec.br.edu.EstoqueApp.models;
import fatec.br.edu.EstoqueApp.repositories.LojaRepository;
import fatec.br.edu.EstoqueApp.repositories.ProdutoRepository;
import fatec.br.edu.EstoqueApp.services.EstoqueService;
import jakarta.persistence.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

@Entity
@Table(name = "loja")
public class Loja {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome")
    private String nome;

//    @ManyToMany
//    @JoinTable(
//            name = "loja_produto",
//            joinColumns = @JoinColumn(name = "loja_id"),
//            inverseJoinColumns = @JoinColumn(name = "produto_id")
//    )
@ManyToMany(mappedBy = "lojas")
private List<Produto> produtos;

    public Loja(){

    }

    public Loja(String nome) {
        this.nome = nome;
    }


    // Construtores, getters e setters

    public List<Produto> getProdutos() {
        return produtos;
    }

    public void setProdutos(List<Produto> produtos) {
        this.produtos = produtos;
    }

    public void setNome(String lojaPrincipal) {
        this.nome = lojaPrincipal;
    }


    public double calcularValorTotalEstoque() {
        // Carrega a coleção de produtos
        if (produtos != null && !produtos.isEmpty()) {
            produtos.size();  // Força o carregamento da coleção
        }

        return produtos.stream()
                .mapToDouble(Produto::calcularValorEstoque)
                .sum();
    }
    public void exibirProdutos() {
        produtos.forEach(Produto::exibirInformacoes);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }
}

