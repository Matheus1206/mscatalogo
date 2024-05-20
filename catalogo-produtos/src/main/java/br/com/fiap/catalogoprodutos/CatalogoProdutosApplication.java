package br.com.fiap.catalogoprodutos;

import br.com.fiap.catalogoprodutos.config.BatchConfiguration;
import br.com.fiap.catalogoprodutos.config.MongoDBConfiguration;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class CatalogoProdutosApplication {

	public static void main(String[] args) throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException {
		SpringApplication.run(CatalogoProdutosApplication.class, args);
		Class<?>[] configurationClasses = { BatchConfiguration.class,
				MongoDBConfiguration.class };
		ApplicationContext context = new AnnotationConfigApplicationContext(configurationClasses);
		JobLauncher jobLauncher = context.getBean(JobLauncher.class);
		Job insertionJob = context.getBean("processarProduto", Job.class);
		jobLauncher.run(insertionJob, new JobParameters());
	}

}
