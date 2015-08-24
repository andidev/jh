package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.ManyToManyEntity;
import com.mycompany.myapp.repository.ManyToManyEntityRepository;
import com.mycompany.myapp.web.rest.util.HeaderUtil;
import com.mycompany.myapp.web.rest.util.PaginationUtil;
import com.mycompany.myapp.web.rest.dto.ManyToManyEntityDTO;
import com.mycompany.myapp.web.rest.mapper.ManyToManyEntityMapper;
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
 * REST controller for managing ManyToManyEntity.
 */
@RestController
@RequestMapping("/api")
public class ManyToManyEntityResource {

    private final Logger log = LoggerFactory.getLogger(ManyToManyEntityResource.class);

    @Inject
    private ManyToManyEntityRepository manyToManyEntityRepository;

    @Inject
    private ManyToManyEntityMapper manyToManyEntityMapper;

    /**
     * POST  /manyToManyEntitys -> Create a new manyToManyEntity.
     */
    @RequestMapping(value = "/manyToManyEntitys",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ManyToManyEntityDTO> create(@RequestBody ManyToManyEntityDTO manyToManyEntityDTO) throws URISyntaxException {
        log.debug("REST request to save ManyToManyEntity : {}", manyToManyEntityDTO);
        if (manyToManyEntityDTO.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new manyToManyEntity cannot already have an ID").body(null);
        }
        ManyToManyEntity manyToManyEntity = manyToManyEntityMapper.manyToManyEntityDTOToManyToManyEntity(manyToManyEntityDTO);
        ManyToManyEntity result = manyToManyEntityRepository.save(manyToManyEntity);
        return ResponseEntity.created(new URI("/api/manyToManyEntitys/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert("manyToManyEntity", result.getId().toString()))
                .body(manyToManyEntityMapper.manyToManyEntityToManyToManyEntityDTO(result));
    }

    /**
     * PUT  /manyToManyEntitys -> Updates an existing manyToManyEntity.
     */
    @RequestMapping(value = "/manyToManyEntitys",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ManyToManyEntityDTO> update(@RequestBody ManyToManyEntityDTO manyToManyEntityDTO) throws URISyntaxException {
        log.debug("REST request to update ManyToManyEntity : {}", manyToManyEntityDTO);
        if (manyToManyEntityDTO.getId() == null) {
            return create(manyToManyEntityDTO);
        }
        ManyToManyEntity manyToManyEntity = manyToManyEntityMapper.manyToManyEntityDTOToManyToManyEntity(manyToManyEntityDTO);
        ManyToManyEntity result = manyToManyEntityRepository.save(manyToManyEntity);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert("manyToManyEntity", manyToManyEntityDTO.getId().toString()))
                .body(manyToManyEntityMapper.manyToManyEntityToManyToManyEntityDTO(result));
    }

    /**
     * GET  /manyToManyEntitys -> get all the manyToManyEntitys.
     */
    @RequestMapping(value = "/manyToManyEntitys",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Transactional(readOnly = true)
    public ResponseEntity<List<ManyToManyEntityDTO>> getAll(@RequestParam(value = "page" , required = false) Integer offset,
                                  @RequestParam(value = "per_page", required = false) Integer limit)
        throws URISyntaxException {
        Page<ManyToManyEntity> page = manyToManyEntityRepository.findAll(PaginationUtil.generatePageRequest(offset, limit));
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/manyToManyEntitys", offset, limit);
        return new ResponseEntity<>(page.getContent().stream()
            .map(manyToManyEntityMapper::manyToManyEntityToManyToManyEntityDTO)
            .collect(Collectors.toCollection(LinkedList::new)), headers, HttpStatus.OK);
    }

    /**
     * GET  /manyToManyEntitys/:id -> get the "id" manyToManyEntity.
     */
    @RequestMapping(value = "/manyToManyEntitys/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ManyToManyEntityDTO> get(@PathVariable Long id) {
        log.debug("REST request to get ManyToManyEntity : {}", id);
        return Optional.ofNullable(manyToManyEntityRepository.findOne(id))
            .map(manyToManyEntityMapper::manyToManyEntityToManyToManyEntityDTO)
            .map(manyToManyEntityDTO -> new ResponseEntity<>(
                manyToManyEntityDTO,
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
