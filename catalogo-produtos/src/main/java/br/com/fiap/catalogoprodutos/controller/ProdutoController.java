package br.com.fiap.catalogoprodutos.controller;

import br.com.fiap.catalogoprodutos.dto.ProdutoRequest;
import br.com.fiap.catalogoprodutos.dto.ProdutoResponse;
import br.com.fiap.catalogoprodutos.services.ProdutoService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/produtos")
public class ProdutoController {


    private final ProdutoService produtoService;

    public ProdutoController(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ProdutoResponse> cadastrar(@Valid @RequestBody ProdutoRequest produtoRequest){
        return produtoService.cadastrarProduto(produtoRequest);
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ProdutoResponse> atualizar(@Valid @PathVariable String id , @RequestBody ProdutoRequest produtoRequest ){
        return produtoService.atualizarProduto(id, produtoRequest);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<?> deletar(@Valid @PathVariable String id){
        return produtoService.deletarProduto(id);
    }

    @GetMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ProdutoResponse> consulta(@Valid @PathVariable String id){
        return produtoService.consultarProduto(id);
    }

    @GetMapping("{id}/estoque")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Integer> consultaEstoque(@Valid @PathVariable String id){
        return produtoService.consultarEstoqueDoProduto(id);
    }

    @PostMapping("{id}/{quantidade}/estoque")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<?> verificaEstoque(@Valid @PathVariable("id") String id, @PathVariable("quantidade") @Valid @Min(1) Integer quantidade){
        return produtoService.verificaDisponibilidadeDoProdutoNoEstoque(id, quantidade);
    }

}
