package com.sggw.accessmux.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.sggw.accessmux.service.ITService;
import com.sggw.accessmux.web.rest.errors.BadRequestAlertException;
import com.sggw.accessmux.web.rest.util.HeaderUtil;
import com.sggw.accessmux.service.dto.ITDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing IT.
 */
@RestController
@RequestMapping("/api")
public class ITResource {

    private final Logger log = LoggerFactory.getLogger(ITResource.class);

    private static final String ENTITY_NAME = "iT";

    private final ITService iTService;

    public ITResource(ITService iTService) {
        this.iTService = iTService;
    }

    /**
     * POST  /its : Create a new iT.
     *
     * @param iTDTO the iTDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new iTDTO, or with status 400 (Bad Request) if the iT has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/its")
    @Timed
    public ResponseEntity<ITDTO> createIT(@Valid @RequestBody ITDTO iTDTO) throws URISyntaxException {
        log.debug("REST request to save IT : {}", iTDTO);
        if (iTDTO.getId() != null) {
            throw new BadRequestAlertException("A new iT cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ITDTO result = iTService.save(iTDTO);
        return ResponseEntity.created(new URI("/api/its/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /its : Updates an existing iT.
     *
     * @param iTDTO the iTDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated iTDTO,
     * or with status 400 (Bad Request) if the iTDTO is not valid,
     * or with status 500 (Internal Server Error) if the iTDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/its")
    @Timed
    public ResponseEntity<ITDTO> updateIT(@Valid @RequestBody ITDTO iTDTO) throws URISyntaxException {
        log.debug("REST request to update IT : {}", iTDTO);
        if (iTDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ITDTO result = iTService.save(iTDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, iTDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /its : get all the iTS.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of iTS in body
     */
    @GetMapping("/its")
    @Timed
    public List<ITDTO> getAllITS() {
        log.debug("REST request to get all ITS");
        return iTService.findAll();
    }

    /**
     * GET  /its/:id : get the "id" iT.
     *
     * @param id the id of the iTDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the iTDTO, or with status 404 (Not Found)
     */
    @GetMapping("/its/{id}")
    @Timed
    public ResponseEntity<ITDTO> getIT(@PathVariable Long id) {
        log.debug("REST request to get IT : {}", id);
        Optional<ITDTO> iTDTO = iTService.findOne(id);
        return ResponseUtil.wrapOrNotFound(iTDTO);
    }

    /**
     * DELETE  /its/:id : delete the "id" iT.
     *
     * @param id the id of the iTDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/its/{id}")
    @Timed
    public ResponseEntity<Void> deleteIT(@PathVariable Long id) {
        log.debug("REST request to delete IT : {}", id);
        iTService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
