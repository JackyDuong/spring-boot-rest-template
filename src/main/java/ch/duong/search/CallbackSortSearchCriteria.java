package ch.jmd.search;

public interface CallbackSortSearchCriteria<S extends SearchCriteria, E> {
    int sort(S criteria, E e1, E e2);
}
