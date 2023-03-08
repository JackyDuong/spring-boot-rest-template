package ch.jmd.search;

import org.springframework.data.jpa.domain.Specification;

import java.util.Objects;

public final class CriteriaSpecification {
    private CriteriaSpecification() {
    }

    public static <E> Specification<E> and(Specification<E> spec, final boolean conditionnedValue, final CallbackCondition<E> callback) {
        if (Objects.nonNull(spec) && conditionnedValue) {
            return spec.and(callback.execute());
        }
        return spec;
    }

    public interface CallbackCondition<C> {
        Specification<C> execute();
    }
}
