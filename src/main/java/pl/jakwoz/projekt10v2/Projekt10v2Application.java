package pl.jakwoz.projekt10v2;

import Controller.AppController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableConfigurationProperties
@ComponentScan(basePackages = {"Controller","dao"})
@EntityScan(basePackages = {"model"})
public class Projekt10v2Application {

	public static void main(String[] args) {
		SpringApplication.run(Projekt10v2Application.class, args);
	}
}
