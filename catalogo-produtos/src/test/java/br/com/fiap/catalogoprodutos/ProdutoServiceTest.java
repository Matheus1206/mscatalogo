package br.com.fiap.catalogoprodutos;

import br.com.fiap.catalogoprodutos.dto.ProdutoRequest;
import br.com.fiap.catalogoprodutos.model.Produto;
import br.com.fiap.catalogoprodutos.repository.ProdutoRepository;
import br.com.fiap.catalogoprodutos.services.ProdutoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;


class ProdutoServiceTest {

    @InjectMocks
    private ProdutoService produtoService;

    @Mock
    private ProdutoRepository produtoRepository;

    private ProdutoRequest produtoRequest = new ProdutoRequest("teste", "teste", 3f, "teste", "teste", 3);
    private Produto produto = produtoRequest.toModel();

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void deveCadastrarUmProduto(){
        given(produtoRepository.save(any(Produto.class))).willReturn(produto);
        var produtoResponseResponseEntity = produtoService.cadastrarProduto(produtoRequest);
        assertEquals(produtoResponseResponseEntity.getBody().nome(), "teste");
    }

    @Test
    void deveAtualizarUmProduto(){
        given(produtoRepository.findById(anyString())).willReturn(Optional.ofNullable(produto));
        given(produtoRepository.save(any(Produto.class))).willReturn(produto);
        ProdutoRequest produtoRequest2 = new ProdutoRequest("testeNovo", "testeNovo", 5f, "testeNovo", "testeNovo", 6);
        var produtoResponseResponseEntity = produtoService.atualizarProduto("1", produtoRequest2);
        assertEquals(produtoResponseResponseEntity.getBody().nome(), "testeNovo");
    }

    @Test
    void deveDeletarUmProduto(){
        given(produtoRepository.findById(anyString())).willReturn(Optional.ofNullable(produto));
        var produtoResponseResponseEntity = produtoService.deletarProduto("1");
        assertNotNull(produtoResponseResponseEntity);
    }

    @Test
    void deveConsultarUmProduto(){
        given(produtoRepository.findById(anyString())).willReturn(Optional.ofNullable(produto));
        var produtoResponseResponseEntity = produtoService.consultarProduto("1");
        assertEquals(produtoResponseResponseEntity.getBody().nome(), "teste");
    }

    @Test
    void deveConsultarEstoqueDeUmProduto(){
        given(produtoRepository.findById(anyString())).willReturn(Optional.ofNullable(produto));
        given(produtoRepository.save(any(Produto.class))).willReturn(produto);
        var produtoResponseResponseEntity = produtoService.verificaDisponibilidadeDoProdutoNoEstoque("1", 2);
        assertTrue((boolean) produtoResponseResponseEntity.getBody());
    }

    @Test
    void deveConsultarQuantidadeMenorDeEstoqueDeUmProduto(){
        given(produtoRepository.findById(anyString())).willReturn(Optional.ofNullable(produto));
        given(produtoRepository.save(any(Produto.class))).willReturn(produto);
        var produtoResponseResponseEntity = produtoService.verificaDisponibilidadeDoProdutoNoEstoque("1", 4);
        assertEquals("Quantidade do produto " + produto.getId() + " : " + produto.getNome() + " indisponível" ,produtoResponseResponseEntity.getBody());
    }

}
