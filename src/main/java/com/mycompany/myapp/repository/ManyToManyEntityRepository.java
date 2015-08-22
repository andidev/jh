package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.ManyToManyEntity;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the ManyToManyEntity entity.
 */
public interface ManyToManyEntityRepository extends JpaRepository<ManyToManyEntity,Long> {

}
