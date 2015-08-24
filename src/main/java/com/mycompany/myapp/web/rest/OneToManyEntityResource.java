package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.OneToManyEntity;
import com.mycompany.myapp.repository.OneToManyEntityRepository;
import com.mycompany.myapp.web.rest.util.HeaderUtil;
import com.mycompany.myapp.web.rest.util.PaginationUtil;
import com.mycompany.myapp.web.rest.dto.OneToManyEntityDTO;
import com.mycompany.myapp.web.rest.mapper.OneToManyEntityMapper;
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
 * REST controller for managing OneToManyEntity.
 */
@RestController
@RequestMapping("/api")
public class OneToManyEntityResource {

    private final Logger log = LoggerFactory.getLogger(OneToManyEntityResource.class);

    @Inject
    private OneToManyEntityRepository oneToManyEntityRepository;

    @Inject
    private OneToManyEntityMapper oneToManyEntityMapper;

    /**
     * POST  /oneToManyEntitys -> Create a new oneToManyEntity.
     */
    @RequestMapping(value = "/oneToManyEntitys",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<OneToManyEntityDTO> create(@RequestBody OneToManyEntityDTO oneToManyEntityDTO) throws URISyntaxException {
        log.debug("REST request to save OneToManyEntity : {}", oneToManyEntityDTO);
        if (oneToManyEntityDTO.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new oneToManyEntity cannot already have an ID").body(null);
        }
        OneToManyEntity oneToManyEntity = oneToManyEntityMapper.oneToManyEntityDTOToOneToManyEntity(oneToManyEntityDTO);
        OneToManyEntity result = oneToManyEntityRepository.save(oneToManyEntity);
        return ResponseEntity.created(new URI("/api/oneToManyEntitys/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert("oneToManyEntity", result.getId().toString()))
                .body(oneToManyEntityMapper.oneToManyEntityToOneToManyEntityDTO(result));
    }

    /**
     * PUT  /oneToManyEntitys -> Updates an existing oneToManyEntity.
     */
    @RequestMapping(value = "/oneToManyEntitys",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<OneToManyEntityDTO> update(@RequestBody OneToManyEntityDTO oneToManyEntityDTO) throws URISyntaxException {
        log.debug("REST request to update OneToManyEntity : {}", oneToManyEntityDTO);
        if (oneToManyEntityDTO.getId() == null) {
            return create(oneToManyEntityDTO);
        }
        OneToManyEntity oneToManyEntity = oneToManyEntityMapper.oneToManyEntityDTOToOneToManyEntity(oneToManyEntityDTO);
        OneToManyEntity result = oneToManyEntityRepository.save(oneToManyEntity);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert("oneToManyEntity", oneToManyEntityDTO.getId().toString()))
                .body(oneToManyEntityMapper.oneToManyEntityToOneToManyEntityDTO(result));
    }

    /**
     * GET  /oneToManyEntitys -> get all the oneToManyEntitys.
     */
    @RequestMapping(value = "/oneToManyEntitys",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Transactional(readOnly = true)
    public ResponseEntity<List<OneToManyEntityDTO>> getAll(@RequestParam(value = "page" , required = false) Integer offset,
                                  @RequestParam(value = "per_page", required = false) Integer limit)
        throws URISyntaxException {
        Page<OneToManyEntity> page = oneToManyEntityRepository.findAll(PaginationUtil.generatePageRequest(offset, limit));
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/oneToManyEntitys", offset, limit);
        return new ResponseEntity<>(page.getContent().stream()
            .map(oneToManyEntityMapper::oneToManyEntityToOneToManyEntityDTO)
            .collect(Collectors.toCollection(LinkedList::new)), headers, HttpStatus.OK);
    }

    /**
     * GET  /oneToManyEntitys/:id -> get the "id" oneToManyEntity.
     */
    @RequestMapping(value = "/oneToManyEntitys/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<OneToManyEntityDTO> get(@PathVariable Long id) {
        log.debug("REST request to get OneToManyEntity : {}", id);
        return Optional.ofNullable(oneToManyEntityRepository.findOne(id))
            .map(oneToManyEntityMapper::oneToManyEntityToOneToManyEntityDTO)
            .map(oneToManyEntityDTO -> new ResponseEntity<>(
                oneToManyEntityDTO,
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
