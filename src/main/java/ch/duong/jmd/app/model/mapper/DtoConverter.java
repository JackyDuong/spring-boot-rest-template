package ch.duong.jmd.#APP_ABBREVIATION.model.mapper;

import org.modelmapper.ModelMapper;

public interface DtoConverter<E, D> {

    ModelMapper getMapper();

    Class<E> getEntityClass();

    Class<D> getDtoClass();

    default D convertToDTo(E source) {
        if (source == null) {
            return null;
        }

        return getMapper().map(source, getDtoClass());
    }

    default E convertToEntity(D source) {
        if (source == null) {
            return null;
        }

        return getMapper().map(source, getEntityClass());
    }
}
