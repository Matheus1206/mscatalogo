package br.com.fiap.catalogoprodutos.services;

import br.com.fiap.catalogoprodutos.dto.ProdutoRequest;
import br.com.fiap.catalogoprodutos.dto.ProdutoResponse;
import br.com.fiap.catalogoprodutos.model.Produto;
import br.com.fiap.catalogoprodutos.repository.ProdutoRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProdutoService {


    private final ProdutoRepository produtoRepository;


    public ProdutoService(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    @Transactional
    public ResponseEntity<ProdutoResponse> cadastrarProduto(ProdutoRequest produtoRequest) {
        Produto produto = produtoRepository.save(produtoRequest.toModel());
        return ResponseEntity.created(null).body(new ProdutoResponse(produto));
    }
    @Transactional
    public ResponseEntity<ProdutoResponse> atualizarProduto(String id, ProdutoRequest produtoRequest) {
        Produto produto = produtoRepository.findById(id).orElseThrow();
        produto.setNome(produtoRequest.nome());
        produto.setDescricao(produtoRequest.descricao());
        produto.setCategoria(produtoRequest.categoria());
        produto.setPreco(produtoRequest.preco());
        produto.setImagem(produtoRequest.imagem());
        produto.setQuantidadeEstoque(produtoRequest.quantidadeEstoque());
        Produto save = produtoRepository.save(produto);
        return ResponseEntity.ok(new ProdutoResponse(save));
    }
    @Transactional
    public ResponseEntity<?> deletarProduto(String id) {
        Produto produto = produtoRepository.findById(id).orElseThrow();
        produtoRepository.delete(produto);
        return ResponseEntity.noContent().build();
    }
    @Transactional
    public ResponseEntity<ProdutoResponse> consultarProduto(String id) {
        return ResponseEntity.ok().body(new ProdutoResponse(produtoRepository.findById(id).orElseThrow()));
    }
    @Transactional
    public ResponseEntity<Integer> consultarEstoqueDoProduto(String id) {
        return ResponseEntity.ok().body(produtoRepository.findById(id).orElseThrow().getQuantidadeEstoque());
    }
    @Transactional
    public ResponseEntity<?> verificaDisponibilidadeDoProdutoNoEstoque(String id, Integer quantidade) {
        Produto produto = produtoRepository.findById(id).get();
        if(produto.getQuantidadeEstoque() < quantidade){
            return ResponseEntity.badRequest().body("Quantidade do produto " + produto.getId() + " : " + produto.getNome() + " indisponível");
        } else {
            produto.setQuantidadeEstoque(produto.getQuantidadeEstoque() - quantidade);
            produtoRepository.save(produto);
            return ResponseEntity.ok().body(true);
        }
    }
}
