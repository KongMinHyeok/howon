package kr.co.voard.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
	  
	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
		    public void addCorsMappings(CorsRegistry registry) {
		        registry.addMapping("/**")
		                .allowedOrigins("http://localhost:8080")
		                .allowedOriginPatterns("http://localhost:8080")
		                .allowedOriginPatterns("http://localhost:3000")
		                .allowedHeaders("Content-Type", "Authorization")
		                .allowedMethods("GET", "POST", "PUT", "DELETE", "HEAD", "OPTIONS", "PATCH")
		                .exposedHeaders("Authorization", "RefreshToken");
		        
		    }
		};
	}
	
    
}