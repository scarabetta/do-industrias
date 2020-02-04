package com.adviserit.ms.industrias.web.rest;

import com.adviserit.ms.industrias.IndustriasApp;
import com.adviserit.ms.industrias.config.SecurityBeanOverrideConfiguration;
import com.adviserit.ms.industrias.domain.Industrias;
import com.adviserit.ms.industrias.repository.IndustriasRepository;
import com.adviserit.ms.industrias.service.IndustriasService;
import com.adviserit.ms.industrias.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;

import static com.adviserit.ms.industrias.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.adviserit.ms.industrias.domain.enumeration.Categoria;
/**
 * Integration tests for the {@link IndustriasResource} REST controller.
 */
@SpringBootTest(classes = {SecurityBeanOverrideConfiguration.class, IndustriasApp.class})
public class IndustriasResourceIT {

    private static final String DEFAULT_INDUSTRIA = "AAAAAAAAAA";
    private static final String UPDATED_INDUSTRIA = "BBBBBBBBBB";

    private static final Categoria DEFAULT_CATEGORIA = Categoria.BIENES;
    private static final Categoria UPDATED_CATEGORIA = Categoria.SERVICIOS;

    @Autowired
    private IndustriasRepository industriasRepository;

    @Autowired
    private IndustriasService industriasService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restIndustriasMockMvc;

    private Industrias industrias;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final IndustriasResource industriasResource = new IndustriasResource(industriasService);
        this.restIndustriasMockMvc = MockMvcBuilders.standaloneSetup(industriasResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Industrias createEntity(EntityManager em) {
        Industrias industrias = new Industrias()
            .industria(DEFAULT_INDUSTRIA)
            .categoria(DEFAULT_CATEGORIA);
        return industrias;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Industrias createUpdatedEntity(EntityManager em) {
        Industrias industrias = new Industrias()
            .industria(UPDATED_INDUSTRIA)
            .categoria(UPDATED_CATEGORIA);
        return industrias;
    }

    @BeforeEach
    public void initTest() {
        industrias = createEntity(em);
    }

    @Test
    @Transactional
    public void createIndustrias() throws Exception {
        int databaseSizeBeforeCreate = industriasRepository.findAll().size();

        // Create the Industrias
        restIndustriasMockMvc.perform(post("/api/industrias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(industrias)))
            .andExpect(status().isCreated());

        // Validate the Industrias in the database
        List<Industrias> industriasList = industriasRepository.findAll();
        assertThat(industriasList).hasSize(databaseSizeBeforeCreate + 1);
        Industrias testIndustrias = industriasList.get(industriasList.size() - 1);
        assertThat(testIndustrias.getIndustria()).isEqualTo(DEFAULT_INDUSTRIA);
        assertThat(testIndustrias.getCategoria()).isEqualTo(DEFAULT_CATEGORIA);
    }

    @Test
    @Transactional
    public void createIndustriasWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = industriasRepository.findAll().size();

        // Create the Industrias with an existing ID
        industrias.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restIndustriasMockMvc.perform(post("/api/industrias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(industrias)))
            .andExpect(status().isBadRequest());

        // Validate the Industrias in the database
        List<Industrias> industriasList = industriasRepository.findAll();
        assertThat(industriasList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkIndustriaIsRequired() throws Exception {
        int databaseSizeBeforeTest = industriasRepository.findAll().size();
        // set the field null
        industrias.setIndustria(null);

        // Create the Industrias, which fails.

        restIndustriasMockMvc.perform(post("/api/industrias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(industrias)))
            .andExpect(status().isBadRequest());

        List<Industrias> industriasList = industriasRepository.findAll();
        assertThat(industriasList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllIndustrias() throws Exception {
        // Initialize the database
        industriasRepository.saveAndFlush(industrias);

        // Get all the industriasList
        restIndustriasMockMvc.perform(get("/api/industrias?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(industrias.getId().intValue())))
            .andExpect(jsonPath("$.[*].industria").value(hasItem(DEFAULT_INDUSTRIA)))
            .andExpect(jsonPath("$.[*].categoria").value(hasItem(DEFAULT_CATEGORIA.toString())));
    }
    
    @Test
    @Transactional
    public void getIndustrias() throws Exception {
        // Initialize the database
        industriasRepository.saveAndFlush(industrias);

        // Get the industrias
        restIndustriasMockMvc.perform(get("/api/industrias/{id}", industrias.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(industrias.getId().intValue()))
            .andExpect(jsonPath("$.industria").value(DEFAULT_INDUSTRIA))
            .andExpect(jsonPath("$.categoria").value(DEFAULT_CATEGORIA.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingIndustrias() throws Exception {
        // Get the industrias
        restIndustriasMockMvc.perform(get("/api/industrias/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateIndustrias() throws Exception {
        // Initialize the database
        industriasService.save(industrias);

        int databaseSizeBeforeUpdate = industriasRepository.findAll().size();

        // Update the industrias
        Industrias updatedIndustrias = industriasRepository.findById(industrias.getId()).get();
        // Disconnect from session so that the updates on updatedIndustrias are not directly saved in db
        em.detach(updatedIndustrias);
        updatedIndustrias
            .industria(UPDATED_INDUSTRIA)
            .categoria(UPDATED_CATEGORIA);

        restIndustriasMockMvc.perform(put("/api/industrias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedIndustrias)))
            .andExpect(status().isOk());

        // Validate the Industrias in the database
        List<Industrias> industriasList = industriasRepository.findAll();
        assertThat(industriasList).hasSize(databaseSizeBeforeUpdate);
        Industrias testIndustrias = industriasList.get(industriasList.size() - 1);
        assertThat(testIndustrias.getIndustria()).isEqualTo(UPDATED_INDUSTRIA);
        assertThat(testIndustrias.getCategoria()).isEqualTo(UPDATED_CATEGORIA);
    }

    @Test
    @Transactional
    public void updateNonExistingIndustrias() throws Exception {
        int databaseSizeBeforeUpdate = industriasRepository.findAll().size();

        // Create the Industrias

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restIndustriasMockMvc.perform(put("/api/industrias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(industrias)))
            .andExpect(status().isBadRequest());

        // Validate the Industrias in the database
        List<Industrias> industriasList = industriasRepository.findAll();
        assertThat(industriasList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteIndustrias() throws Exception {
        // Initialize the database
        industriasService.save(industrias);

        int databaseSizeBeforeDelete = industriasRepository.findAll().size();

        // Delete the industrias
        restIndustriasMockMvc.perform(delete("/api/industrias/{id}", industrias.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Industrias> industriasList = industriasRepository.findAll();
        assertThat(industriasList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
