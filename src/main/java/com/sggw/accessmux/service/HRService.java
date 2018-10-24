package com.sggw.accessmux.service;

import com.sggw.accessmux.service.dto.HRDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing HR.
 */
public interface HRService {

    /**
     * Save a hR.
     *
     * @param hRDTO the entity to save
     * @return the persisted entity
     */
    HRDTO save(HRDTO hRDTO);

    /**
     * Get all the hRS.
     *
     * @return the list of entities
     */
    List<HRDTO> findAll();


    /**
     * Get the "id" hR.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<HRDTO> findOne(Long id);

    /**
     * Delete the "id" hR.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
