package com.mycompany.myapp.web.rest.mapper;

import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.web.rest.dto.ManyToManyDisplayFieldEntityDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity ManyToManyDisplayFieldEntity and its DTO ManyToManyDisplayFieldEntityDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ManyToManyDisplayFieldEntityMapper {

    ManyToManyDisplayFieldEntityDTO manyToManyDisplayFieldEntityToManyToManyDisplayFieldEntityDTO(ManyToManyDisplayFieldEntity manyToManyDisplayFieldEntity);

    @Mapping(target = "multiRelationalDisplayFieldEntitys", ignore = true)
    ManyToManyDisplayFieldEntity manyToManyDisplayFieldEntityDTOToManyToManyDisplayFieldEntity(ManyToManyDisplayFieldEntityDTO manyToManyDisplayFieldEntityDTO);
}
