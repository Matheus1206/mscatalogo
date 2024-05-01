package br.com.fiap.catalogoprodutos.dto;

import br.com.fiap.catalogoprodutos.model.Produto;

public record ProdutoResponse(String id, String nome, String descricao, Float preco, String categoria, String imagem, Integer quantidadeEstoque) {

    public ProdutoResponse(Produto produto) {
        this(produto.getId(), produto.getNome(), produto.getDescricao(), produto.getPreco(), produto.getCategoria(), produto.getImagem(), produto.getQuantidadeEstoque());
    }
}
