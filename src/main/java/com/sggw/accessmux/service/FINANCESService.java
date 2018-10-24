package com.sggw.accessmux.service;

import com.sggw.accessmux.service.dto.FINANCESDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing FINANCES.
 */
public interface FINANCESService {

    /**
     * Save a fINANCES.
     *
     * @param fINANCESDTO the entity to save
     * @return the persisted entity
     */
    FINANCESDTO save(FINANCESDTO fINANCESDTO);

    /**
     * Get all the fINANCES.
     *
     * @return the list of entities
     */
    List<FINANCESDTO> findAll();


    /**
     * Get the "id" fINANCES.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<FINANCESDTO> findOne(Long id);

    /**
     * Delete the "id" fINANCES.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
