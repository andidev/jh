package com.mycompany.myapp.web.rest.mapper;

import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.web.rest.dto.MultiRelationalEntityDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity MultiRelationalEntity and its DTO MultiRelationalEntityDTO.
 */
@Mapper(componentModel = "spring", uses = {ManyToManyEntityMapper.class, })
public interface MultiRelationalEntityMapper {

    @Mapping(source = "oneToOneEntity.id", target = "oneToOneEntityId")
    @Mapping(source = "oneToManyEntity.id", target = "oneToManyEntityId")
    MultiRelationalEntityDTO multiRelationalEntityToMultiRelationalEntityDTO(MultiRelationalEntity multiRelationalEntity);

    @Mapping(source = "oneToOneEntityId", target = "oneToOneEntity")
    @Mapping(source = "oneToManyEntityId", target = "oneToManyEntity")
    MultiRelationalEntity multiRelationalEntityDTOToMultiRelationalEntity(MultiRelationalEntityDTO multiRelationalEntityDTO);

    default OneToOneEntity oneToOneEntityFromId(Long id) {
        if (id == null) {
            return null;
        }
        OneToOneEntity oneToOneEntity = new OneToOneEntity();
        oneToOneEntity.setId(id);
        return oneToOneEntity;
    }

    default ManyToManyEntity manyToManyEntityFromId(Long id) {
        if (id == null) {
            return null;
        }
        ManyToManyEntity manyToManyEntity = new ManyToManyEntity();
        manyToManyEntity.setId(id);
        return manyToManyEntity;
    }

    default OneToManyEntity oneToManyEntityFromId(Long id) {
        if (id == null) {
            return null;
        }
        OneToManyEntity oneToManyEntity = new OneToManyEntity();
        oneToManyEntity.setId(id);
        return oneToManyEntity;
    }
}
