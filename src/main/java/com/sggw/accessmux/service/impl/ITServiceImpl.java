package com.sggw.accessmux.service.impl;

import com.sggw.accessmux.service.ITService;
import com.sggw.accessmux.domain.IT;
import com.sggw.accessmux.repository.ITRepository;
import com.sggw.accessmux.service.dto.ITDTO;
import com.sggw.accessmux.service.mapper.ITMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing IT.
 */
@Service
@Transactional
public class ITServiceImpl implements ITService {

    private final Logger log = LoggerFactory.getLogger(ITServiceImpl.class);

    private final ITRepository iTRepository;

    private final ITMapper iTMapper;

    public ITServiceImpl(ITRepository iTRepository, ITMapper iTMapper) {
        this.iTRepository = iTRepository;
        this.iTMapper = iTMapper;
    }

    /**
     * Save a iT.
     *
     * @param iTDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ITDTO save(ITDTO iTDTO) {
        log.debug("Request to save IT : {}", iTDTO);
        IT iT = iTMapper.toEntity(iTDTO);
        iT = iTRepository.save(iT);
        return iTMapper.toDto(iT);
    }

    /**
     * Get all the iTS.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<ITDTO> findAll() {
        log.debug("Request to get all ITS");
        return iTRepository.findAll().stream()
            .map(iTMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one iT by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ITDTO> findOne(Long id) {
        log.debug("Request to get IT : {}", id);
        return iTRepository.findById(id)
            .map(iTMapper::toDto);
    }

    /**
     * Delete the iT by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete IT : {}", id);
        iTRepository.deleteById(id);
    }
}
