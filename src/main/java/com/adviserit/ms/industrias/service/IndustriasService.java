package com.adviserit.ms.industrias.service;

import com.adviserit.ms.industrias.domain.Industrias;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link Industrias}.
 */
public interface IndustriasService {

    /**
     * Save a industrias.
     *
     * @param industrias the entity to save.
     * @return the persisted entity.
     */
    Industrias save(Industrias industrias);

    /**
     * Get all the industrias.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Industrias> findAll(Pageable pageable);


    /**
     * Get the "id" industrias.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Industrias> findOne(Long id);

    /**
     * Delete the "id" industrias.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
