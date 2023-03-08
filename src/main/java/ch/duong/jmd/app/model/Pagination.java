package ch.duong.jmd.#APP_ABBREVIATION.model;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Pagination {
    Integer totalPages;
    Long totalElements;
    Integer page;
    Integer size;
}
