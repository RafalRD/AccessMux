package com.sggw.accessmux.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.sggw.accessmux.security.SecurityUtils;
import com.sggw.accessmux.service.FINANCESService;
import com.sggw.accessmux.web.rest.errors.BadRequestAlertException;
import com.sggw.accessmux.web.rest.util.HeaderUtil;
import com.sggw.accessmux.service.dto.FINANCESDTO;
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
import static com.sggw.accessmux.security.AuthoritiesConstants.FINANSE;

/**
 * REST controller for managing FINANCES.
 */
@RestController
@RequestMapping("/api")
public class FINANCESResource {

    private final Logger log = LoggerFactory.getLogger(FINANCESResource.class);

    private static final String ENTITY_NAME = "fINANCES";

    private final FINANCESService fINANCESService;

    public FINANCESResource(FINANCESService fINANCESService) {
        this.fINANCESService = fINANCESService;
    }

    /**
     * POST  /finances : Create a new fINANCES.
     *
     * @param fINANCESDTO the fINANCESDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new fINANCESDTO, or with status 400 (Bad Request) if the fINANCES has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/finances")
    @Timed
    public ResponseEntity<FINANCESDTO> createFINANCES(@Valid @RequestBody FINANCESDTO fINANCESDTO) throws URISyntaxException {

        if (!(SecurityUtils.isCurrentUserInRole(FINANSE) || SecurityUtils.isCurrentUserInRole(ADMIN))) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "http.403","You need to be logged!")).body(null);
        }

        log.debug("REST request to save FINANCES : {}", fINANCESDTO);
        if (fINANCESDTO.getId() != null) {
            throw new BadRequestAlertException("A new fINANCES cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FINANCESDTO result = fINANCESService.save(fINANCESDTO);
        return ResponseEntity.created(new URI("/api/finances/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /finances : Updates an existing fINANCES.
     *
     * @param fINANCESDTO the fINANCESDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated fINANCESDTO,
     * or with status 400 (Bad Request) if the fINANCESDTO is not valid,
     * or with status 500 (Internal Server Error) if the fINANCESDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/finances")
    @Timed
    public ResponseEntity<FINANCESDTO> updateFINANCES(@Valid @RequestBody FINANCESDTO fINANCESDTO) throws URISyntaxException {

        if (!(SecurityUtils.isCurrentUserInRole(FINANSE) || SecurityUtils.isCurrentUserInRole(ADMIN))) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "http.403","You need to be logged!")).body(null);
        }

        log.debug("REST request to update FINANCES : {}", fINANCESDTO);
        if (fINANCESDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        FINANCESDTO result = fINANCESService.save(fINANCESDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, fINANCESDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /finances : get all the fINANCES.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of fINANCES in body
     */
    @GetMapping("/finances")
    @Timed
    public List<FINANCESDTO> getAllFINANCES() {

        if (!(SecurityUtils.isCurrentUserInRole(FINANSE) || SecurityUtils.isCurrentUserInRole(ADMIN))) {
            return null;
        }

        log.debug("REST request to get all FINANCES");
        return fINANCESService.findAll();
    }

    /**
     * GET  /finances/:id : get the "id" fINANCES.
     *
     * @param id the id of the fINANCESDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the fINANCESDTO, or with status 404 (Not Found)
     */
    @GetMapping("/finances/{id}")
    @Timed
    public ResponseEntity<FINANCESDTO> getFINANCES(@PathVariable Long id) {

        if (!(SecurityUtils.isCurrentUserInRole(FINANSE) || SecurityUtils.isCurrentUserInRole(ADMIN))) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "http.403","You need to be logged!")).body(null);
        }

        log.debug("REST request to get FINANCES : {}", id);
        Optional<FINANCESDTO> fINANCESDTO = fINANCESService.findOne(id);
        return ResponseUtil.wrapOrNotFound(fINANCESDTO);
    }

    /**
     * DELETE  /finances/:id : delete the "id" fINANCES.
     *
     * @param id the id of the fINANCESDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/finances/{id}")
    @Timed
    public ResponseEntity<Void> deleteFINANCES(@PathVariable Long id) {

        if (!(SecurityUtils.isCurrentUserInRole(FINANSE) || SecurityUtils.isCurrentUserInRole(ADMIN))) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "http.403","You need to be logged!")).body(null);
        }

        log.debug("REST request to delete FINANCES : {}", id);
        fINANCESService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
