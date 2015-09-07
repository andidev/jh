package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.ManyToManyDisplayFieldEntity;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the ManyToManyDisplayFieldEntity entity.
 */
public interface ManyToManyDisplayFieldEntityRepository extends JpaRepository<ManyToManyDisplayFieldEntity,Long> {

}
