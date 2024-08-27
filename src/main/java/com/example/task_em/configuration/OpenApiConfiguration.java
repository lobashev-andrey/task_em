package com.example.task_em.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfiguration {

    @Bean
    public OpenAPI openApiDescription() {
        Server localhostServer = new Server();
        localhostServer.setUrl("http://localhost:8080");
        localhostServer.setDescription("Local env");

        Server productionServer = new Server();
        productionServer.setUrl("http://some__production.url");
        productionServer.setDescription("Production env");

        Contact contact = new Contact();
        contact.setName("Andrey Lobashev");
        contact.setEmail("myEmail@example.ru");
        contact.setUrl("http://mySite.url");

        License mitLisense = new License().name("GNU AGPLv3")
                .url("https://choosealicense.com/license/agpl-3.0/");

        Info info = new Info()
                .title("Client tasks API")
                .version("1.0")
                .contact(contact)
                .description("API for client tasks")
                .termsOfService("http://some.terms.url")
                .license(mitLisense);

        return new OpenAPI().info(info).servers(List.of(localhostServer, productionServer));
    }
}
