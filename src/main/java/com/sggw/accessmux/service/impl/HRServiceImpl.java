package com.sggw.accessmux.service.impl;

import com.sggw.accessmux.service.HRService;
import com.sggw.accessmux.domain.HR;
import com.sggw.accessmux.repository.HRRepository;
import com.sggw.accessmux.service.dto.HRDTO;
import com.sggw.accessmux.service.mapper.HRMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing HR.
 */
@Service
@Transactional
public class HRServiceImpl implements HRService {

    private final Logger log = LoggerFactory.getLogger(HRServiceImpl.class);

    private final HRRepository hRRepository;

    private final HRMapper hRMapper;

    public HRServiceImpl(HRRepository hRRepository, HRMapper hRMapper) {
        this.hRRepository = hRRepository;
        this.hRMapper = hRMapper;
    }

    /**
     * Save a hR.
     *
     * @param hRDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public HRDTO save(HRDTO hRDTO) {
        log.debug("Request to save HR : {}", hRDTO);
        HR hR = hRMapper.toEntity(hRDTO);
        hR = hRRepository.save(hR);
        return hRMapper.toDto(hR);
    }

    /**
     * Get all the hRS.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<HRDTO> findAll() {
        log.debug("Request to get all HRS");
        return hRRepository.findAll().stream()
            .map(hRMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one hR by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<HRDTO> findOne(Long id) {
        log.debug("Request to get HR : {}", id);
        return hRRepository.findById(id)
            .map(hRMapper::toDto);
    }

    /**
     * Delete the hR by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete HR : {}", id);
        hRRepository.deleteById(id);
    }
}
