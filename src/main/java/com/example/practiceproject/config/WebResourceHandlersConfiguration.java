package com.example.practiceproject.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * WebResourceHandlersConfiguration configures resource handlers for serving static resources.
 * This configuration allows resources from the specified file system path to be served
 * under the "/storage/**" URL pattern.
 */
@Configuration
public class WebResourceHandlersConfiguration implements WebMvcConfigurer {
    /**
     * Adds resource handlers to serve static resources from a file location.
     *
     * @param registry the ResourceHandlerRegistry to register resource handlers
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/storage/**")
                .addResourceLocations("file:C:/Users/eternalwill/Documents/IntellijProjects/gs-uploading-files2/storage/");
    }
}
