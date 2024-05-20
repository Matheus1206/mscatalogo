package br.com.fiap.catalogoprodutos.config;

import br.com.fiap.catalogoprodutos.model.Produto;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.data.builder.MongoItemWriterBuilder;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.transaction.PlatformTransactionManager;

@EnableBatchProcessing
public class BatchConfiguration {
    @Bean
    public Job processarProduto(JobRepository jobRepository, Step step){
        return new JobBuilder("processarProduto", jobRepository)
                .start(step)
                .build();
    }

    @Bean
    public Step step(JobRepository jobRepository,
                     PlatformTransactionManager jtaTransactionManager,
                     ItemReader<Produto> itemReader,
                     ItemWriter<Produto> itemWriter){
        return new StepBuilder("step", jobRepository)
                .<Produto,Produto>chunk(20, jtaTransactionManager)
                .reader(itemReader)
                .writer(itemWriter)
                .build();
    }



    @Bean
    public ItemReader<Produto> itemReader(){
        BeanWrapperFieldSetMapper<Produto> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
        fieldSetMapper.setTargetType(Produto.class);
        return new FlatFileItemReaderBuilder<Produto>()
                .name("produtoItemReader")
                .resource(new ClassPathResource("produtos-list.csv"))
                .delimited()
                .names("nome","descricao","preco","categoria","imagem","quantidadEstoque")
                .fieldSetMapper(fieldSetMapper)
                .build();
    }

    @Bean
    public ItemWriter<Produto> itemWriter(MongoTemplate mongoTemplate){
        return new MongoItemWriterBuilder<Produto>()
                .template(mongoTemplate)
                .collection("produtos")
                .build();
    }
}
