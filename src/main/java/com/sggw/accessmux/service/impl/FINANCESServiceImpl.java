package com.sggw.accessmux.service.impl;

import com.sggw.accessmux.service.FINANCESService;
import com.sggw.accessmux.domain.FINANCES;
import com.sggw.accessmux.repository.FINANCESRepository;
import com.sggw.accessmux.service.dto.FINANCESDTO;
import com.sggw.accessmux.service.mapper.FINANCESMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing FINANCES.
 */
@Service
@Transactional
public class FINANCESServiceImpl implements FINANCESService {

    private final Logger log = LoggerFactory.getLogger(FINANCESServiceImpl.class);

    private final FINANCESRepository fINANCESRepository;

    private final FINANCESMapper fINANCESMapper;

    public FINANCESServiceImpl(FINANCESRepository fINANCESRepository, FINANCESMapper fINANCESMapper) {
        this.fINANCESRepository = fINANCESRepository;
        this.fINANCESMapper = fINANCESMapper;
    }

    /**
     * Save a fINANCES.
     *
     * @param fINANCESDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public FINANCESDTO save(FINANCESDTO fINANCESDTO) {
        log.debug("Request to save FINANCES : {}", fINANCESDTO);
        FINANCES fINANCES = fINANCESMapper.toEntity(fINANCESDTO);
        fINANCES = fINANCESRepository.save(fINANCES);
        return fINANCESMapper.toDto(fINANCES);
    }

    /**
     * Get all the fINANCES.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<FINANCESDTO> findAll() {
        log.debug("Request to get all FINANCES");
        return fINANCESRepository.findAll().stream()
            .map(fINANCESMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one fINANCES by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<FINANCESDTO> findOne(Long id) {
        log.debug("Request to get FINANCES : {}", id);
        return fINANCESRepository.findById(id)
            .map(fINANCESMapper::toDto);
    }

    /**
     * Delete the fINANCES by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete FINANCES : {}", id);
        fINANCESRepository.deleteById(id);
    }
}
