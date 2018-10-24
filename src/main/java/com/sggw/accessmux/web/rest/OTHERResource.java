package com.sggw.accessmux.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.sggw.accessmux.service.OTHERService;
import com.sggw.accessmux.web.rest.errors.BadRequestAlertException;
import com.sggw.accessmux.web.rest.util.HeaderUtil;
import com.sggw.accessmux.service.dto.OTHERDTO;
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
 * REST controller for managing OTHER.
 */
@RestController
@RequestMapping("/api")
public class OTHERResource {

    private final Logger log = LoggerFactory.getLogger(OTHERResource.class);

    private static final String ENTITY_NAME = "oTHER";

    private final OTHERService oTHERService;

    public OTHERResource(OTHERService oTHERService) {
        this.oTHERService = oTHERService;
    }

    /**
     * POST  /others : Create a new oTHER.
     *
     * @param oTHERDTO the oTHERDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new oTHERDTO, or with status 400 (Bad Request) if the oTHER has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/others")
    @Timed
    public ResponseEntity<OTHERDTO> createOTHER(@Valid @RequestBody OTHERDTO oTHERDTO) throws URISyntaxException {
        log.debug("REST request to save OTHER : {}", oTHERDTO);
        if (oTHERDTO.getId() != null) {
            throw new BadRequestAlertException("A new oTHER cannot already have an ID", ENTITY_NAME, "idexists");
        }
        OTHERDTO result = oTHERService.save(oTHERDTO);
        return ResponseEntity.created(new URI("/api/others/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /others : Updates an existing oTHER.
     *
     * @param oTHERDTO the oTHERDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated oTHERDTO,
     * or with status 400 (Bad Request) if the oTHERDTO is not valid,
     * or with status 500 (Internal Server Error) if the oTHERDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/others")
    @Timed
    public ResponseEntity<OTHERDTO> updateOTHER(@Valid @RequestBody OTHERDTO oTHERDTO) throws URISyntaxException {
        log.debug("REST request to update OTHER : {}", oTHERDTO);
        if (oTHERDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        OTHERDTO result = oTHERService.save(oTHERDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, oTHERDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /others : get all the oTHERS.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of oTHERS in body
     */
    @GetMapping("/others")
    @Timed
    public List<OTHERDTO> getAllOTHERS() {
        log.debug("REST request to get all OTHERS");
        return oTHERService.findAll();
    }

    /**
     * GET  /others/:id : get the "id" oTHER.
     *
     * @param id the id of the oTHERDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the oTHERDTO, or with status 404 (Not Found)
     */
    @GetMapping("/others/{id}")
    @Timed
    public ResponseEntity<OTHERDTO> getOTHER(@PathVariable Long id) {
        log.debug("REST request to get OTHER : {}", id);
        Optional<OTHERDTO> oTHERDTO = oTHERService.findOne(id);
        return ResponseUtil.wrapOrNotFound(oTHERDTO);
    }

    /**
     * DELETE  /others/:id : delete the "id" oTHER.
     *
     * @param id the id of the oTHERDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/others/{id}")
    @Timed
    public ResponseEntity<Void> deleteOTHER(@PathVariable Long id) {
        log.debug("REST request to delete OTHER : {}", id);
        oTHERService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
