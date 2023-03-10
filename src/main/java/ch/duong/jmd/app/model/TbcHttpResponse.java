package ch.duong.jmd.#APP_ABBREVIATION.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data

@JsonInclude(JsonInclude.Include.NON_NULL)
public class #UPPER_CASE_APP_ABBREVIATIONHttpResponse {
    private Object meta;
    private Object data;
    private Object errors;
}
