package com.sggw.accessmux.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.sggw.accessmux.security.SecurityUtils;
import com.sggw.accessmux.service.SALEService;
import com.sggw.accessmux.web.rest.errors.BadRequestAlertException;
import com.sggw.accessmux.web.rest.util.HeaderUtil;
import com.sggw.accessmux.service.dto.SALEDTO;
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

import static com.sggw.accessmux.security.AuthoritiesConstants.ADMIN;
import static com.sggw.accessmux.security.AuthoritiesConstants.SPRZEDAZ;

/**
 * REST controller for managing SALE.
 */
@RestController
@RequestMapping("/api")
public class SALEResource {

    private final Logger log = LoggerFactory.getLogger(SALEResource.class);

    private static final String ENTITY_NAME = "sALE";

    private final SALEService sALEService;

    public SALEResource(SALEService sALEService) {
        this.sALEService = sALEService;
    }

    /**
     * POST  /sales : Create a new sALE.
     *
     * @param sALEDTO the sALEDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new sALEDTO, or with status 400 (Bad Request) if the sALE has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/sales")
    @Timed
    public ResponseEntity<SALEDTO> createSALE(@Valid @RequestBody SALEDTO sALEDTO) throws URISyntaxException {
        if (!(SecurityUtils.isCurrentUserInRole(SPRZEDAZ) || SecurityUtils.isCurrentUserInRole(ADMIN))) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "http.403","You need to be logged!")).body(null);
        }

        log.debug("REST request to save SALE : {}", sALEDTO);
        if (sALEDTO.getId() != null) {
            throw new BadRequestAlertException("A new sALE cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SALEDTO result = sALEService.save(sALEDTO);
        return ResponseEntity.created(new URI("/api/sales/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /sales : Updates an existing sALE.
     *
     * @param sALEDTO the sALEDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated sALEDTO,
     * or with status 400 (Bad Request) if the sALEDTO is not valid,
     * or with status 500 (Internal Server Error) if the sALEDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/sales")
    @Timed
    public ResponseEntity<SALEDTO> updateSALE(@Valid @RequestBody SALEDTO sALEDTO) throws URISyntaxException {
        if (!(SecurityUtils.isCurrentUserInRole(SPRZEDAZ) || SecurityUtils.isCurrentUserInRole(ADMIN))) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "http.403","You need to be logged!")).body(null);
        }
        log.debug("REST request to update SALE : {}", sALEDTO);
        if (sALEDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SALEDTO result = sALEService.save(sALEDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, sALEDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /sales : get all the sALES.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of sALES in body
     */
    @GetMapping("/sales")
    @Timed
    public List<SALEDTO> getAllSALES() {
        if (!(SecurityUtils.isCurrentUserInRole(SPRZEDAZ) || SecurityUtils.isCurrentUserInRole(ADMIN))) {
            return null;
        }

        log.debug("REST request to get all SALES");
        return sALEService.findAll();
    }

    /**
     * GET  /sales/:id : get the "id" sALE.
     *
     * @param id the id of the sALEDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the sALEDTO, or with status 404 (Not Found)
     */
    @GetMapping("/sales/{id}")
    @Timed
    public ResponseEntity<SALEDTO> getSALE(@PathVariable Long id) {
        if (!(SecurityUtils.isCurrentUserInRole(SPRZEDAZ) || SecurityUtils.isCurrentUserInRole(ADMIN))) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "http.403","You need to be logged!")).body(null);
        }

        log.debug("REST request to get SALE : {}", id);
        Optional<SALEDTO> sALEDTO = sALEService.findOne(id);
        return ResponseUtil.wrapOrNotFound(sALEDTO);
    }

    /**
     * DELETE  /sales/:id : delete the "id" sALE.
     *
     * @param id the id of the sALEDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/sales/{id}")
    @Timed
    public ResponseEntity<Void> deleteSALE(@PathVariable Long id) {
        if (!(SecurityUtils.isCurrentUserInRole(SPRZEDAZ) || SecurityUtils.isCurrentUserInRole(ADMIN))) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "http.403","You need to be logged!")).body(null);
        }

        log.debug("REST request to delete SALE : {}", id);
        sALEService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
