package ch.jmd.search;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public interface ISearchCriteriaService<S extends SearchCriteria, E> {

    default Pageable buildPagination(SearchCriteria criteria, Class<E> classObj) {
        return buildPagination(criteria, classObj, null);
    }

    default Pageable buildPagination(SearchCriteria criteria, Class<E> classObj, Map<String, String> customCriteria) {
        Pageable pageable = Pageable.unpaged();

        if (!(criteria.getPage() != null && criteria.getSize() != null)) {
            return pageable;
        }

        pageable = PageRequest.of(criteria.getPage() - 1, criteria.getSize());

        if (!(criteria.getSort() != null && criteria.getSortBy() != null)) {
            return pageable;
        }

        // verify that field exists
        StringBuilder fields = new StringBuilder();
        for (var sortBy : criteria.getSortBy().split(",")) {
            var finalSortBy = sortBy;

            // check on class fields
            var optionField = Arrays.stream(classObj.getDeclaredFields()).filter(field -> field.getName().equals(sortBy)).findFirst();
            var fieldExists = optionField.isPresent();

            // not exists, check on custom criteria
            if (!fieldExists && customCriteria != null && customCriteria.containsKey(sortBy)) {
                fieldExists = true;
                finalSortBy = customCriteria.get(sortBy);
            }

            if (fieldExists) {
                if (fields.length() == 0) {
                    fields.append(finalSortBy);
                } else {
                    fields.append(",").append(finalSortBy);
                }
            }
        }

        if (fields.length() > 0) {
            return PageRequest.of(criteria.getPage() - 1, criteria.getSize(), Sort.by(criteria.getSort(), fields.toString().split(",")));
        }
        return pageable;
    }

    default List<E> sortPageable(S criteria, Page<E> entities, CallbackSortSearchCriteria<S, E> callbackSortSearchCriteria) {
        if (callbackSortSearchCriteria == null) {
            return entities.getContent();
        }
        return entities.stream().sorted((e1, e2) -> {
            var asc = 0;
            if (criteria.getSort() != null) {
                asc = criteria.getSort().isAscending() ? 1 : -1;
            }
            return asc * callbackSortSearchCriteria.sort(criteria, e1, e2);
        }).collect(Collectors.toList());
    }
}
