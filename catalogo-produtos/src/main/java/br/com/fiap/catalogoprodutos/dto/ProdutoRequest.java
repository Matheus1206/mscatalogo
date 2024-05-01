package br.com.fiap.catalogoprodutos.dto;

import br.com.fiap.catalogoprodutos.model.Produto;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ProdutoRequest(@NotBlank String nome, @NotBlank String descricao, @NotNull @Min(1) Float preco, @NotBlank String categoria, @NotBlank String imagem, @NotNull @Min(1) Integer quantidadeEstoque) {

    public Produto toModel() {
        return new Produto(nome, descricao, preco, categoria, imagem, quantidadeEstoque);
    }
}
