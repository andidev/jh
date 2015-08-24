package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.MultiRelationalEntity;
import com.mycompany.myapp.repository.MultiRelationalEntityRepository;
import com.mycompany.myapp.web.rest.util.HeaderUtil;
import com.mycompany.myapp.web.rest.util.PaginationUtil;
import com.mycompany.myapp.web.rest.dto.MultiRelationalEntityDTO;
import com.mycompany.myapp.web.rest.mapper.MultiRelationalEntityMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * REST controller for managing MultiRelationalEntity.
 */
@RestController
@RequestMapping("/api")
public class MultiRelationalEntityResource {

    private final Logger log = LoggerFactory.getLogger(MultiRelationalEntityResource.class);

    @Inject
    private MultiRelationalEntityRepository multiRelationalEntityRepository;

    @Inject
    private MultiRelationalEntityMapper multiRelationalEntityMapper;

    /**
     * POST  /multiRelationalEntitys -> Create a new multiRelationalEntity.
     */
    @RequestMapping(value = "/multiRelationalEntitys",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<MultiRelationalEntityDTO> create(@RequestBody MultiRelationalEntityDTO multiRelationalEntityDTO) throws URISyntaxException {
        log.debug("REST request to save MultiRelationalEntity : {}", multiRelationalEntityDTO);
        if (multiRelationalEntityDTO.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new multiRelationalEntity cannot already have an ID").body(null);
        }
        MultiRelationalEntity multiRelationalEntity = multiRelationalEntityMapper.multiRelationalEntityDTOToMultiRelationalEntity(multiRelationalEntityDTO);
        MultiRelationalEntity result = multiRelationalEntityRepository.save(multiRelationalEntity);
        return ResponseEntity.created(new URI("/api/multiRelationalEntitys/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert("multiRelationalEntity", result.getId().toString()))
                .body(multiRelationalEntityMapper.multiRelationalEntityToMultiRelationalEntityDTO(result));
    }

    /**
     * PUT  /multiRelationalEntitys -> Updates an existing multiRelationalEntity.
     */
    @RequestMapping(value = "/multiRelationalEntitys",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<MultiRelationalEntityDTO> update(@RequestBody MultiRelationalEntityDTO multiRelationalEntityDTO) throws URISyntaxException {
        log.debug("REST request to update MultiRelationalEntity : {}", multiRelationalEntityDTO);
        if (multiRelationalEntityDTO.getId() == null) {
            return create(multiRelationalEntityDTO);
        }
        MultiRelationalEntity multiRelationalEntity = multiRelationalEntityMapper.multiRelationalEntityDTOToMultiRelationalEntity(multiRelationalEntityDTO);
        MultiRelationalEntity result = multiRelationalEntityRepository.save(multiRelationalEntity);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert("multiRelationalEntity", multiRelationalEntityDTO.getId().toString()))
                .body(multiRelationalEntityMapper.multiRelationalEntityToMultiRelationalEntityDTO(result));
    }

    /**
     * GET  /multiRelationalEntitys -> get all the multiRelationalEntitys.
     */
    @RequestMapping(value = "/multiRelationalEntitys",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Transactional(readOnly = true)
    public ResponseEntity<List<MultiRelationalEntityDTO>> getAll(@RequestParam(value = "page" , required = false) Integer offset,
                                  @RequestParam(value = "per_page", required = false) Integer limit)
        throws URISyntaxException {
        Page<MultiRelationalEntity> page = multiRelationalEntityRepository.findAll(PaginationUtil.generatePageRequest(offset, limit));
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/multiRelationalEntitys", offset, limit);
        return new ResponseEntity<>(page.getContent().stream()
            .map(multiRelationalEntityMapper::multiRelationalEntityToMultiRelationalEntityDTO)
            .collect(Collectors.toCollection(LinkedList::new)), headers, HttpStatus.OK);
    }

    /**
     * GET  /multiRelationalEntitys/:id -> get the "id" multiRelationalEntity.
     */
    @RequestMapping(value = "/multiRelationalEntitys/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<MultiRelationalEntityDTO> get(@PathVariable Long id) {
        log.debug("REST request to get MultiRelationalEntity : {}", id);
        return Optional.ofNullable(multiRelationalEntityRepository.findOneWithEagerRelationships(id))
            .map(multiRelationalEntityMapper::multiRelationalEntityToMultiRelationalEntityDTO)
            .map(multiRelationalEntityDTO -> new ResponseEntity<>(
                multiRelationalEntityDTO,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /multiRelationalEntitys/:id -> delete the "id" multiRelationalEntity.
     */
    @RequestMapping(value = "/multiRelationalEntitys/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        log.debug("REST request to delete MultiRelationalEntity : {}", id);
        multiRelationalEntityRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("multiRelationalEntity", id.toString())).build();
    }
}
