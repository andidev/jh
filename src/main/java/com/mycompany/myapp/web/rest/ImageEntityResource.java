package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.ImageEntity;
import com.mycompany.myapp.repository.ImageEntityRepository;
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
 * REST controller for managing ImageEntity.
 */
@RestController
@RequestMapping("/api")
public class ImageEntityResource {

    private final Logger log = LoggerFactory.getLogger(ImageEntityResource.class);

    @Inject
    private ImageEntityRepository imageEntityRepository;

    /**
     * POST  /imageEntitys -> Create a new imageEntity.
     */
    @RequestMapping(value = "/imageEntitys",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ImageEntity> create(@Valid @RequestBody ImageEntity imageEntity) throws URISyntaxException {
        log.debug("REST request to save ImageEntity : {}", imageEntity);
        if (imageEntity.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new imageEntity cannot already have an ID").body(null);
        }
        ImageEntity result = imageEntityRepository.save(imageEntity);
        return ResponseEntity.created(new URI("/api/imageEntitys/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert("imageEntity", result.getId().toString()))
                .body(result);
    }

    /**
     * PUT  /imageEntitys -> Updates an existing imageEntity.
     */
    @RequestMapping(value = "/imageEntitys",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ImageEntity> update(@Valid @RequestBody ImageEntity imageEntity) throws URISyntaxException {
        log.debug("REST request to update ImageEntity : {}", imageEntity);
        if (imageEntity.getId() == null) {
            return create(imageEntity);
        }
        ImageEntity result = imageEntityRepository.save(imageEntity);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert("imageEntity", imageEntity.getId().toString()))
                .body(result);
    }

    /**
     * GET  /imageEntitys -> get all the imageEntitys.
     */
    @RequestMapping(value = "/imageEntitys",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<ImageEntity> getAll() {
        log.debug("REST request to get all ImageEntitys");
        return imageEntityRepository.findAll();
    }

    /**
     * GET  /imageEntitys/:id -> get the "id" imageEntity.
     */
    @RequestMapping(value = "/imageEntitys/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ImageEntity> get(@PathVariable Long id) {
        log.debug("REST request to get ImageEntity : {}", id);
        return Optional.ofNullable(imageEntityRepository.findOne(id))
            .map(imageEntity -> new ResponseEntity<>(
                imageEntity,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /imageEntitys/:id -> delete the "id" imageEntity.
     */
    @RequestMapping(value = "/imageEntitys/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        log.debug("REST request to delete ImageEntity : {}", id);
        imageEntityRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("imageEntity", id.toString())).build();
    }
}
