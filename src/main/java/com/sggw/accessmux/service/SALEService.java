package com.sggw.accessmux.service;

import com.sggw.accessmux.service.dto.SALEDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing SALE.
 */
public interface SALEService {

    /**
     * Save a sALE.
     *
     * @param sALEDTO the entity to save
     * @return the persisted entity
     */
    SALEDTO save(SALEDTO sALEDTO);

    /**
     * Get all the sALES.
     *
     * @return the list of entities
     */
    List<SALEDTO> findAll();


    /**
     * Get the "id" sALE.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<SALEDTO> findOne(Long id);

    /**
     * Delete the "id" sALE.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
