package br.com.fiap.catalogoprodutos.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Getter
@Setter
public class Produto {
    @Id
    private String id;
    private String nome;
    private String descricao;
    private Float preco;
    private String categoria;
    private String imagem;
    private Integer quantidadeEstoque;

    public Produto(String nome, String descricao, Float preco, String categoria, String imagem, Integer quantidadeEstoque){
        this.nome = nome;
        this.descricao = descricao;
        this.preco = preco;
        this.categoria = categoria;
        this.imagem = imagem;
        this.quantidadeEstoque = quantidadeEstoque;
    }

    public Produto(){}



}
