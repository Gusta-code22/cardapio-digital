package br.com.cardapio.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "School Menu API",
                version = "1.0.0",
                description = "School meal management system used by a real educational institution.",
                contact = @Contact(
                        name = "Gustavo Miranda",
                        url = "https://github.com/Gusta-code22"
                ),
                license = @License(
                        name = "MIT License"
                )
        )
)
public class OpenApiConfig {
}