package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.ManyToManyDisplayFieldEntity;
import com.mycompany.myapp.repository.ManyToManyDisplayFieldEntityRepository;
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
 * REST controller for managing ManyToManyDisplayFieldEntity.
 */
@RestController
@RequestMapping("/api")
public class ManyToManyDisplayFieldEntityResource {

    private final Logger log = LoggerFactory.getLogger(ManyToManyDisplayFieldEntityResource.class);

    @Inject
    private ManyToManyDisplayFieldEntityRepository manyToManyDisplayFieldEntityRepository;

    /**
     * POST  /manyToManyDisplayFieldEntitys -> Create a new manyToManyDisplayFieldEntity.
     */
    @RequestMapping(value = "/manyToManyDisplayFieldEntitys",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ManyToManyDisplayFieldEntity> create(@RequestBody ManyToManyDisplayFieldEntity manyToManyDisplayFieldEntity) throws URISyntaxException {
        log.debug("REST request to save ManyToManyDisplayFieldEntity : {}", manyToManyDisplayFieldEntity);
        if (manyToManyDisplayFieldEntity.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new manyToManyDisplayFieldEntity cannot already have an ID").body(null);
        }
        ManyToManyDisplayFieldEntity result = manyToManyDisplayFieldEntityRepository.save(manyToManyDisplayFieldEntity);
        return ResponseEntity.created(new URI("/api/manyToManyDisplayFieldEntitys/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert("manyToManyDisplayFieldEntity", result.getId().toString()))
                .body(result);
    }

    /**
     * PUT  /manyToManyDisplayFieldEntitys -> Updates an existing manyToManyDisplayFieldEntity.
     */
    @RequestMapping(value = "/manyToManyDisplayFieldEntitys",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ManyToManyDisplayFieldEntity> update(@RequestBody ManyToManyDisplayFieldEntity manyToManyDisplayFieldEntity) throws URISyntaxException {
        log.debug("REST request to update ManyToManyDisplayFieldEntity : {}", manyToManyDisplayFieldEntity);
        if (manyToManyDisplayFieldEntity.getId() == null) {
            return create(manyToManyDisplayFieldEntity);
        }
        ManyToManyDisplayFieldEntity result = manyToManyDisplayFieldEntityRepository.save(manyToManyDisplayFieldEntity);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert("manyToManyDisplayFieldEntity", manyToManyDisplayFieldEntity.getId().toString()))
                .body(result);
    }

    /**
     * GET  /manyToManyDisplayFieldEntitys -> get all the manyToManyDisplayFieldEntitys.
     */
    @RequestMapping(value = "/manyToManyDisplayFieldEntitys",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<ManyToManyDisplayFieldEntity>> getAll(@RequestParam(value = "page" , required = false) Integer offset,
                                  @RequestParam(value = "per_page", required = false) Integer limit)
        throws URISyntaxException {
        Page<ManyToManyDisplayFieldEntity> page = manyToManyDisplayFieldEntityRepository.findAll(PaginationUtil.generatePageRequest(offset, limit));
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/manyToManyDisplayFieldEntitys", offset, limit);
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /manyToManyDisplayFieldEntitys/:id -> get the "id" manyToManyDisplayFieldEntity.
     */
    @RequestMapping(value = "/manyToManyDisplayFieldEntitys/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ManyToManyDisplayFieldEntity> get(@PathVariable Long id) {
        log.debug("REST request to get ManyToManyDisplayFieldEntity : {}", id);
        return Optional.ofNullable(manyToManyDisplayFieldEntityRepository.findOne(id))
            .map(manyToManyDisplayFieldEntity -> new ResponseEntity<>(
                manyToManyDisplayFieldEntity,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /manyToManyDisplayFieldEntitys/:id -> delete the "id" manyToManyDisplayFieldEntity.
     */
    @RequestMapping(value = "/manyToManyDisplayFieldEntitys/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        log.debug("REST request to delete ManyToManyDisplayFieldEntity : {}", id);
        manyToManyDisplayFieldEntityRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("manyToManyDisplayFieldEntity", id.toString())).build();
    }
}
