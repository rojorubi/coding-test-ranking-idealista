package com.idealista.infrastructure.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig extends WebMvcConfigurationSupport {
		
	@Bean
    public Docket productApi() {
        
    	return new Docket(DocumentationType.SWAGGER_2)
	        .select()
	        .apis(RequestHandlerSelectors.basePackage("com.idealista.infrastructure"))
	        .paths(PathSelectors.any()) 
	        .build()
	        .apiInfo(metaData());
    }
	      
    private ApiInfo metaData() {
        
    	return new ApiInfoBuilder()
    			.title("MICROSERVICIO PARA EL CÁLCULO DE CALIDAD DE ANUNCIOS")
    			.description("Los supuestos están basados en un hipotético equipo de gestión de calidad de los anuncios, que demanda una serie de verificaciones automáticas para clasificar los anuncios en base a una serie de características concretas.\n" + 
    					"\n" + 
    					"Historias de usuario\n" + 
    					"Yo, como encargado del equipo de gestión de calidad de los anuncios quiero asignar una puntuación a un anuncio para que los usuarios de idealista puedan ordenar anuncios de más completos a menos completos. La puntuación del anuncio es un valor entre 0 y 100 que se calcula teniendo encuenta las siguientes reglas:\n" + 
    					"\n" + 
    					"Si el anuncio no tiene ninguna foto se restan 10 puntos. Cada foto que tenga el anuncio proporciona 20 puntos si es una foto de alta resolución (HD) o 10 si no lo es.\n" + 
    					"Que el anuncio tenga un texto descriptivo suma 5 puntos.\n" + 
    					"El tamaño de la descripción también proporciona puntos cuando el anuncio es sobre un piso o sobre un chalet. En el caso de los pisos, la descripción aporta 10 puntos si tiene entre 20 y 49 palabras o 30 puntos si tiene 50 o mas palabras. En el caso de los chalets, si tiene mas de 50 palabras, añade 20 puntos.\n" + 
    					"Que las siguientes palabras aparezcan en la descripción añaden 5 puntos cada una: Luminoso, Nuevo, Céntrico, Reformado, Ático.\n" + 
    					"Que el anuncio esté completo también aporta puntos. Para considerar un anuncio completo este tiene que tener descripción, al menos una foto y los datos particulares de cada tipología, esto es, en el caso de los pisos tiene que tener también tamaño de vivienda, en el de los chalets, tamaño de vivienda y de jardín. Además, excepcionalmente, en los garajes no es necesario que el anuncio tenga descripción. Si el anuncio tiene todos los datos anteriores, proporciona otros 40 puntos.\n" + 
    					"Yo como encargado de calidad quiero que los usuarios no vean anuncios irrelevantes para que el usuario siempre vea contenido de calidad en idealista. Un anuncio se considera irrelevante si tiene una puntación inferior a 40 puntos.\n" + 
    					"\n" + 
    					"Yo como encargado de calidad quiero poder ver los anuncios irrelevantes y desde que fecha lo son para medir la calidad media del contenido del portal.\n" + 
    					"\n" + 
    					"Yo como usuario de idealista quiero poder ver los anuncios ordenados de mejor a peor para encontrar fácilmente mi vivienda.")
                .version("1.0.0")
                .license("2020 rojorubi for Idealista")
                .contact(new Contact("Rojorubi", "https://www.linkedin.com/in/rubirincon/", "rojorubi@gmail.com"))
                .build();
    }
    
    /*
     * To configure support for Swagger UI with Spring Boot 
     */
	@Override
	protected void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("swagger-ui.html")
	            .addResourceLocations("classpath:/META-INF/resources/");
	
	    registry.addResourceHandler("/webjars/**")
	            .addResourceLocations("classpath:/META-INF/resources/webjars/");
	}

	
}
