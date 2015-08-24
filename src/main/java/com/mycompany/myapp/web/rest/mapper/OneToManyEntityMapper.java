package com.mycompany.myapp.web.rest.mapper;

import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.web.rest.dto.OneToManyEntityDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity OneToManyEntity and its DTO OneToManyEntityDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface OneToManyEntityMapper {

    OneToManyEntityDTO oneToManyEntityToOneToManyEntityDTO(OneToManyEntity oneToManyEntity);

    @Mapping(target = "multiRelationalEntitys", ignore = true)
    OneToManyEntity oneToManyEntityDTOToOneToManyEntity(OneToManyEntityDTO oneToManyEntityDTO);
}
