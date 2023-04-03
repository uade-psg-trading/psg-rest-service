package integracionapp.psgtrading.configuration;

import integracionapp.psgtrading.exception.ErrorCode;
import integracionapp.psgtrading.exception.GlobalErrorHandler;
import io.swagger.v3.core.converter.AnnotatedType;
import io.swagger.v3.core.converter.ModelConverters;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.responses.ApiResponse;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    public static final String APPLICATION_JSON_VALUE = org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

    @Bean
    public OpenAPI openAPI() {

        Info info = new Info()
                .description("Here's some information about Trading GPS, as well as a bunch of market´s price.")
                .title("GPS trading")
                .version("1.0.0")
                .contact(new Contact().url("https://trading.com.ar"));

        return new OpenAPI().info(info);
    }

    @Bean
    public OpenApiCustomizer openApiCustomizer() {
        return openApi -> {
            Schema<?> errorSchema = ModelConverters.getInstance()
                    .resolveAsResolvedSchema(new AnnotatedType(GlobalErrorHandler.Error.class)).schema;
            openApi.getPaths()
                    .forEach((key, value) -> 
                            value.readOperations()
                                    .forEach(operation -> registerErrors(errorSchema, operation)));
        };
    }

    private static void registerErrors(Schema<?> errorSchema, Operation operation) {
        for (ErrorCode errorCode : ErrorCode.values()) {
            String status = String.valueOf(errorCode.getStatus());
            if (!operation.getResponses().containsKey(status)) {
                ApiResponse apiResponse = new ApiResponse()
                        .description(errorCode.getDescription())
                        .content(new Content().addMediaType(APPLICATION_JSON_VALUE, new MediaType().schema(errorSchema)));
                operation.getResponses()
                        .addApiResponse(status, apiResponse);
            } else {
                operation.getResponses().get(status).getContent().forEach((k, action) -> action.setSchema(errorSchema));
            }
        }
    }

}
