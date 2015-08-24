package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.OneToOneEntity;
import com.mycompany.myapp.repository.OneToOneEntityRepository;
import com.mycompany.myapp.web.rest.util.HeaderUtil;
import com.mycompany.myapp.web.rest.util.PaginationUtil;
import com.mycompany.myapp.web.rest.dto.OneToOneEntityDTO;
import com.mycompany.myapp.web.rest.mapper.OneToOneEntityMapper;
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
import java.util.stream.StreamSupport;

/**
 * REST controller for managing OneToOneEntity.
 */
@RestController
@RequestMapping("/api")
public class OneToOneEntityResource {

    private final Logger log = LoggerFactory.getLogger(OneToOneEntityResource.class);

    @Inject
    private OneToOneEntityRepository oneToOneEntityRepository;

    @Inject
    private OneToOneEntityMapper oneToOneEntityMapper;

    /**
     * POST  /oneToOneEntitys -> Create a new oneToOneEntity.
     */
    @RequestMapping(value = "/oneToOneEntitys",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<OneToOneEntityDTO> create(@RequestBody OneToOneEntityDTO oneToOneEntityDTO) throws URISyntaxException {
        log.debug("REST request to save OneToOneEntity : {}", oneToOneEntityDTO);
        if (oneToOneEntityDTO.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new oneToOneEntity cannot already have an ID").body(null);
        }
        OneToOneEntity oneToOneEntity = oneToOneEntityMapper.oneToOneEntityDTOToOneToOneEntity(oneToOneEntityDTO);
        OneToOneEntity result = oneToOneEntityRepository.save(oneToOneEntity);
        return ResponseEntity.created(new URI("/api/oneToOneEntitys/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert("oneToOneEntity", result.getId().toString()))
                .body(oneToOneEntityMapper.oneToOneEntityToOneToOneEntityDTO(result));
    }

    /**
     * PUT  /oneToOneEntitys -> Updates an existing oneToOneEntity.
     */
    @RequestMapping(value = "/oneToOneEntitys",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<OneToOneEntityDTO> update(@RequestBody OneToOneEntityDTO oneToOneEntityDTO) throws URISyntaxException {
        log.debug("REST request to update OneToOneEntity : {}", oneToOneEntityDTO);
        if (oneToOneEntityDTO.getId() == null) {
            return create(oneToOneEntityDTO);
        }
        OneToOneEntity oneToOneEntity = oneToOneEntityMapper.oneToOneEntityDTOToOneToOneEntity(oneToOneEntityDTO);
        OneToOneEntity result = oneToOneEntityRepository.save(oneToOneEntity);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert("oneToOneEntity", oneToOneEntityDTO.getId().toString()))
                .body(oneToOneEntityMapper.oneToOneEntityToOneToOneEntityDTO(result));
    }

    /**
     * GET  /oneToOneEntitys -> get all the oneToOneEntitys.
     */
    @RequestMapping(value = "/oneToOneEntitys",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Transactional(readOnly = true)
    public ResponseEntity<List<OneToOneEntityDTO>> getAll(@RequestParam(value = "page" , required = false) Integer offset,
                                  @RequestParam(value = "per_page", required = false) Integer limit, @RequestParam(required = false) String filter)
        throws URISyntaxException {
        if ("multirelationalentity-is-null".equals(filter)) {
            log.debug("REST request to get all OneToOneEntitys where multiRelationalEntity is null");
            return new ResponseEntity<>(StreamSupport
                .stream(oneToOneEntityRepository.findAll().spliterator(), false)
                .filter(oneToOneEntity -> oneToOneEntity.getMultiRelationalEntity() == null)
                .map(oneToOneEntity -> oneToOneEntityMapper.oneToOneEntityToOneToOneEntityDTO(oneToOneEntity))
                .collect(Collectors.toList()), HttpStatus.OK);
        }
        
        Page<OneToOneEntity> page = oneToOneEntityRepository.findAll(PaginationUtil.generatePageRequest(offset, limit));
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/oneToOneEntitys", offset, limit);
        return new ResponseEntity<>(page.getContent().stream()
            .map(oneToOneEntityMapper::oneToOneEntityToOneToOneEntityDTO)
            .collect(Collectors.toCollection(LinkedList::new)), headers, HttpStatus.OK);
    }

    /**
     * GET  /oneToOneEntitys/:id -> get the "id" oneToOneEntity.
     */
    @RequestMapping(value = "/oneToOneEntitys/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<OneToOneEntityDTO> get(@PathVariable Long id) {
        log.debug("REST request to get OneToOneEntity : {}", id);
        return Optional.ofNullable(oneToOneEntityRepository.findOne(id))
            .map(oneToOneEntityMapper::oneToOneEntityToOneToOneEntityDTO)
            .map(oneToOneEntityDTO -> new ResponseEntity<>(
                oneToOneEntityDTO,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /oneToOneEntitys/:id -> delete the "id" oneToOneEntity.
     */
    @RequestMapping(value = "/oneToOneEntitys/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        log.debug("REST request to delete OneToOneEntity : {}", id);
        oneToOneEntityRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("oneToOneEntity", id.toString())).build();
    }
}
