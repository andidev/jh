package com.mycompany.myapp.web.rest.mapper;

import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.web.rest.dto.OneToOneEntityDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity OneToOneEntity and its DTO OneToOneEntityDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface OneToOneEntityMapper {

    OneToOneEntityDTO oneToOneEntityToOneToOneEntityDTO(OneToOneEntity oneToOneEntity);

    @Mapping(target = "multiRelationalEntity", ignore = true)
    OneToOneEntity oneToOneEntityDTOToOneToOneEntity(OneToOneEntityDTO oneToOneEntityDTO);
}
