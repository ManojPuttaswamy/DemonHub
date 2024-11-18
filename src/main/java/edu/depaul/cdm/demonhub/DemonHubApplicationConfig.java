package edu.depaul.cdm.demonhub;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@Configuration
public class DemonHubApplicationConfig {

    /**
	 * Defines services documentation based Swagger
	 * @see <a href="http://localhost:8080/swagger-ui/index.html">Swagger</a>  
	 * @param title
	 * @return
	 */
    @Bean
	public OpenAPI customOpenAPI(
		@Value("${app.doc.title:Default Title}") String title,
		@Value("${app.doc.version:Default Version}") String version,
		@Value("${app.doc.description:Default Description}") String description,
		@Value("${app.doc.terms-of-service:Default Terms}") String terms,
		@Value("${app.doc.license:Default License}") String license,
		@Value("${app.doc.url:Default Url}") String url
		) {
		return new OpenAPI().info(new Info()
		  .title("DemonHub")
		  .version("1.0")
		  .description("DemonHub is an exclusive e-commerce platform designed for the DePaul University community. Offering a curated selection of products tailored to students, faculty, and alumni, this one-stop shop provides everything from textbooks and tech to DePaul-branded gear and essentials. Accessible only to DePaul members, it's your trusted source for quality items that fit your unique university lifestyle. Safe, convenient, and just for Blue Demons!")
		  .termsOfService(terms)
		  .license(new License().name(license)
		  .url(url)));
	}	

    
}
