package ru.akrtkv.egisz.vimis.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    OpenAPI getOpenApi() {
        var contact = new Contact();
        contact.setName("");
        contact.setEmail("");
        contact.setUrl("");
        return new OpenAPI()
                .info(
                        new Info().title("vimis")
                                .description("Набор методов для сервисов ВИМИС")
                                .contact(contact)
                                .version("1.0.0")
                );
    }
}
