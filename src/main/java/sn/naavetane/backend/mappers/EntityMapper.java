package sn.naavetane.backend.mappers;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface EntityMapper<D, E> {

    E asEntity(D dto);

    D asDto(E entity);

    List<D> parse(List<E> entities);

    List<E> parseToEntity(List<D> entities);

    default Page<D> asPage(Page<E> entityPage, List<E> content) {
        Pageable pageable = entityPage.getPageable();
        List<D> dtoList = parse(content);
        return new PageImpl<>(dtoList, pageable, entityPage.getTotalElements());
    }
    default Page<D> asPage(Page<E> entityPage) {
        Pageable pageable = entityPage.getPageable();
        List<D> dtoList = parse(entityPage.getContent());
        return new PageImpl<>(dtoList, pageable, entityPage.getTotalElements());
    }
}
