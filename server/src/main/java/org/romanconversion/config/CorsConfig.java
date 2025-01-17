package org.romanconversion.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Configuration class to enable and manage Cross-Origin Resource Sharing (CORS)
 * for the application. This ensures the backend can accept requests from
 * specific frontend origins, avoiding CORS issues in browser-based clients.
 */
@Configuration
public class CorsConfig implements WebMvcConfigurer {

    /**
     * The frontend URL is injected from the `application.yml`.
     * This allows flexibility to change the frontend URL without modifying the code.
     */
    @Value("${client.url}")
    private String clientUrl;

    /**
     * Configures CORS mappings for the application.
     *
     * @param registry the CORS registry used to define the CORS rules.
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // Define CORS rules for the "/romannumeral*" endpoint.
        registry.addMapping("/romannumeral*") // Match all URLs under "/romannumeral*"
                .allowedOrigins(clientUrl) // Allow requests only from the configured frontend URL.
                .allowedMethods("GET", "POST", "PUT", "DELETE") // Allow specified HTTP methods.
                .allowedHeaders("Access-Control-Allow-Origin") // Allow this specific header.
                .allowCredentials(false); // Disable the use of cookies in CORS requests,
                                          // can be changed to True if we need to for authenticated users


    }

}
