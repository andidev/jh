package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.ImageEntity;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the ImageEntity entity.
 */
public interface ImageEntityRepository extends JpaRepository<ImageEntity,Long> {

}
