package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.MultiRelationalEntity;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the MultiRelationalEntity entity.
 */
public interface MultiRelationalEntityRepository extends JpaRepository<MultiRelationalEntity,Long> {

    @Query("select distinct multiRelationalEntity from MultiRelationalEntity multiRelationalEntity left join fetch multiRelationalEntity.manyToManyEntitys")
    List<MultiRelationalEntity> findAllWithEagerRelationships();

    @Query("select multiRelationalEntity from MultiRelationalEntity multiRelationalEntity left join fetch multiRelationalEntity.manyToManyEntitys where multiRelationalEntity.id =:id")
    MultiRelationalEntity findOneWithEagerRelationships(@Param("id") Long id);

}
