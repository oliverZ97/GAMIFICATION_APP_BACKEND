package app.restservice.apprestservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;


@SpringBootApplication
@EnableConfigurationProperties
@EntityScan(basePackages = {"app.restservice.apprestservice"})  // scan JPA entities
public class AppRestserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(AppRestserviceApplication.class, args);
	}

}
