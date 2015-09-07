package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.OneToOneDisplayFieldEntity;
import com.mycompany.myapp.repository.OneToOneDisplayFieldEntityRepository;
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
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * REST controller for managing OneToOneDisplayFieldEntity.
 */
@RestController
@RequestMapping("/api")
public class OneToOneDisplayFieldEntityResource {

    private final Logger log = LoggerFactory.getLogger(OneToOneDisplayFieldEntityResource.class);

    @Inject
    private OneToOneDisplayFieldEntityRepository oneToOneDisplayFieldEntityRepository;

    /**
     * POST  /oneToOneDisplayFieldEntitys -> Create a new oneToOneDisplayFieldEntity.
     */
    @RequestMapping(value = "/oneToOneDisplayFieldEntitys",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<OneToOneDisplayFieldEntity> createOneToOneDisplayFieldEntity(@RequestBody OneToOneDisplayFieldEntity oneToOneDisplayFieldEntity) throws URISyntaxException {
        log.debug("REST request to save OneToOneDisplayFieldEntity : {}", oneToOneDisplayFieldEntity);
        if (oneToOneDisplayFieldEntity.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new oneToOneDisplayFieldEntity cannot already have an ID").body(null);
        }
        OneToOneDisplayFieldEntity result = oneToOneDisplayFieldEntityRepository.save(oneToOneDisplayFieldEntity);
        return ResponseEntity.created(new URI("/api/oneToOneDisplayFieldEntitys/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert("oneToOneDisplayFieldEntity", result.getId().toString()))
                .body(result);
    }

    /**
     * PUT  /oneToOneDisplayFieldEntitys -> Updates an existing oneToOneDisplayFieldEntity.
     */
    @RequestMapping(value = "/oneToOneDisplayFieldEntitys",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<OneToOneDisplayFieldEntity> updateOneToOneDisplayFieldEntity(@RequestBody OneToOneDisplayFieldEntity oneToOneDisplayFieldEntity) throws URISyntaxException {
        log.debug("REST request to update OneToOneDisplayFieldEntity : {}", oneToOneDisplayFieldEntity);
        if (oneToOneDisplayFieldEntity.getId() == null) {
            return createOneToOneDisplayFieldEntity(oneToOneDisplayFieldEntity);
        }
        OneToOneDisplayFieldEntity result = oneToOneDisplayFieldEntityRepository.save(oneToOneDisplayFieldEntity);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert("oneToOneDisplayFieldEntity", oneToOneDisplayFieldEntity.getId().toString()))
                .body(result);
    }

    /**
     * GET  /oneToOneDisplayFieldEntitys -> get all the oneToOneDisplayFieldEntitys.
     */
    @RequestMapping(value = "/oneToOneDisplayFieldEntitys",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<OneToOneDisplayFieldEntity>> getAllOneToOneDisplayFieldEntitys(Pageable pageable, @RequestParam(required = false) String filter)
        throws URISyntaxException {
        if ("multirelationaldisplayfieldentity-is-null".equals(filter)) {
            log.debug("REST request to get all OneToOneDisplayFieldEntitys where multiRelationalDisplayFieldEntity is null");
            return new ResponseEntity<>(StreamSupport
                .stream(oneToOneDisplayFieldEntityRepository.findAll().spliterator(), false)
                .filter(oneToOneDisplayFieldEntity -> oneToOneDisplayFieldEntity.getMultiRelationalDisplayFieldEntity() == null)
                .collect(Collectors.toList()), HttpStatus.OK);
        }
        
        Page<OneToOneDisplayFieldEntity> page = oneToOneDisplayFieldEntityRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/oneToOneDisplayFieldEntitys");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /oneToOneDisplayFieldEntitys/:id -> get the "id" oneToOneDisplayFieldEntity.
     */
    @RequestMapping(value = "/oneToOneDisplayFieldEntitys/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<OneToOneDisplayFieldEntity> getOneToOneDisplayFieldEntity(@PathVariable Long id) {
        log.debug("REST request to get OneToOneDisplayFieldEntity : {}", id);
        return Optional.ofNullable(oneToOneDisplayFieldEntityRepository.findOne(id))
            .map(oneToOneDisplayFieldEntity -> new ResponseEntity<>(
                oneToOneDisplayFieldEntity,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /oneToOneDisplayFieldEntitys/:id -> delete the "id" oneToOneDisplayFieldEntity.
     */
    @RequestMapping(value = "/oneToOneDisplayFieldEntitys/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteOneToOneDisplayFieldEntity(@PathVariable Long id) {
        log.debug("REST request to delete OneToOneDisplayFieldEntity : {}", id);
        oneToOneDisplayFieldEntityRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("oneToOneDisplayFieldEntity", id.toString())).build();
    }
}
