package com.mycompany.myapp.web.rest.mapper;

import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.web.rest.dto.OneToManyDisplayFieldEntityDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity OneToManyDisplayFieldEntity and its DTO OneToManyDisplayFieldEntityDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface OneToManyDisplayFieldEntityMapper {

    OneToManyDisplayFieldEntityDTO oneToManyDisplayFieldEntityToOneToManyDisplayFieldEntityDTO(OneToManyDisplayFieldEntity oneToManyDisplayFieldEntity);

    @Mapping(target = "multiRelationalDisplayFieldEntitys", ignore = true)
    OneToManyDisplayFieldEntity oneToManyDisplayFieldEntityDTOToOneToManyDisplayFieldEntity(OneToManyDisplayFieldEntityDTO oneToManyDisplayFieldEntityDTO);
}
