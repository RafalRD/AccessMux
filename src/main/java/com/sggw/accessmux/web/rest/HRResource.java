package com.sggw.accessmux.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.sggw.accessmux.service.HRService;
import com.sggw.accessmux.web.rest.errors.BadRequestAlertException;
import com.sggw.accessmux.web.rest.util.HeaderUtil;
import com.sggw.accessmux.service.dto.HRDTO;
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
 * REST controller for managing HR.
 */
@RestController
@RequestMapping("/api")
public class HRResource {

    private final Logger log = LoggerFactory.getLogger(HRResource.class);

    private static final String ENTITY_NAME = "hR";

    private final HRService hRService;

    public HRResource(HRService hRService) {
        this.hRService = hRService;
    }

    /**
     * POST  /hrs : Create a new hR.
     *
     * @param hRDTO the hRDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new hRDTO, or with status 400 (Bad Request) if the hR has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/hrs")
    @Timed
    public ResponseEntity<HRDTO> createHR(@Valid @RequestBody HRDTO hRDTO) throws URISyntaxException {
        log.debug("REST request to save HR : {}", hRDTO);
        if (hRDTO.getId() != null) {
            throw new BadRequestAlertException("A new hR cannot already have an ID", ENTITY_NAME, "idexists");
        }
        HRDTO result = hRService.save(hRDTO);
        return ResponseEntity.created(new URI("/api/hrs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /hrs : Updates an existing hR.
     *
     * @param hRDTO the hRDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated hRDTO,
     * or with status 400 (Bad Request) if the hRDTO is not valid,
     * or with status 500 (Internal Server Error) if the hRDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/hrs")
    @Timed
    public ResponseEntity<HRDTO> updateHR(@Valid @RequestBody HRDTO hRDTO) throws URISyntaxException {
        log.debug("REST request to update HR : {}", hRDTO);
        if (hRDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        HRDTO result = hRService.save(hRDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, hRDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /hrs : get all the hRS.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of hRS in body
     */
    @GetMapping("/hrs")
    @Timed
    public List<HRDTO> getAllHRS() {
        log.debug("REST request to get all HRS");
        return hRService.findAll();
    }

    /**
     * GET  /hrs/:id : get the "id" hR.
     *
     * @param id the id of the hRDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the hRDTO, or with status 404 (Not Found)
     */
    @GetMapping("/hrs/{id}")
    @Timed
    public ResponseEntity<HRDTO> getHR(@PathVariable Long id) {
        log.debug("REST request to get HR : {}", id);
        Optional<HRDTO> hRDTO = hRService.findOne(id);
        return ResponseUtil.wrapOrNotFound(hRDTO);
    }

    /**
     * DELETE  /hrs/:id : delete the "id" hR.
     *
     * @param id the id of the hRDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/hrs/{id}")
    @Timed
    public ResponseEntity<Void> deleteHR(@PathVariable Long id) {
        log.debug("REST request to delete HR : {}", id);
        hRService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
