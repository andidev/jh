package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.CommentedEntity;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the CommentedEntity entity.
 */
public interface CommentedEntityRepository extends JpaRepository<CommentedEntity,Long> {

}
