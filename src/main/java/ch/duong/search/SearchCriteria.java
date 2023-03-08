package ch.jmd.search;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.domain.Sort;

@AllArgsConstructor
@SuperBuilder
@Data
public class SearchCriteria {
    private Integer page;
    private Integer size;
    private Sort.Direction sort;
    private String sortBy;
}
