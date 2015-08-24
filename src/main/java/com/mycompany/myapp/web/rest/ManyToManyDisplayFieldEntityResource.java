package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.ManyToManyDisplayFieldEntity;
import com.mycompany.myapp.repository.ManyToManyDisplayFieldEntityRepository;
import com.mycompany.myapp.web.rest.util.HeaderUtil;
import com.mycompany.myapp.web.rest.util.PaginationUtil;
import com.mycompany.myapp.web.rest.dto.ManyToManyDisplayFieldEntityDTO;
import com.mycompany.myapp.web.rest.mapper.ManyToManyDisplayFieldEntityMapper;
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
 * REST controller for managing ManyToManyDisplayFieldEntity.
 */
@RestController
@RequestMapping("/api")
public class ManyToManyDisplayFieldEntityResource {

    private final Logger log = LoggerFactory.getLogger(ManyToManyDisplayFieldEntityResource.class);

    @Inject
    private ManyToManyDisplayFieldEntityRepository manyToManyDisplayFieldEntityRepository;

    @Inject
    private ManyToManyDisplayFieldEntityMapper manyToManyDisplayFieldEntityMapper;

    /**
     * POST  /manyToManyDisplayFieldEntitys -> Create a new manyToManyDisplayFieldEntity.
     */
    @RequestMapping(value = "/manyToManyDisplayFieldEntitys",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ManyToManyDisplayFieldEntityDTO> create(@RequestBody ManyToManyDisplayFieldEntityDTO manyToManyDisplayFieldEntityDTO) throws URISyntaxException {
        log.debug("REST request to save ManyToManyDisplayFieldEntity : {}", manyToManyDisplayFieldEntityDTO);
        if (manyToManyDisplayFieldEntityDTO.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new manyToManyDisplayFieldEntity cannot already have an ID").body(null);
        }
        ManyToManyDisplayFieldEntity manyToManyDisplayFieldEntity = manyToManyDisplayFieldEntityMapper.manyToManyDisplayFieldEntityDTOToManyToManyDisplayFieldEntity(manyToManyDisplayFieldEntityDTO);
        ManyToManyDisplayFieldEntity result = manyToManyDisplayFieldEntityRepository.save(manyToManyDisplayFieldEntity);
        return ResponseEntity.created(new URI("/api/manyToManyDisplayFieldEntitys/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert("manyToManyDisplayFieldEntity", result.getId().toString()))
                .body(manyToManyDisplayFieldEntityMapper.manyToManyDisplayFieldEntityToManyToManyDisplayFieldEntityDTO(result));
    }

    /**
     * PUT  /manyToManyDisplayFieldEntitys -> Updates an existing manyToManyDisplayFieldEntity.
     */
    @RequestMapping(value = "/manyToManyDisplayFieldEntitys",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ManyToManyDisplayFieldEntityDTO> update(@RequestBody ManyToManyDisplayFieldEntityDTO manyToManyDisplayFieldEntityDTO) throws URISyntaxException {
        log.debug("REST request to update ManyToManyDisplayFieldEntity : {}", manyToManyDisplayFieldEntityDTO);
        if (manyToManyDisplayFieldEntityDTO.getId() == null) {
            return create(manyToManyDisplayFieldEntityDTO);
        }
        ManyToManyDisplayFieldEntity manyToManyDisplayFieldEntity = manyToManyDisplayFieldEntityMapper.manyToManyDisplayFieldEntityDTOToManyToManyDisplayFieldEntity(manyToManyDisplayFieldEntityDTO);
        ManyToManyDisplayFieldEntity result = manyToManyDisplayFieldEntityRepository.save(manyToManyDisplayFieldEntity);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert("manyToManyDisplayFieldEntity", manyToManyDisplayFieldEntityDTO.getId().toString()))
                .body(manyToManyDisplayFieldEntityMapper.manyToManyDisplayFieldEntityToManyToManyDisplayFieldEntityDTO(result));
    }

    /**
     * GET  /manyToManyDisplayFieldEntitys -> get all the manyToManyDisplayFieldEntitys.
     */
    @RequestMapping(value = "/manyToManyDisplayFieldEntitys",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Transactional(readOnly = true)
    public ResponseEntity<List<ManyToManyDisplayFieldEntityDTO>> getAll(@RequestParam(value = "page" , required = false) Integer offset,
                                  @RequestParam(value = "per_page", required = false) Integer limit)
        throws URISyntaxException {
        Page<ManyToManyDisplayFieldEntity> page = manyToManyDisplayFieldEntityRepository.findAll(PaginationUtil.generatePageRequest(offset, limit));
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/manyToManyDisplayFieldEntitys", offset, limit);
        return new ResponseEntity<>(page.getContent().stream()
            .map(manyToManyDisplayFieldEntityMapper::manyToManyDisplayFieldEntityToManyToManyDisplayFieldEntityDTO)
            .collect(Collectors.toCollection(LinkedList::new)), headers, HttpStatus.OK);
    }

    /**
     * GET  /manyToManyDisplayFieldEntitys/:id -> get the "id" manyToManyDisplayFieldEntity.
     */
    @RequestMapping(value = "/manyToManyDisplayFieldEntitys/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ManyToManyDisplayFieldEntityDTO> get(@PathVariable Long id) {
        log.debug("REST request to get ManyToManyDisplayFieldEntity : {}", id);
        return Optional.ofNullable(manyToManyDisplayFieldEntityRepository.findOne(id))
            .map(manyToManyDisplayFieldEntityMapper::manyToManyDisplayFieldEntityToManyToManyDisplayFieldEntityDTO)
            .map(manyToManyDisplayFieldEntityDTO -> new ResponseEntity<>(
                manyToManyDisplayFieldEntityDTO,
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
