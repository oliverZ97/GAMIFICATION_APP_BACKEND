package app.restservice.apprestservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import java.util.Optional;

import app.restservice.apprestservice.Entities.User;
import app.restservice.apprestservice.Repositories.UserRepository;

@SpringBootApplication
@EnableConfigurationProperties
@EntityScan(basePackages = {"app.restservice.apprestservice"})  // scan JPA entities
public class AppRestserviceApplication {


	private static final Logger log = LoggerFactory.getLogger(AppRestserviceApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(AppRestserviceApplication.class, args);
	}

	@Bean
  public CommandLineRunner demo(UserRepository repository) {
    return (args) -> {
      // save a few customers
      repository.save(new User("oli@example.de", "Oliver", "Ziemann"));
      repository.save(new User("nives@example.de", "Nives", "Boni"));
      repository.save(new User("caro@example.de", "Caro", "Radtke"));
      repository.save(new User("sascha@example.de", "Sascha", "Minuth"));

      // fetch all customers
      log.info("Users found with findAll():");
      log.info("-------------------------------");
      for (User user : repository.findAll()) {
        log.info(user.toString());
      }
      log.info("");

      // fetch an individual customer by ID
	  Optional<User> unitOptional = repository.findById(1L);
      User user = unitOptional.get();
      log.info("User found with findById(1L):");
      log.info("--------------------------------");
      log.info(user.toString());
      log.info("");

      // for (Customer bauer : repository.findByLastName("Bauer")) {
      //  log.info(bauer.toString());
      // }
      log.info("");
    };
  }

}
