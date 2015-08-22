package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.MultiRelationalDisplayFieldEntity;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the MultiRelationalDisplayFieldEntity entity.
 */
public interface MultiRelationalDisplayFieldEntityRepository extends JpaRepository<MultiRelationalDisplayFieldEntity,Long> {

    @Query("select distinct multiRelationalDisplayFieldEntity from MultiRelationalDisplayFieldEntity multiRelationalDisplayFieldEntity left join fetch multiRelationalDisplayFieldEntity.manyToManyDisplayFieldEntitys")
    List<MultiRelationalDisplayFieldEntity> findAllWithEagerRelationships();

    @Query("select multiRelationalDisplayFieldEntity from MultiRelationalDisplayFieldEntity multiRelationalDisplayFieldEntity left join fetch multiRelationalDisplayFieldEntity.manyToManyDisplayFieldEntitys where multiRelationalDisplayFieldEntity.id =:id")
    MultiRelationalDisplayFieldEntity findOneWithEagerRelationships(@Param("id") Long id);

}
