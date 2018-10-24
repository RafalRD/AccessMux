package com.sggw.accessmux.service.impl;

import com.sggw.accessmux.service.SALEService;
import com.sggw.accessmux.domain.SALE;
import com.sggw.accessmux.repository.SALERepository;
import com.sggw.accessmux.service.dto.SALEDTO;
import com.sggw.accessmux.service.mapper.SALEMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing SALE.
 */
@Service
@Transactional
public class SALEServiceImpl implements SALEService {

    private final Logger log = LoggerFactory.getLogger(SALEServiceImpl.class);

    private final SALERepository sALERepository;

    private final SALEMapper sALEMapper;

    public SALEServiceImpl(SALERepository sALERepository, SALEMapper sALEMapper) {
        this.sALERepository = sALERepository;
        this.sALEMapper = sALEMapper;
    }

    /**
     * Save a sALE.
     *
     * @param sALEDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public SALEDTO save(SALEDTO sALEDTO) {
        log.debug("Request to save SALE : {}", sALEDTO);
        SALE sALE = sALEMapper.toEntity(sALEDTO);
        sALE = sALERepository.save(sALE);
        return sALEMapper.toDto(sALE);
    }

    /**
     * Get all the sALES.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<SALEDTO> findAll() {
        log.debug("Request to get all SALES");
        return sALERepository.findAll().stream()
            .map(sALEMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one sALE by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<SALEDTO> findOne(Long id) {
        log.debug("Request to get SALE : {}", id);
        return sALERepository.findById(id)
            .map(sALEMapper::toDto);
    }

    /**
     * Delete the sALE by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete SALE : {}", id);
        sALERepository.deleteById(id);
    }
}
