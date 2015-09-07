package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.FieldEntity;
import com.mycompany.myapp.repository.FieldEntityRepository;
import com.mycompany.myapp.web.rest.util.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing FieldEntity.
 */
@RestController
@RequestMapping("/api")
public class FieldEntityResource {

    private final Logger log = LoggerFactory.getLogger(FieldEntityResource.class);

    @Inject
    private FieldEntityRepository fieldEntityRepository;

    /**
     * POST  /fieldEntitys -> Create a new fieldEntity.
     */
    @RequestMapping(value = "/fieldEntitys",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<FieldEntity> createFieldEntity(@Valid @RequestBody FieldEntity fieldEntity) throws URISyntaxException {
        log.debug("REST request to save FieldEntity : {}", fieldEntity);
        if (fieldEntity.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new fieldEntity cannot already have an ID").body(null);
        }
        FieldEntity result = fieldEntityRepository.save(fieldEntity);
        return ResponseEntity.created(new URI("/api/fieldEntitys/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert("fieldEntity", result.getId().toString()))
                .body(result);
    }

    /**
     * PUT  /fieldEntitys -> Updates an existing fieldEntity.
     */
    @RequestMapping(value = "/fieldEntitys",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<FieldEntity> updateFieldEntity(@Valid @RequestBody FieldEntity fieldEntity) throws URISyntaxException {
        log.debug("REST request to update FieldEntity : {}", fieldEntity);
        if (fieldEntity.getId() == null) {
            return createFieldEntity(fieldEntity);
        }
        FieldEntity result = fieldEntityRepository.save(fieldEntity);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert("fieldEntity", fieldEntity.getId().toString()))
                .body(result);
    }

    /**
     * GET  /fieldEntitys -> get all the fieldEntitys.
     */
    @RequestMapping(value = "/fieldEntitys",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<FieldEntity> getAllFieldEntitys() {
        log.debug("REST request to get all FieldEntitys");
        return fieldEntityRepository.findAll();
    }

    /**
     * GET  /fieldEntitys/:id -> get the "id" fieldEntity.
     */
    @RequestMapping(value = "/fieldEntitys/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<FieldEntity> getFieldEntity(@PathVariable Long id) {
        log.debug("REST request to get FieldEntity : {}", id);
        return Optional.ofNullable(fieldEntityRepository.findOne(id))
            .map(fieldEntity -> new ResponseEntity<>(
                fieldEntity,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /fieldEntitys/:id -> delete the "id" fieldEntity.
     */
    @RequestMapping(value = "/fieldEntitys/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteFieldEntity(@PathVariable Long id) {
        log.debug("REST request to delete FieldEntity : {}", id);
        fieldEntityRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("fieldEntity", id.toString())).build();
    }
}
