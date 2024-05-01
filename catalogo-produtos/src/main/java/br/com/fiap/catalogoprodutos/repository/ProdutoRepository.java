package br.com.fiap.catalogoprodutos.repository;

import br.com.fiap.catalogoprodutos.model.Produto;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProdutoRepository extends MongoRepository<Produto, String> {
}
