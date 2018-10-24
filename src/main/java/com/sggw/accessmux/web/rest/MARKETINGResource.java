package com.sggw.accessmux.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.sggw.accessmux.service.MARKETINGService;
import com.sggw.accessmux.web.rest.errors.BadRequestAlertException;
import com.sggw.accessmux.web.rest.util.HeaderUtil;
import com.sggw.accessmux.service.dto.MARKETINGDTO;
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
 * REST controller for managing MARKETING.
 */
@RestController
@RequestMapping("/api")
public class MARKETINGResource {

    private final Logger log = LoggerFactory.getLogger(MARKETINGResource.class);

    private static final String ENTITY_NAME = "mARKETING";

    private final MARKETINGService mARKETINGService;

    public MARKETINGResource(MARKETINGService mARKETINGService) {
        this.mARKETINGService = mARKETINGService;
    }

    /**
     * POST  /marketings : Create a new mARKETING.
     *
     * @param mARKETINGDTO the mARKETINGDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new mARKETINGDTO, or with status 400 (Bad Request) if the mARKETING has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/marketings")
    @Timed
    public ResponseEntity<MARKETINGDTO> createMARKETING(@Valid @RequestBody MARKETINGDTO mARKETINGDTO) throws URISyntaxException {
        log.debug("REST request to save MARKETING : {}", mARKETINGDTO);
        if (mARKETINGDTO.getId() != null) {
            throw new BadRequestAlertException("A new mARKETING cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MARKETINGDTO result = mARKETINGService.save(mARKETINGDTO);
        return ResponseEntity.created(new URI("/api/marketings/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /marketings : Updates an existing mARKETING.
     *
     * @param mARKETINGDTO the mARKETINGDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated mARKETINGDTO,
     * or with status 400 (Bad Request) if the mARKETINGDTO is not valid,
     * or with status 500 (Internal Server Error) if the mARKETINGDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/marketings")
    @Timed
    public ResponseEntity<MARKETINGDTO> updateMARKETING(@Valid @RequestBody MARKETINGDTO mARKETINGDTO) throws URISyntaxException {
        log.debug("REST request to update MARKETING : {}", mARKETINGDTO);
        if (mARKETINGDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MARKETINGDTO result = mARKETINGService.save(mARKETINGDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, mARKETINGDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /marketings : get all the mARKETINGS.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of mARKETINGS in body
     */
    @GetMapping("/marketings")
    @Timed
    public List<MARKETINGDTO> getAllMARKETINGS() {
        log.debug("REST request to get all MARKETINGS");
        return mARKETINGService.findAll();
    }

    /**
     * GET  /marketings/:id : get the "id" mARKETING.
     *
     * @param id the id of the mARKETINGDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the mARKETINGDTO, or with status 404 (Not Found)
     */
    @GetMapping("/marketings/{id}")
    @Timed
    public ResponseEntity<MARKETINGDTO> getMARKETING(@PathVariable Long id) {
        log.debug("REST request to get MARKETING : {}", id);
        Optional<MARKETINGDTO> mARKETINGDTO = mARKETINGService.findOne(id);
        return ResponseUtil.wrapOrNotFound(mARKETINGDTO);
    }

    /**
     * DELETE  /marketings/:id : delete the "id" mARKETING.
     *
     * @param id the id of the mARKETINGDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/marketings/{id}")
    @Timed
    public ResponseEntity<Void> deleteMARKETING(@PathVariable Long id) {
        log.debug("REST request to delete MARKETING : {}", id);
        mARKETINGService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
