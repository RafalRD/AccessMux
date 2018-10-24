package com.sggw.accessmux.service;

import com.sggw.accessmux.service.dto.ACTIVITYDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing ACTIVITY.
 */
public interface ACTIVITYService {

    /**
     * Save a aCTIVITY.
     *
     * @param aCTIVITYDTO the entity to save
     * @return the persisted entity
     */
    ACTIVITYDTO save(ACTIVITYDTO aCTIVITYDTO);

    /**
     * Get all the aCTIVITIES.
     *
     * @return the list of entities
     */
    List<ACTIVITYDTO> findAll();


    /**
     * Get the "id" aCTIVITY.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<ACTIVITYDTO> findOne(Long id);

    /**
     * Delete the "id" aCTIVITY.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
