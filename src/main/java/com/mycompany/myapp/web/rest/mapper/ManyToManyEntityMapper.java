package com.mycompany.myapp.web.rest.mapper;

import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.web.rest.dto.ManyToManyEntityDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity ManyToManyEntity and its DTO ManyToManyEntityDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ManyToManyEntityMapper {

    ManyToManyEntityDTO manyToManyEntityToManyToManyEntityDTO(ManyToManyEntity manyToManyEntity);

    @Mapping(target = "multiRelationalEntitys", ignore = true)
    ManyToManyEntity manyToManyEntityDTOToManyToManyEntity(ManyToManyEntityDTO manyToManyEntityDTO);
}
