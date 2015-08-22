package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.MultiRelationalDisplayFieldEntity;
import com.mycompany.myapp.repository.MultiRelationalDisplayFieldEntityRepository;
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
 * REST controller for managing MultiRelationalDisplayFieldEntity.
 */
@RestController
@RequestMapping("/api")
public class MultiRelationalDisplayFieldEntityResource {

    private final Logger log = LoggerFactory.getLogger(MultiRelationalDisplayFieldEntityResource.class);

    @Inject
    private MultiRelationalDisplayFieldEntityRepository multiRelationalDisplayFieldEntityRepository;

    /**
     * POST  /multiRelationalDisplayFieldEntitys -> Create a new multiRelationalDisplayFieldEntity.
     */
    @RequestMapping(value = "/multiRelationalDisplayFieldEntitys",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<MultiRelationalDisplayFieldEntity> create(@RequestBody MultiRelationalDisplayFieldEntity multiRelationalDisplayFieldEntity) throws URISyntaxException {
        log.debug("REST request to save MultiRelationalDisplayFieldEntity : {}", multiRelationalDisplayFieldEntity);
        if (multiRelationalDisplayFieldEntity.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new multiRelationalDisplayFieldEntity cannot already have an ID").body(null);
        }
        MultiRelationalDisplayFieldEntity result = multiRelationalDisplayFieldEntityRepository.save(multiRelationalDisplayFieldEntity);
        return ResponseEntity.created(new URI("/api/multiRelationalDisplayFieldEntitys/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert("multiRelationalDisplayFieldEntity", result.getId().toString()))
                .body(result);
    }

    /**
     * PUT  /multiRelationalDisplayFieldEntitys -> Updates an existing multiRelationalDisplayFieldEntity.
     */
    @RequestMapping(value = "/multiRelationalDisplayFieldEntitys",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<MultiRelationalDisplayFieldEntity> update(@RequestBody MultiRelationalDisplayFieldEntity multiRelationalDisplayFieldEntity) throws URISyntaxException {
        log.debug("REST request to update MultiRelationalDisplayFieldEntity : {}", multiRelationalDisplayFieldEntity);
        if (multiRelationalDisplayFieldEntity.getId() == null) {
            return create(multiRelationalDisplayFieldEntity);
        }
        MultiRelationalDisplayFieldEntity result = multiRelationalDisplayFieldEntityRepository.save(multiRelationalDisplayFieldEntity);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert("multiRelationalDisplayFieldEntity", multiRelationalDisplayFieldEntity.getId().toString()))
                .body(result);
    }

    /**
     * GET  /multiRelationalDisplayFieldEntitys -> get all the multiRelationalDisplayFieldEntitys.
     */
    @RequestMapping(value = "/multiRelationalDisplayFieldEntitys",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<MultiRelationalDisplayFieldEntity> getAll() {
        log.debug("REST request to get all MultiRelationalDisplayFieldEntitys");
        return multiRelationalDisplayFieldEntityRepository.findAllWithEagerRelationships();
    }

    /**
     * GET  /multiRelationalDisplayFieldEntitys/:id -> get the "id" multiRelationalDisplayFieldEntity.
     */
    @RequestMapping(value = "/multiRelationalDisplayFieldEntitys/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<MultiRelationalDisplayFieldEntity> get(@PathVariable Long id) {
        log.debug("REST request to get MultiRelationalDisplayFieldEntity : {}", id);
        return Optional.ofNullable(multiRelationalDisplayFieldEntityRepository.findOneWithEagerRelationships(id))
            .map(multiRelationalDisplayFieldEntity -> new ResponseEntity<>(
                multiRelationalDisplayFieldEntity,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /multiRelationalDisplayFieldEntitys/:id -> delete the "id" multiRelationalDisplayFieldEntity.
     */
    @RequestMapping(value = "/multiRelationalDisplayFieldEntitys/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        log.debug("REST request to delete MultiRelationalDisplayFieldEntity : {}", id);
        multiRelationalDisplayFieldEntityRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("multiRelationalDisplayFieldEntity", id.toString())).build();
    }
}
