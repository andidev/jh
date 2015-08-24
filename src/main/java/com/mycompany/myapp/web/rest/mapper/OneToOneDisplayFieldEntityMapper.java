package com.mycompany.myapp.web.rest.mapper;

import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.web.rest.dto.OneToOneDisplayFieldEntityDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity OneToOneDisplayFieldEntity and its DTO OneToOneDisplayFieldEntityDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface OneToOneDisplayFieldEntityMapper {

    OneToOneDisplayFieldEntityDTO oneToOneDisplayFieldEntityToOneToOneDisplayFieldEntityDTO(OneToOneDisplayFieldEntity oneToOneDisplayFieldEntity);

    @Mapping(target = "multiRelationalDisplayFieldEntity", ignore = true)
    OneToOneDisplayFieldEntity oneToOneDisplayFieldEntityDTOToOneToOneDisplayFieldEntity(OneToOneDisplayFieldEntityDTO oneToOneDisplayFieldEntityDTO);
}
