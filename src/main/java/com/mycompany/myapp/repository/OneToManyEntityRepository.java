package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.OneToManyEntity;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the OneToManyEntity entity.
 */
public interface OneToManyEntityRepository extends JpaRepository<OneToManyEntity,Long> {

}
