package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.OneToOneDisplayFieldEntity;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the OneToOneDisplayFieldEntity entity.
 */
public interface OneToOneDisplayFieldEntityRepository extends JpaRepository<OneToOneDisplayFieldEntity,Long> {

}
