package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.OneToManyEntity;
import com.mycompany.myapp.repository.OneToManyEntityRepository;
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
 * REST controller for managing OneToManyEntity.
 */
@RestController
@RequestMapping("/api")
public class OneToManyEntityResource {

    private final Logger log = LoggerFactory.getLogger(OneToManyEntityResource.class);

    @Inject
    private OneToManyEntityRepository oneToManyEntityRepository;

    /**
     * POST  /oneToManyEntitys -> Create a new oneToManyEntity.
     */
    @RequestMapping(value = "/oneToManyEntitys",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<OneToManyEntity> create(@RequestBody OneToManyEntity oneToManyEntity) throws URISyntaxException {
        log.debug("REST request to save OneToManyEntity : {}", oneToManyEntity);
        if (oneToManyEntity.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new oneToManyEntity cannot already have an ID").body(null);
        }
        OneToManyEntity result = oneToManyEntityRepository.save(oneToManyEntity);
        return ResponseEntity.created(new URI("/api/oneToManyEntitys/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert("oneToManyEntity", result.getId().toString()))
                .body(result);
    }

    /**
     * PUT  /oneToManyEntitys -> Updates an existing oneToManyEntity.
     */
    @RequestMapping(value = "/oneToManyEntitys",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<OneToManyEntity> update(@RequestBody OneToManyEntity oneToManyEntity) throws URISyntaxException {
        log.debug("REST request to update OneToManyEntity : {}", oneToManyEntity);
        if (oneToManyEntity.getId() == null) {
            return create(oneToManyEntity);
        }
        OneToManyEntity result = oneToManyEntityRepository.save(oneToManyEntity);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert("oneToManyEntity", oneToManyEntity.getId().toString()))
                .body(result);
    }

    /**
     * GET  /oneToManyEntitys -> get all the oneToManyEntitys.
     */
    @RequestMapping(value = "/oneToManyEntitys",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<OneToManyEntity> getAll() {
        log.debug("REST request to get all OneToManyEntitys");
        return oneToManyEntityRepository.findAll();
    }

    /**
     * GET  /oneToManyEntitys/:id -> get the "id" oneToManyEntity.
     */
    @RequestMapping(value = "/oneToManyEntitys/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<OneToManyEntity> get(@PathVariable Long id) {
        log.debug("REST request to get OneToManyEntity : {}", id);
        return Optional.ofNullable(oneToManyEntityRepository.findOne(id))
            .map(oneToManyEntity -> new ResponseEntity<>(
                oneToManyEntity,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /oneToManyEntitys/:id -> delete the "id" oneToManyEntity.
     */
    @RequestMapping(value = "/oneToManyEntitys/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        log.debug("REST request to delete OneToManyEntity : {}", id);
        oneToManyEntityRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("oneToManyEntity", id.toString())).build();
    }
}
