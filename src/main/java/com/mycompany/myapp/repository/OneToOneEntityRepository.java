package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.OneToOneEntity;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the OneToOneEntity entity.
 */
public interface OneToOneEntityRepository extends JpaRepository<OneToOneEntity,Long> {

}
