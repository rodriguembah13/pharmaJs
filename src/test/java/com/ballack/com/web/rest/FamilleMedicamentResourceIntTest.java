package com.ballack.com.web.rest;

import com.ballack.com.PharmaApp;

import com.ballack.com.domain.FamilleMedicament;
import com.ballack.com.repository.FamilleMedicamentRepository;
import com.ballack.com.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the FamilleMedicamentResource REST controller.
 *
 * @see FamilleMedicamentResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PharmaApp.class)
public class FamilleMedicamentResourceIntTest {

    private static final String DEFAULT_DESIGNATION = "AAAAAAAAAA";
    private static final String UPDATED_DESIGNATION = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    @Autowired
    private FamilleMedicamentRepository familleMedicamentRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restFamilleMedicamentMockMvc;

    private FamilleMedicament familleMedicament;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final FamilleMedicamentResource familleMedicamentResource = new FamilleMedicamentResource(familleMedicamentRepository);
        this.restFamilleMedicamentMockMvc = MockMvcBuilders.standaloneSetup(familleMedicamentResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FamilleMedicament createEntity(EntityManager em) {
        FamilleMedicament familleMedicament = new FamilleMedicament()
            .designation(DEFAULT_DESIGNATION)
            .description(DEFAULT_DESCRIPTION)
            .code(DEFAULT_CODE);
        return familleMedicament;
    }

    @Before
    public void initTest() {
        familleMedicament = createEntity(em);
    }

    @Test
    @Transactional
    public void createFamilleMedicament() throws Exception {
        int databaseSizeBeforeCreate = familleMedicamentRepository.findAll().size();

        // Create the FamilleMedicament
        restFamilleMedicamentMockMvc.perform(post("/api/famille-medicaments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(familleMedicament)))
            .andExpect(status().isCreated());

        // Validate the FamilleMedicament in the database
        List<FamilleMedicament> familleMedicamentList = familleMedicamentRepository.findAll();
        assertThat(familleMedicamentList).hasSize(databaseSizeBeforeCreate + 1);
        FamilleMedicament testFamilleMedicament = familleMedicamentList.get(familleMedicamentList.size() - 1);
        assertThat(testFamilleMedicament.getDesignation()).isEqualTo(DEFAULT_DESIGNATION);
        assertThat(testFamilleMedicament.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testFamilleMedicament.getCode()).isEqualTo(DEFAULT_CODE);
    }

    @Test
    @Transactional
    public void createFamilleMedicamentWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = familleMedicamentRepository.findAll().size();

        // Create the FamilleMedicament with an existing ID
        familleMedicament.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFamilleMedicamentMockMvc.perform(post("/api/famille-medicaments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(familleMedicament)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<FamilleMedicament> familleMedicamentList = familleMedicamentRepository.findAll();
        assertThat(familleMedicamentList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllFamilleMedicaments() throws Exception {
        // Initialize the database
        familleMedicamentRepository.saveAndFlush(familleMedicament);

        // Get all the familleMedicamentList
        restFamilleMedicamentMockMvc.perform(get("/api/famille-medicaments?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(familleMedicament.getId().intValue())))
            .andExpect(jsonPath("$.[*].designation").value(hasItem(DEFAULT_DESIGNATION.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())));
    }

    @Test
    @Transactional
    public void getFamilleMedicament() throws Exception {
        // Initialize the database
        familleMedicamentRepository.saveAndFlush(familleMedicament);

        // Get the familleMedicament
        restFamilleMedicamentMockMvc.perform(get("/api/famille-medicaments/{id}", familleMedicament.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(familleMedicament.getId().intValue()))
            .andExpect(jsonPath("$.designation").value(DEFAULT_DESIGNATION.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingFamilleMedicament() throws Exception {
        // Get the familleMedicament
        restFamilleMedicamentMockMvc.perform(get("/api/famille-medicaments/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFamilleMedicament() throws Exception {
        // Initialize the database
        familleMedicamentRepository.saveAndFlush(familleMedicament);
        int databaseSizeBeforeUpdate = familleMedicamentRepository.findAll().size();

        // Update the familleMedicament
        FamilleMedicament updatedFamilleMedicament = familleMedicamentRepository.findOne(familleMedicament.getId());
        updatedFamilleMedicament
            .designation(UPDATED_DESIGNATION)
            .description(UPDATED_DESCRIPTION)
            .code(UPDATED_CODE);

        restFamilleMedicamentMockMvc.perform(put("/api/famille-medicaments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedFamilleMedicament)))
            .andExpect(status().isOk());

        // Validate the FamilleMedicament in the database
        List<FamilleMedicament> familleMedicamentList = familleMedicamentRepository.findAll();
        assertThat(familleMedicamentList).hasSize(databaseSizeBeforeUpdate);
        FamilleMedicament testFamilleMedicament = familleMedicamentList.get(familleMedicamentList.size() - 1);
        assertThat(testFamilleMedicament.getDesignation()).isEqualTo(UPDATED_DESIGNATION);
        assertThat(testFamilleMedicament.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testFamilleMedicament.getCode()).isEqualTo(UPDATED_CODE);
    }

    @Test
    @Transactional
    public void updateNonExistingFamilleMedicament() throws Exception {
        int databaseSizeBeforeUpdate = familleMedicamentRepository.findAll().size();

        // Create the FamilleMedicament

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restFamilleMedicamentMockMvc.perform(put("/api/famille-medicaments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(familleMedicament)))
            .andExpect(status().isCreated());

        // Validate the FamilleMedicament in the database
        List<FamilleMedicament> familleMedicamentList = familleMedicamentRepository.findAll();
        assertThat(familleMedicamentList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteFamilleMedicament() throws Exception {
        // Initialize the database
        familleMedicamentRepository.saveAndFlush(familleMedicament);
        int databaseSizeBeforeDelete = familleMedicamentRepository.findAll().size();

        // Get the familleMedicament
        restFamilleMedicamentMockMvc.perform(delete("/api/famille-medicaments/{id}", familleMedicament.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<FamilleMedicament> familleMedicamentList = familleMedicamentRepository.findAll();
        assertThat(familleMedicamentList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FamilleMedicament.class);
        FamilleMedicament familleMedicament1 = new FamilleMedicament();
        familleMedicament1.setId(1L);
        FamilleMedicament familleMedicament2 = new FamilleMedicament();
        familleMedicament2.setId(familleMedicament1.getId());
        assertThat(familleMedicament1).isEqualTo(familleMedicament2);
        familleMedicament2.setId(2L);
        assertThat(familleMedicament1).isNotEqualTo(familleMedicament2);
        familleMedicament1.setId(null);
        assertThat(familleMedicament1).isNotEqualTo(familleMedicament2);
    }
}
