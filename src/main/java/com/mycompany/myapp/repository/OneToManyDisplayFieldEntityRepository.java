package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.OneToManyDisplayFieldEntity;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the OneToManyDisplayFieldEntity entity.
 */
public interface OneToManyDisplayFieldEntityRepository extends JpaRepository<OneToManyDisplayFieldEntity,Long> {

}
