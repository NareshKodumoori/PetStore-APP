package com.naresh.petstore.config;

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
public class SpringSwaggerConfig {                            
    @Bean
    public Docket api() { 
        return new Docket(DocumentationType.SWAGGER_2)  
          .select()                                  
          .apis(RequestHandlerSelectors.basePackage("com.naresh.petstore.controller"))              
          .paths(PathSelectors.any())                   
          .build().apiInfo(getApiInfo());                                    
    }

	private ApiInfo getApiInfo() {
		return new ApiInfo("Pet Store Application APIs", "Pet Store API Manual/Reference", "v1", null, 
				new Contact("Pet Store Java Developers", null, "NareshChinni.K@gmail.com"), null, null, Collections.emptyList());
	}
}