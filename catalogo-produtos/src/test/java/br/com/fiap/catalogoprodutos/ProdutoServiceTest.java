package br.com.fiap.catalogoprodutos;

import br.com.fiap.catalogoprodutos.dto.ProdutoRequest;
import br.com.fiap.catalogoprodutos.dto.ProdutoResponse;
import br.com.fiap.catalogoprodutos.model.Produto;
import br.com.fiap.catalogoprodutos.repository.ProdutoRepository;
import br.com.fiap.catalogoprodutos.services.ProdutoService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import static org.mockito.BDDMockito.given;


@ExtendWith(MockitoExtension.class)
class ProdutoServiceTest {

    @InjectMocks
    private ProdutoService produtoService;

    @Mock
    private ProdutoRepository produtoRepository;

    @Mock
    private Produto produto;

    @Mock
    private ProdutoRequest produtoRequest;


    @Test
    void deveCadastrarUmProdutoComTodosOsCamposValidos(){
        given(produtoRepository.save(produtoRequest.toModel())).willReturn(produto);
        ResponseEntity<ProdutoResponse> produtoResponseResponseEntity = produtoService.cadastrarProduto(produtoRequest);
    }



}
