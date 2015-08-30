package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.ManyToManyEntity;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the ManyToManyEntity entity.
 */
public interface ManyToManyEntityRepository extends JpaRepository<ManyToManyEntity,Long> {

    @Query("select distinct manyToManyEntity from ManyToManyEntity manyToManyEntity left join fetch manyToManyEntity.userEntitys")
    List<ManyToManyEntity> findAllWithEagerRelationships();

    @Query("select manyToManyEntity from ManyToManyEntity manyToManyEntity left join fetch manyToManyEntity.userEntitys where manyToManyEntity.id =:id")
    ManyToManyEntity findOneWithEagerRelationships(@Param("id") Long id);

}
