package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.ManyToManyEntity;
import com.mycompany.myapp.repository.ManyToManyEntityRepository;
import com.mycompany.myapp.web.rest.util.HeaderUtil;
import com.mycompany.myapp.web.rest.util.PaginationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
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
 * REST controller for managing ManyToManyEntity.
 */
@RestController
@RequestMapping("/api")
public class ManyToManyEntityResource {

    private final Logger log = LoggerFactory.getLogger(ManyToManyEntityResource.class);

    @Inject
    private ManyToManyEntityRepository manyToManyEntityRepository;

    /**
     * POST  /manyToManyEntitys -> Create a new manyToManyEntity.
     */
    @RequestMapping(value = "/manyToManyEntitys",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ManyToManyEntity> create(@RequestBody ManyToManyEntity manyToManyEntity) throws URISyntaxException {
        log.debug("REST request to save ManyToManyEntity : {}", manyToManyEntity);
        if (manyToManyEntity.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new manyToManyEntity cannot already have an ID").body(null);
        }
        ManyToManyEntity result = manyToManyEntityRepository.save(manyToManyEntity);
        return ResponseEntity.created(new URI("/api/manyToManyEntitys/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert("manyToManyEntity", result.getId().toString()))
                .body(result);
    }

    /**
     * PUT  /manyToManyEntitys -> Updates an existing manyToManyEntity.
     */
    @RequestMapping(value = "/manyToManyEntitys",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ManyToManyEntity> update(@RequestBody ManyToManyEntity manyToManyEntity) throws URISyntaxException {
        log.debug("REST request to update ManyToManyEntity : {}", manyToManyEntity);
        if (manyToManyEntity.getId() == null) {
            return create(manyToManyEntity);
        }
        ManyToManyEntity result = manyToManyEntityRepository.save(manyToManyEntity);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert("manyToManyEntity", manyToManyEntity.getId().toString()))
                .body(result);
    }

    /**
     * GET  /manyToManyEntitys -> get all the manyToManyEntitys.
     */
    @RequestMapping(value = "/manyToManyEntitys",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<ManyToManyEntity>> getAll(@RequestParam(value = "page" , required = false) Integer offset,
                                  @RequestParam(value = "per_page", required = false) Integer limit)
        throws URISyntaxException {
        Page<ManyToManyEntity> page = manyToManyEntityRepository.findAll(PaginationUtil.generatePageRequest(offset, limit));
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/manyToManyEntitys", offset, limit);
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /manyToManyEntitys/:id -> get the "id" manyToManyEntity.
     */
    @RequestMapping(value = "/manyToManyEntitys/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ManyToManyEntity> get(@PathVariable Long id) {
        log.debug("REST request to get ManyToManyEntity : {}", id);
        return Optional.ofNullable(manyToManyEntityRepository.findOneWithEagerRelationships(id))
            .map(manyToManyEntity -> new ResponseEntity<>(
                manyToManyEntity,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /manyToManyEntitys/:id -> delete the "id" manyToManyEntity.
     */
    @RequestMapping(value = "/manyToManyEntitys/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        log.debug("REST request to delete ManyToManyEntity : {}", id);
        manyToManyEntityRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("manyToManyEntity", id.toString())).build();
    }
}
