package com.sggw.accessmux.service.impl;

import com.sggw.accessmux.service.ACTIVITYService;
import com.sggw.accessmux.domain.ACTIVITY;
import com.sggw.accessmux.repository.ACTIVITYRepository;
import com.sggw.accessmux.service.dto.ACTIVITYDTO;
import com.sggw.accessmux.service.mapper.ACTIVITYMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing ACTIVITY.
 */
@Service
@Transactional
public class ACTIVITYServiceImpl implements ACTIVITYService {

    private final Logger log = LoggerFactory.getLogger(ACTIVITYServiceImpl.class);

    private final ACTIVITYRepository aCTIVITYRepository;

    private final ACTIVITYMapper aCTIVITYMapper;

    public ACTIVITYServiceImpl(ACTIVITYRepository aCTIVITYRepository, ACTIVITYMapper aCTIVITYMapper) {
        this.aCTIVITYRepository = aCTIVITYRepository;
        this.aCTIVITYMapper = aCTIVITYMapper;
    }

    /**
     * Save a aCTIVITY.
     *
     * @param aCTIVITYDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ACTIVITYDTO save(ACTIVITYDTO aCTIVITYDTO) {
        log.debug("Request to save ACTIVITY : {}", aCTIVITYDTO);
        ACTIVITY aCTIVITY = aCTIVITYMapper.toEntity(aCTIVITYDTO);
        aCTIVITY = aCTIVITYRepository.save(aCTIVITY);
        return aCTIVITYMapper.toDto(aCTIVITY);
    }

    /**
     * Get all the aCTIVITIES.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<ACTIVITYDTO> findAll() {
        log.debug("Request to get all ACTIVITIES");
        return aCTIVITYRepository.findAll().stream()
            .map(aCTIVITYMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one aCTIVITY by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ACTIVITYDTO> findOne(Long id) {
        log.debug("Request to get ACTIVITY : {}", id);
        return aCTIVITYRepository.findById(id)
            .map(aCTIVITYMapper::toDto);
    }

    /**
     * Delete the aCTIVITY by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ACTIVITY : {}", id);
        aCTIVITYRepository.deleteById(id);
    }
}
