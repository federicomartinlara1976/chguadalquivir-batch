package net.bounceme.chronos.chguadalquivir.config;

import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

	@Bean
	public Docket apiDocket() {
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.apis(RequestHandlerSelectors.basePackage("net.bounceme.chronos.chguadalquivir.controller"))
				.paths(PathSelectors.any())
				.build()
				.apiInfo(getApiInfo())
				;
	}
	
	private ApiInfo getApiInfo() {
		return new ApiInfo(
				"CHGuadalquivir batch Api controller",
				"Api de control para el batch de chguadalquivir",
				"0.0.1-SNAPSHOT",
				"https://github.com/federicomartinlara1976/chguadalquivir-batch/blob/master/LICENSE",
				new Contact("CHGuadalquivir", "https://github.com/federicomartinlara1976/chguadalquivir-batch", "federicomartinlara1976@gmail.com"),
				"LICENSE",
				"LICENSE URL",
				Collections.emptyList()
				);
	}
}