package com.sggw.accessmux.service;

import com.sggw.accessmux.service.dto.MARKETINGDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing MARKETING.
 */
public interface MARKETINGService {

    /**
     * Save a mARKETING.
     *
     * @param mARKETINGDTO the entity to save
     * @return the persisted entity
     */
    MARKETINGDTO save(MARKETINGDTO mARKETINGDTO);

    /**
     * Get all the mARKETINGS.
     *
     * @return the list of entities
     */
    List<MARKETINGDTO> findAll();


    /**
     * Get the "id" mARKETING.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<MARKETINGDTO> findOne(Long id);

    /**
     * Delete the "id" mARKETING.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
