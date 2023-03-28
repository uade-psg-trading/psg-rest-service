package integracionapp.psgtrading.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI openAPI() {

        Info info = new Info()
                .description("Here's some information about Trading GPS, as well as a bunch of market´s price.")
                .title("GPS trading")
                .version("1.0.0")
                .contact(new Contact().url("https://trading.com.ar"));

        return new OpenAPI().info(info);
    }
}
