package com.adviserit.ms.industrias.web.rest;

import com.adviserit.ms.industrias.domain.Industrias;
import com.adviserit.ms.industrias.service.IndustriasService;
import com.adviserit.ms.industrias.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.adviserit.ms.industrias.domain.Industrias}.
 */
@RestController
@RequestMapping("/api")
public class IndustriasResource {

    private final Logger log = LoggerFactory.getLogger(IndustriasResource.class);

    private static final String ENTITY_NAME = "industriasIndustrias";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final IndustriasService industriasService;

    public IndustriasResource(IndustriasService industriasService) {
        this.industriasService = industriasService;
    }

    /**
     * {@code POST  /industrias} : Create a new industrias.
     *
     * @param industrias the industrias to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new industrias, or with status {@code 400 (Bad Request)} if the industrias has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/industrias")
    public ResponseEntity<Industrias> createIndustrias(@Valid @RequestBody Industrias industrias) throws URISyntaxException {
        log.debug("REST request to save Industrias : {}", industrias);
        if (industrias.getId() != null) {
            throw new BadRequestAlertException("A new industrias cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Industrias result = industriasService.save(industrias);
        return ResponseEntity.created(new URI("/api/industrias/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /industrias} : Updates an existing industrias.
     *
     * @param industrias the industrias to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated industrias,
     * or with status {@code 400 (Bad Request)} if the industrias is not valid,
     * or with status {@code 500 (Internal Server Error)} if the industrias couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/industrias")
    public ResponseEntity<Industrias> updateIndustrias(@Valid @RequestBody Industrias industrias) throws URISyntaxException {
        log.debug("REST request to update Industrias : {}", industrias);
        if (industrias.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Industrias result = industriasService.save(industrias);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, industrias.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /industrias} : get all the industrias.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of industrias in body.
     */
    @GetMapping("/industrias")
    public ResponseEntity<List<Industrias>> getAllIndustrias(Pageable pageable) {
        log.debug("REST request to get a page of Industrias");
        Page<Industrias> page = industriasService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /industrias/:id} : get the "id" industrias.
     *
     * @param id the id of the industrias to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the industrias, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/industrias/{id}")
    public ResponseEntity<Industrias> getIndustrias(@PathVariable Long id) {
        log.debug("REST request to get Industrias : {}", id);
        Optional<Industrias> industrias = industriasService.findOne(id);
        return ResponseUtil.wrapOrNotFound(industrias);
    }

    /**
     * {@code DELETE  /industrias/:id} : delete the "id" industrias.
     *
     * @param id the id of the industrias to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/industrias/{id}")
    public ResponseEntity<Void> deleteIndustrias(@PathVariable Long id) {
        log.debug("REST request to delete Industrias : {}", id);
        industriasService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
