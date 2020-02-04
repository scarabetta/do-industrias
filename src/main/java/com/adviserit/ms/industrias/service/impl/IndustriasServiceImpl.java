package com.adviserit.ms.industrias.service.impl;

import com.adviserit.ms.industrias.service.IndustriasService;
import com.adviserit.ms.industrias.domain.Industrias;
import com.adviserit.ms.industrias.repository.IndustriasRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Industrias}.
 */
@Service
@Transactional
public class IndustriasServiceImpl implements IndustriasService {

    private final Logger log = LoggerFactory.getLogger(IndustriasServiceImpl.class);

    private final IndustriasRepository industriasRepository;

    public IndustriasServiceImpl(IndustriasRepository industriasRepository) {
        this.industriasRepository = industriasRepository;
    }

    /**
     * Save a industrias.
     *
     * @param industrias the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Industrias save(Industrias industrias) {
        log.debug("Request to save Industrias : {}", industrias);
        return industriasRepository.save(industrias);
    }

    /**
     * Get all the industrias.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Industrias> findAll(Pageable pageable) {
        log.debug("Request to get all Industrias");
        return industriasRepository.findAll(pageable);
    }


    /**
     * Get one industrias by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Industrias> findOne(Long id) {
        log.debug("Request to get Industrias : {}", id);
        return industriasRepository.findById(id);
    }

    /**
     * Delete the industrias by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Industrias : {}", id);
        industriasRepository.deleteById(id);
    }
}
