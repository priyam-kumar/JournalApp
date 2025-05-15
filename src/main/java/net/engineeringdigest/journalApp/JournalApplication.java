package net.engineeringdigest.journalApp;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class JournalApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(JournalApplication.class, args);
		System.out.println(context.getEnvironment().getActiveProfiles()[0]);
	}

	@Bean
	public PlatformTransactionManager transConfig(MongoDatabaseFactory dbFactory){
		return new MongoTransactionManager(dbFactory);
	}

//	@Bean
//	public MongoTemplate mongoTemplate() throws Exception {
//		MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");
//		return new MongoTemplate(mongoClient, "journaldb");
//	}

}
