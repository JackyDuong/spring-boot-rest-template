package ch.duong.jmd.#APP_ABBREVIATION.configuration;

import ch.duong.jmd.#APP_ABBREVIATION.utils.Dates;
import org.modelmapper.AbstractConverter;
import org.modelmapper.ModelMapper;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;

@Configuration
public class ApplicationConfiguration {

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder
                .setConnectTimeout(Duration.ofSeconds(10))
                .setReadTimeout(Duration.ofSeconds(10))
                .build();
    }

    @Bean
    public ModelMapper modelMapper() {

        var dateTimeConverter = new AbstractConverter<LocalDateTime, Instant>() {
            protected Instant convert(LocalDateTime source) {
                if (source == null) {
                    return null;
                }

                return Dates.toInstant(source);
            }
        };

        var instantConverter = new AbstractConverter<Instant, LocalDateTime>() {
            protected LocalDateTime convert(Instant source) {
                if (source == null) {
                    return null;
                }
                return Dates.toLocalDateTime(source);
            }
        };

        ModelMapper modelMapper = new ModelMapper();
        modelMapper.addConverter(dateTimeConverter);
        modelMapper.addConverter(instantConverter);

        return modelMapper;
    }
}
