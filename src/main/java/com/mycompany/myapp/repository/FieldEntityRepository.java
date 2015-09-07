package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.FieldEntity;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the FieldEntity entity.
 */
public interface FieldEntityRepository extends JpaRepository<FieldEntity,Long> {

}
