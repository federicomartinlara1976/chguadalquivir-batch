package net.bounceme.chronos.chguadalquivir.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;

@Configuration
public class OpenApiConfig {

	@Value("${spring.application.name}")
	String appName;

	@Value("${spring.application.version}")
	String appVersion;

	@Value("${spring.profiles.active}")
	String appProfile;

	@Value("${build.date}")
	String fechaCompilacion;

	OpenAPI customOpenAPI() {
		String descripcion = String.format(
				"<b>%s</b>Versión: <b>%s</b><br>Perfil activo: <b>%s</b><br>Fecha de compilación: %s", appName,
				appVersion, appProfile, fechaCompilacion);

		return new OpenAPI().info(new Info().title("CHGuadalquivir-batch").version(appVersion).description(descripcion))
				.addServersItem(new Server().url("").description("Default server URL"));
	}
}
