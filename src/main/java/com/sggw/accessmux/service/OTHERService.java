package com.sggw.accessmux.service;

import com.sggw.accessmux.service.dto.OTHERDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing OTHER.
 */
public interface OTHERService {

    /**
     * Save a oTHER.
     *
     * @param oTHERDTO the entity to save
     * @return the persisted entity
     */
    OTHERDTO save(OTHERDTO oTHERDTO);

    /**
     * Get all the oTHERS.
     *
     * @return the list of entities
     */
    List<OTHERDTO> findAll();


    /**
     * Get the "id" oTHER.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<OTHERDTO> findOne(Long id);

    /**
     * Delete the "id" oTHER.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
