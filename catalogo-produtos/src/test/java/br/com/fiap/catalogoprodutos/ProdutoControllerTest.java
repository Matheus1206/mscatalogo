package br.com.fiap.catalogoprodutos;


import br.com.fiap.catalogoprodutos.dto.ProdutoRequest;
import br.com.fiap.catalogoprodutos.dto.ProdutoResponse;
import br.com.fiap.catalogoprodutos.model.Produto;
import br.com.fiap.catalogoprodutos.services.ProdutoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@SpringBootTest
@AutoConfigureMockMvc
class ProdutoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProdutoService produtoService;

    @Autowired
    private ObjectMapper mapper;


    @Test
    void deveCadastrarProduto() throws Exception {
        ProdutoRequest produtoRequest = new ProdutoRequest("Teste", "teste", 3f, "teste", "teste", 2);
        Produto model = produtoRequest.toModel();
        ResponseEntity<ProdutoResponse> produtoResponseResponseEntity = ResponseEntity.created(null).body(new ProdutoResponse(model));
        given(produtoService.cadastrarProduto(any(ProdutoRequest.class))).willReturn(produtoResponseResponseEntity);
        int status = mockMvc.perform(post("/api/produtos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(produtoRequest)))
                .andReturn()
                .getResponse()
                .getStatus();
        assertEquals(201, status);
    }

    @Test
    void deveAtualizarProduto() throws Exception {
        ProdutoRequest produtoRequest = new ProdutoRequest("Teste", "teste", 3f, "teste", "teste", 2);
        Produto model = produtoRequest.toModel();
        ResponseEntity<ProdutoResponse> produtoResponseResponseEntity = ResponseEntity.ok(new ProdutoResponse(model));
        given(produtoService.atualizarProduto(anyString(),any(ProdutoRequest.class))).willReturn(produtoResponseResponseEntity);
        int status = mockMvc.perform(put("/api/produtos/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(produtoRequest)))
                .andReturn()
                .getResponse()
                .getStatus();
        assertEquals(200, status);
    }

    @Test
    void deveDeletarProduto() throws Exception {
        given(produtoService.deletarProduto(anyString())).willReturn(ResponseEntity.noContent().build());
        int status = mockMvc.perform(delete("/api/produtos/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        )
                .andReturn()
                .getResponse()
                .getStatus();
        assertEquals(204, status);
    }

    @Test
    void deveConsultarUmProduto() throws Exception {
        ProdutoRequest produtoRequest = new ProdutoRequest("Teste", "teste", 3f, "teste", "teste", 2);
        Produto model = produtoRequest.toModel();
        ResponseEntity<ProdutoResponse> produtoResponseResponseEntity = ResponseEntity.ok(new ProdutoResponse(model));
        given(produtoService.consultarProduto(anyString())).willReturn(produtoResponseResponseEntity);
        int status = mockMvc.perform(get("/api/produtos/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(produtoRequest)))
                .andReturn()
                .getResponse()
                .getStatus();
        assertEquals(200, status);
    }

    @Test
    void deveConsultarEstoqueDeUmProduto() throws Exception {
        ProdutoRequest produtoRequest = new ProdutoRequest("Teste", "teste", 3f, "teste", "teste", 2);
        Produto model = produtoRequest.toModel();
        ResponseEntity<Integer> produtoResponseResponseEntity = ResponseEntity.ok(1);
        given(produtoService.consultarEstoqueDoProduto(anyString())).willReturn(produtoResponseResponseEntity);
        int status = mockMvc.perform(get("/api/produtos/1/estoque")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(produtoRequest)))
                .andReturn()
                .getResponse()
                .getStatus();
        assertEquals(200, status);
    }

    @Test
    void deveConsultarDisponibilidadeEstoqueDeUmProduto() throws Exception {
        given(produtoService.verificaDisponibilidadeDoProdutoNoEstoque(anyString(), anyInt())).willReturn(ResponseEntity.noContent().build());
        int status = mockMvc.perform(post("/api/produtos/1/3/estoque")
                        .contentType(MediaType.APPLICATION_JSON)
                        )
                .andReturn()
                .getResponse()
                .getStatus();
        assertEquals(204, status);
    }
}
