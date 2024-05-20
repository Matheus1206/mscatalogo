package br.com.fiap.catalogoprodutos;


import br.com.fiap.catalogoprodutos.controller.ProdutoController;
import br.com.fiap.catalogoprodutos.dto.ProdutoRequest;
import br.com.fiap.catalogoprodutos.services.ProdutoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@WebMvcTest(ProdutoController.class)
class ProdutoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProdutoService produtoService;


    @Test
    void deveEfetuarUmRequestNoPostDeCadastroDeProduto() throws Exception {
        int status = mockMvc.perform(
                        post("/api/produtos")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(asJsonString(new ProdutoRequest("teste", "teste", 123f, "teste", "teste", 6)))
                )
                .andReturn()
                .getResponse()
                .getStatus();
        assertEquals(201, status);
    }

    @Test
    void deveVerificarQueORetornoDoCadastroEstaCorreto() throws Exception {
        var status = mockMvc.perform(
                        post("/api/produtos")
                                .contentType("application/json;charset=UTF-8")
                                .content(asJsonString(new ProdutoRequest("teste", "teste", 123f, "teste", "teste", 6)))
                ).andReturn()
                .getResponse()
                .getStatus();
        assertEquals(201, status);
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
