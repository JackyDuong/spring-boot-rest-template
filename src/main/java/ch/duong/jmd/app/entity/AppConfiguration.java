package ch.duong.jmd.#APP_ABBREVIATION.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static ch.duong.jmd.#APP_ABBREVIATION.entity.EntitiesConstants.APP_CONFIGURATION;
import static ch.duong.jmd.#APP_ABBREVIATION.entity.EntitiesConstants.PUBLIC_SCHEMA;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = APP_CONFIGURATION, schema = PUBLIC_SCHEMA)
public class AppConfiguration {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 2048, unique = true, nullable = false)
    private String key;
    @Column(length = 2048)
    private String value;
    private String comment;
}
