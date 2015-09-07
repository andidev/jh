package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.OneToManyDisplayFieldEntity;
import com.mycompany.myapp.repository.OneToManyDisplayFieldEntityRepository;
import com.mycompany.myapp.web.rest.util.HeaderUtil;
import com.mycompany.myapp.web.rest.util.PaginationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
 * REST controller for managing OneToManyDisplayFieldEntity.
 */
@RestController
@RequestMapping("/api")
public class OneToManyDisplayFieldEntityResource {

    private final Logger log = LoggerFactory.getLogger(OneToManyDisplayFieldEntityResource.class);

    @Inject
    private OneToManyDisplayFieldEntityRepository oneToManyDisplayFieldEntityRepository;

    /**
     * POST  /oneToManyDisplayFieldEntitys -> Create a new oneToManyDisplayFieldEntity.
     */
    @RequestMapping(value = "/oneToManyDisplayFieldEntitys",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<OneToManyDisplayFieldEntity> createOneToManyDisplayFieldEntity(@RequestBody OneToManyDisplayFieldEntity oneToManyDisplayFieldEntity) throws URISyntaxException {
        log.debug("REST request to save OneToManyDisplayFieldEntity : {}", oneToManyDisplayFieldEntity);
        if (oneToManyDisplayFieldEntity.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new oneToManyDisplayFieldEntity cannot already have an ID").body(null);
        }
        OneToManyDisplayFieldEntity result = oneToManyDisplayFieldEntityRepository.save(oneToManyDisplayFieldEntity);
        return ResponseEntity.created(new URI("/api/oneToManyDisplayFieldEntitys/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert("oneToManyDisplayFieldEntity", result.getId().toString()))
                .body(result);
    }

    /**
     * PUT  /oneToManyDisplayFieldEntitys -> Updates an existing oneToManyDisplayFieldEntity.
     */
    @RequestMapping(value = "/oneToManyDisplayFieldEntitys",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<OneToManyDisplayFieldEntity> updateOneToManyDisplayFieldEntity(@RequestBody OneToManyDisplayFieldEntity oneToManyDisplayFieldEntity) throws URISyntaxException {
        log.debug("REST request to update OneToManyDisplayFieldEntity : {}", oneToManyDisplayFieldEntity);
        if (oneToManyDisplayFieldEntity.getId() == null) {
            return createOneToManyDisplayFieldEntity(oneToManyDisplayFieldEntity);
        }
        OneToManyDisplayFieldEntity result = oneToManyDisplayFieldEntityRepository.save(oneToManyDisplayFieldEntity);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert("oneToManyDisplayFieldEntity", oneToManyDisplayFieldEntity.getId().toString()))
                .body(result);
    }

    /**
     * GET  /oneToManyDisplayFieldEntitys -> get all the oneToManyDisplayFieldEntitys.
     */
    @RequestMapping(value = "/oneToManyDisplayFieldEntitys",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<OneToManyDisplayFieldEntity>> getAllOneToManyDisplayFieldEntitys(Pageable pageable)
        throws URISyntaxException {
        Page<OneToManyDisplayFieldEntity> page = oneToManyDisplayFieldEntityRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/oneToManyDisplayFieldEntitys");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /oneToManyDisplayFieldEntitys/:id -> get the "id" oneToManyDisplayFieldEntity.
     */
    @RequestMapping(value = "/oneToManyDisplayFieldEntitys/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<OneToManyDisplayFieldEntity> getOneToManyDisplayFieldEntity(@PathVariable Long id) {
        log.debug("REST request to get OneToManyDisplayFieldEntity : {}", id);
        return Optional.ofNullable(oneToManyDisplayFieldEntityRepository.findOne(id))
            .map(oneToManyDisplayFieldEntity -> new ResponseEntity<>(
                oneToManyDisplayFieldEntity,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /oneToManyDisplayFieldEntitys/:id -> delete the "id" oneToManyDisplayFieldEntity.
     */
    @RequestMapping(value = "/oneToManyDisplayFieldEntitys/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteOneToManyDisplayFieldEntity(@PathVariable Long id) {
        log.debug("REST request to delete OneToManyDisplayFieldEntity : {}", id);
        oneToManyDisplayFieldEntityRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("oneToManyDisplayFieldEntity", id.toString())).build();
    }
}
