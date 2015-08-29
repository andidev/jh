package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.CommentedEntity;
import com.mycompany.myapp.repository.CommentedEntityRepository;
import com.mycompany.myapp.web.rest.util.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing CommentedEntity.
 */
@RestController
@RequestMapping("/api")
public class CommentedEntityResource {

    private final Logger log = LoggerFactory.getLogger(CommentedEntityResource.class);

    @Inject
    private CommentedEntityRepository commentedEntityRepository;

    /**
     * POST  /commentedEntitys -> Create a new commentedEntity.
     */
    @RequestMapping(value = "/commentedEntitys",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<CommentedEntity> create(@RequestBody CommentedEntity commentedEntity) throws URISyntaxException {
        log.debug("REST request to save CommentedEntity : {}", commentedEntity);
        if (commentedEntity.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new commentedEntity cannot already have an ID").body(null);
        }
        CommentedEntity result = commentedEntityRepository.save(commentedEntity);
        return ResponseEntity.created(new URI("/api/commentedEntitys/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert("commentedEntity", result.getId().toString()))
                .body(result);
    }

    /**
     * PUT  /commentedEntitys -> Updates an existing commentedEntity.
     */
    @RequestMapping(value = "/commentedEntitys",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<CommentedEntity> update(@RequestBody CommentedEntity commentedEntity) throws URISyntaxException {
        log.debug("REST request to update CommentedEntity : {}", commentedEntity);
        if (commentedEntity.getId() == null) {
            return create(commentedEntity);
        }
        CommentedEntity result = commentedEntityRepository.save(commentedEntity);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert("commentedEntity", commentedEntity.getId().toString()))
                .body(result);
    }

    /**
     * GET  /commentedEntitys -> get all the commentedEntitys.
     */
    @RequestMapping(value = "/commentedEntitys",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<CommentedEntity> getAll() {
        log.debug("REST request to get all CommentedEntitys");
        return commentedEntityRepository.findAll();
    }

    /**
     * GET  /commentedEntitys/:id -> get the "id" commentedEntity.
     */
    @RequestMapping(value = "/commentedEntitys/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<CommentedEntity> get(@PathVariable Long id) {
        log.debug("REST request to get CommentedEntity : {}", id);
        return Optional.ofNullable(commentedEntityRepository.findOne(id))
            .map(commentedEntity -> new ResponseEntity<>(
                commentedEntity,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /commentedEntitys/:id -> delete the "id" commentedEntity.
     */
    @RequestMapping(value = "/commentedEntitys/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        log.debug("REST request to delete CommentedEntity : {}", id);
        commentedEntityRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("commentedEntity", id.toString())).build();
    }
}
