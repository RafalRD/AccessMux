package com.sggw.accessmux.service;

import com.sggw.accessmux.service.dto.ITDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing IT.
 */
public interface ITService {

    /**
     * Save a iT.
     *
     * @param iTDTO the entity to save
     * @return the persisted entity
     */
    ITDTO save(ITDTO iTDTO);

    /**
     * Get all the iTS.
     *
     * @return the list of entities
     */
    List<ITDTO> findAll();


    /**
     * Get the "id" iT.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<ITDTO> findOne(Long id);

    /**
     * Delete the "id" iT.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
