package com.sggw.accessmux.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.sggw.accessmux.service.ACTIVITYService;
import com.sggw.accessmux.web.rest.errors.BadRequestAlertException;
import com.sggw.accessmux.web.rest.util.HeaderUtil;
import com.sggw.accessmux.service.dto.ACTIVITYDTO;
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
 * REST controller for managing ACTIVITY.
 */
@RestController
@RequestMapping("/api")
public class ACTIVITYResource {

    private final Logger log = LoggerFactory.getLogger(ACTIVITYResource.class);

    private static final String ENTITY_NAME = "aCTIVITY";

    private final ACTIVITYService aCTIVITYService;

    public ACTIVITYResource(ACTIVITYService aCTIVITYService) {
        this.aCTIVITYService = aCTIVITYService;
    }

    /**
     * POST  /activities : Create a new aCTIVITY.
     *
     * @param aCTIVITYDTO the aCTIVITYDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new aCTIVITYDTO, or with status 400 (Bad Request) if the aCTIVITY has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/activities")
    @Timed
    public ResponseEntity<ACTIVITYDTO> createACTIVITY(@Valid @RequestBody ACTIVITYDTO aCTIVITYDTO) throws URISyntaxException {
        log.debug("REST request to save ACTIVITY : {}", aCTIVITYDTO);
        if (aCTIVITYDTO.getId() != null) {
            throw new BadRequestAlertException("A new aCTIVITY cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ACTIVITYDTO result = aCTIVITYService.save(aCTIVITYDTO);
        return ResponseEntity.created(new URI("/api/activities/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /activities : Updates an existing aCTIVITY.
     *
     * @param aCTIVITYDTO the aCTIVITYDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated aCTIVITYDTO,
     * or with status 400 (Bad Request) if the aCTIVITYDTO is not valid,
     * or with status 500 (Internal Server Error) if the aCTIVITYDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/activities")
    @Timed
    public ResponseEntity<ACTIVITYDTO> updateACTIVITY(@Valid @RequestBody ACTIVITYDTO aCTIVITYDTO) throws URISyntaxException {
        log.debug("REST request to update ACTIVITY : {}", aCTIVITYDTO);
        if (aCTIVITYDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ACTIVITYDTO result = aCTIVITYService.save(aCTIVITYDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, aCTIVITYDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /activities : get all the aCTIVITIES.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of aCTIVITIES in body
     */
    @GetMapping("/activities")
    @Timed
    public List<ACTIVITYDTO> getAllACTIVITIES() {
        log.debug("REST request to get all ACTIVITIES");
        return aCTIVITYService.findAll();
    }

    /**
     * GET  /activities/:id : get the "id" aCTIVITY.
     *
     * @param id the id of the aCTIVITYDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the aCTIVITYDTO, or with status 404 (Not Found)
     */
    @GetMapping("/activities/{id}")
    @Timed
    public ResponseEntity<ACTIVITYDTO> getACTIVITY(@PathVariable Long id) {
        log.debug("REST request to get ACTIVITY : {}", id);
        Optional<ACTIVITYDTO> aCTIVITYDTO = aCTIVITYService.findOne(id);
        return ResponseUtil.wrapOrNotFound(aCTIVITYDTO);
    }

    /**
     * DELETE  /activities/:id : delete the "id" aCTIVITY.
     *
     * @param id the id of the aCTIVITYDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/activities/{id}")
    @Timed
    public ResponseEntity<Void> deleteACTIVITY(@PathVariable Long id) {
        log.debug("REST request to delete ACTIVITY : {}", id);
        aCTIVITYService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
