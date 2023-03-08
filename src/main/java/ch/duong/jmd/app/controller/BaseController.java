package ch.duong.jmd.#APP_ABBREVIATION.controller;

import ch.duong.jmd.#APP_ABBREVIATION.exception.EntityNotFoundException;
import ch.duong.jmd.#APP_ABBREVIATION.model.#UPPER_CASE_APP_ABBREVIATIONHttpResponse;
import ch.duong.jmd.#APP_ABBREVIATION.model.Pagination;
import ch.duong.jmd.#APP_ABBREVIATION.utils.Strings;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

public class BaseController {

    protected Sort.Direction getSortDirection(String sort) {
        return Strings.isNullOrEmpty(sort) ? null : Sort.Direction.valueOf(sort.toUpperCase());
    }

    protected #UPPER_CASE_APP_ABBREVIATIONHttpResponse get#UPPER_CASE_APP_ABBREVIATIONHttpResponse(Page<?> page, Integer pageNo, Integer size) throws EntityNotFoundException {
        if (pageNo != null && pageNo - 1 > page.getTotalPages()) {
            throw new EntityNotFoundException();
        }
        Pagination pagination = Pagination.builder()
                .totalPages(page.getTotalPages())
                .page(pageNo != null ? pageNo : 1)
                .size(size != null ? size : page.getSize())
                .totalElements(page.getTotalElements())
                .build();

        return #UPPER_CASE_APP_ABBREVIATIONHttpResponse.builder().data(page.getContent()).meta(pagination).build();
    }
}
