package ch.duong.jmd.#APP_ABBREVIATION.model.mapper;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.modelmapper.ModelMapper;

@Getter
@Setter
@RequiredArgsConstructor
public abstract class AbstractDtoConverter<E, D> implements DtoConverter<E, D> {

    private final ModelMapper modelMapper;
    private final Class<E> entityClass;
    private final Class<D> dtoClass;

    @Override
    public ModelMapper getMapper() {
        return this.modelMapper;
    }

    @Override
    public Class<E> getEntityClass() {
        return entityClass;
    }

    @Override
    public Class<D> getDtoClass() {
        return dtoClass;
    }
}
