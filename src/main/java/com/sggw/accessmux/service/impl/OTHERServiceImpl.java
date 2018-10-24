package com.sggw.accessmux.service.impl;

import com.sggw.accessmux.service.OTHERService;
import com.sggw.accessmux.domain.OTHER;
import com.sggw.accessmux.repository.OTHERRepository;
import com.sggw.accessmux.service.dto.OTHERDTO;
import com.sggw.accessmux.service.mapper.OTHERMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing OTHER.
 */
@Service
@Transactional
public class OTHERServiceImpl implements OTHERService {

    private final Logger log = LoggerFactory.getLogger(OTHERServiceImpl.class);

    private final OTHERRepository oTHERRepository;

    private final OTHERMapper oTHERMapper;

    public OTHERServiceImpl(OTHERRepository oTHERRepository, OTHERMapper oTHERMapper) {
        this.oTHERRepository = oTHERRepository;
        this.oTHERMapper = oTHERMapper;
    }

    /**
     * Save a oTHER.
     *
     * @param oTHERDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public OTHERDTO save(OTHERDTO oTHERDTO) {
        log.debug("Request to save OTHER : {}", oTHERDTO);
        OTHER oTHER = oTHERMapper.toEntity(oTHERDTO);
        oTHER = oTHERRepository.save(oTHER);
        return oTHERMapper.toDto(oTHER);
    }

    /**
     * Get all the oTHERS.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<OTHERDTO> findAll() {
        log.debug("Request to get all OTHERS");
        return oTHERRepository.findAll().stream()
            .map(oTHERMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one oTHER by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<OTHERDTO> findOne(Long id) {
        log.debug("Request to get OTHER : {}", id);
        return oTHERRepository.findById(id)
            .map(oTHERMapper::toDto);
    }

    /**
     * Delete the oTHER by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete OTHER : {}", id);
        oTHERRepository.deleteById(id);
    }
}
