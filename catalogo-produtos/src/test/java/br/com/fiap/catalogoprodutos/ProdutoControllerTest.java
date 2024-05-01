package br.com.fiap.catalogoprodutos;


import br.com.fiap.catalogoprodutos.controller.ProdutoController;
import br.com.fiap.catalogoprodutos.services.ProdutoService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
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

    @InjectMocks
    private ProdutoController produtoController;

    @Mock
    private ProdutoService produtoService;


    @Test
    void deveEfetuarUmRequestNoPostDeCadastroDeProduto() throws Exception {
        int status = mockMvc.perform(post("/api/produtos")).andReturn().getResponse().getStatus();
        assertEquals(201, status);
    }

    @Test
    void deveVerificarQueORetornoDoCadastroEstaCorreto() throws Exception {
        MvcResult mvcResult = mockMvc.perform(
                        post("/api/produtos")
                                .contentType("application/json;charset=UTF-8"))
                .andExpect((ResultMatcher) jsonPath("$.nome").value("Smartphone XYZ"))
                .andReturn();
        assertEquals("Smartphone XYZ", mvcResult.getResponse());
    }


}
