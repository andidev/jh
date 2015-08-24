package com.mycompany.myapp.web.rest.mapper;

import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.web.rest.dto.MultiRelationalDisplayFieldEntityDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity MultiRelationalDisplayFieldEntity and its DTO MultiRelationalDisplayFieldEntityDTO.
 */
@Mapper(componentModel = "spring", uses = {ManyToManyDisplayFieldEntityMapper.class, })
public interface MultiRelationalDisplayFieldEntityMapper {

    @Mapping(source = "oneToOneDisplayFieldEntity.id", target = "oneToOneDisplayFieldEntityId")
    @Mapping(source = "oneToOneDisplayFieldEntity.displayField", target = "oneToOneDisplayFieldEntityDisplayField")
    @Mapping(source = "oneToManyDisplayFieldEntity.id", target = "oneToManyDisplayFieldEntityId")
    @Mapping(source = "oneToManyDisplayFieldEntity.displayField", target = "oneToManyDisplayFieldEntityDisplayField")
    MultiRelationalDisplayFieldEntityDTO multiRelationalDisplayFieldEntityToMultiRelationalDisplayFieldEntityDTO(MultiRelationalDisplayFieldEntity multiRelationalDisplayFieldEntity);

    @Mapping(source = "oneToOneDisplayFieldEntityId", target = "oneToOneDisplayFieldEntity")
    @Mapping(source = "oneToManyDisplayFieldEntityId", target = "oneToManyDisplayFieldEntity")
    MultiRelationalDisplayFieldEntity multiRelationalDisplayFieldEntityDTOToMultiRelationalDisplayFieldEntity(MultiRelationalDisplayFieldEntityDTO multiRelationalDisplayFieldEntityDTO);

    default OneToOneDisplayFieldEntity oneToOneDisplayFieldEntityFromId(Long id) {
        if (id == null) {
            return null;
        }
        OneToOneDisplayFieldEntity oneToOneDisplayFieldEntity = new OneToOneDisplayFieldEntity();
        oneToOneDisplayFieldEntity.setId(id);
        return oneToOneDisplayFieldEntity;
    }

    default ManyToManyDisplayFieldEntity manyToManyDisplayFieldEntityFromId(Long id) {
        if (id == null) {
            return null;
        }
        ManyToManyDisplayFieldEntity manyToManyDisplayFieldEntity = new ManyToManyDisplayFieldEntity();
        manyToManyDisplayFieldEntity.setId(id);
        return manyToManyDisplayFieldEntity;
    }

    default OneToManyDisplayFieldEntity oneToManyDisplayFieldEntityFromId(Long id) {
        if (id == null) {
            return null;
        }
        OneToManyDisplayFieldEntity oneToManyDisplayFieldEntity = new OneToManyDisplayFieldEntity();
        oneToManyDisplayFieldEntity.setId(id);
        return oneToManyDisplayFieldEntity;
    }
}
