package com.sggw.accessmux.service.impl;

import com.sggw.accessmux.service.MARKETINGService;
import com.sggw.accessmux.domain.MARKETING;
import com.sggw.accessmux.repository.MARKETINGRepository;
import com.sggw.accessmux.service.dto.MARKETINGDTO;
import com.sggw.accessmux.service.mapper.MARKETINGMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing MARKETING.
 */
@Service
@Transactional
public class MARKETINGServiceImpl implements MARKETINGService {

    private final Logger log = LoggerFactory.getLogger(MARKETINGServiceImpl.class);

    private final MARKETINGRepository mARKETINGRepository;

    private final MARKETINGMapper mARKETINGMapper;

    public MARKETINGServiceImpl(MARKETINGRepository mARKETINGRepository, MARKETINGMapper mARKETINGMapper) {
        this.mARKETINGRepository = mARKETINGRepository;
        this.mARKETINGMapper = mARKETINGMapper;
    }

    /**
     * Save a mARKETING.
     *
     * @param mARKETINGDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public MARKETINGDTO save(MARKETINGDTO mARKETINGDTO) {
        log.debug("Request to save MARKETING : {}", mARKETINGDTO);
        MARKETING mARKETING = mARKETINGMapper.toEntity(mARKETINGDTO);
        mARKETING = mARKETINGRepository.save(mARKETING);
        return mARKETINGMapper.toDto(mARKETING);
    }

    /**
     * Get all the mARKETINGS.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<MARKETINGDTO> findAll() {
        log.debug("Request to get all MARKETINGS");
        return mARKETINGRepository.findAll().stream()
            .map(mARKETINGMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one mARKETING by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<MARKETINGDTO> findOne(Long id) {
        log.debug("Request to get MARKETING : {}", id);
        return mARKETINGRepository.findById(id)
            .map(mARKETINGMapper::toDto);
    }

    /**
     * Delete the mARKETING by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete MARKETING : {}", id);
        mARKETINGRepository.deleteById(id);
    }
}
