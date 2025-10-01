package com.webgram.dgpsn.properties;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.cors.CorsConfiguration;

@Data
 @Component
 @Validated
 @FieldDefaults(level = AccessLevel.PRIVATE)
 @ConfigurationProperties(prefix = "dgpsn-cors")
 public class CorsProperties {

     private final CorsConfiguration cors = new CorsConfiguration();
 }
