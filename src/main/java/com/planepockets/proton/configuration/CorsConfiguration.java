package com.planepockets.proton.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfiguration {

	private static final String GET = "GET", PUT = "PUT", DELETE = "DELETE", POST = "POST";
	
	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/api/v.10/user/admin/**").allowedOrigins("*");
                registry.addMapping("/api/v.10/user/user/**").allowedOrigins("*");
				registry.addMapping("/**").allowedMethods(GET, PUT, POST, DELETE).allowedHeaders("*")
						.allowedOriginPatterns("*").allowCredentials(true);
			}
		};
	}
}
