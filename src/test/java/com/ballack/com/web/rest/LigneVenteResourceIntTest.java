package com.ballack.com.web.rest;

import com.ballack.com.PharmaApp;

import com.ballack.com.domain.LigneVente;
import com.ballack.com.repository.LigneVenteRepository;
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
 * Test class for the LigneVenteResource REST controller.
 *
 * @see LigneVenteResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PharmaApp.class)
public class LigneVenteResourceIntTest {

    private static final Integer DEFAULT_QUANTITE = 1;
    private static final Integer UPDATED_QUANTITE = 2;

    private static final Double DEFAULT_PRIX = 1D;
    private static final Double UPDATED_PRIX = 2D;

    @Autowired
    private LigneVenteRepository ligneVenteRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restLigneVenteMockMvc;

    private LigneVente ligneVente;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final LigneVenteResource ligneVenteResource = new LigneVenteResource(ligneVenteRepository);
        this.restLigneVenteMockMvc = MockMvcBuilders.standaloneSetup(ligneVenteResource)
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
    public static LigneVente createEntity(EntityManager em) {
        LigneVente ligneVente = new LigneVente()
            .quantite(DEFAULT_QUANTITE)
            .prix(DEFAULT_PRIX);
        return ligneVente;
    }

    @Before
    public void initTest() {
        ligneVente = createEntity(em);
    }

    @Test
    @Transactional
    public void createLigneVente() throws Exception {
        int databaseSizeBeforeCreate = ligneVenteRepository.findAll().size();

        // Create the LigneVente
        restLigneVenteMockMvc.perform(post("/api/ligne-ventes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ligneVente)))
            .andExpect(status().isCreated());

        // Validate the LigneVente in the database
        List<LigneVente> ligneVenteList = ligneVenteRepository.findAll();
        assertThat(ligneVenteList).hasSize(databaseSizeBeforeCreate + 1);
        LigneVente testLigneVente = ligneVenteList.get(ligneVenteList.size() - 1);
        assertThat(testLigneVente.getQuantite()).isEqualTo(DEFAULT_QUANTITE);
        assertThat(testLigneVente.getPrix()).isEqualTo(DEFAULT_PRIX);
    }

    @Test
    @Transactional
    public void createLigneVenteWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = ligneVenteRepository.findAll().size();

        // Create the LigneVente with an existing ID
        ligneVente.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLigneVenteMockMvc.perform(post("/api/ligne-ventes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ligneVente)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<LigneVente> ligneVenteList = ligneVenteRepository.findAll();
        assertThat(ligneVenteList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllLigneVentes() throws Exception {
        // Initialize the database
        ligneVenteRepository.saveAndFlush(ligneVente);

        // Get all the ligneVenteList
        restLigneVenteMockMvc.perform(get("/api/ligne-ventes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ligneVente.getId().intValue())))
            .andExpect(jsonPath("$.[*].quantite").value(hasItem(DEFAULT_QUANTITE)))
            .andExpect(jsonPath("$.[*].prix").value(hasItem(DEFAULT_PRIX.doubleValue())));
    }

    @Test
    @Transactional
    public void getLigneVente() throws Exception {
        // Initialize the database
        ligneVenteRepository.saveAndFlush(ligneVente);

        // Get the ligneVente
        restLigneVenteMockMvc.perform(get("/api/ligne-ventes/{id}", ligneVente.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(ligneVente.getId().intValue()))
            .andExpect(jsonPath("$.quantite").value(DEFAULT_QUANTITE))
            .andExpect(jsonPath("$.prix").value(DEFAULT_PRIX.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingLigneVente() throws Exception {
        // Get the ligneVente
        restLigneVenteMockMvc.perform(get("/api/ligne-ventes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLigneVente() throws Exception {
        // Initialize the database
        ligneVenteRepository.saveAndFlush(ligneVente);
        int databaseSizeBeforeUpdate = ligneVenteRepository.findAll().size();

        // Update the ligneVente
        LigneVente updatedLigneVente = ligneVenteRepository.findOne(ligneVente.getId());
        updatedLigneVente
            .quantite(UPDATED_QUANTITE)
            .prix(UPDATED_PRIX);

        restLigneVenteMockMvc.perform(put("/api/ligne-ventes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedLigneVente)))
            .andExpect(status().isOk());

        // Validate the LigneVente in the database
        List<LigneVente> ligneVenteList = ligneVenteRepository.findAll();
        assertThat(ligneVenteList).hasSize(databaseSizeBeforeUpdate);
        LigneVente testLigneVente = ligneVenteList.get(ligneVenteList.size() - 1);
        assertThat(testLigneVente.getQuantite()).isEqualTo(UPDATED_QUANTITE);
        assertThat(testLigneVente.getPrix()).isEqualTo(UPDATED_PRIX);
    }

    @Test
    @Transactional
    public void updateNonExistingLigneVente() throws Exception {
        int databaseSizeBeforeUpdate = ligneVenteRepository.findAll().size();

        // Create the LigneVente

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restLigneVenteMockMvc.perform(put("/api/ligne-ventes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ligneVente)))
            .andExpect(status().isCreated());

        // Validate the LigneVente in the database
        List<LigneVente> ligneVenteList = ligneVenteRepository.findAll();
        assertThat(ligneVenteList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteLigneVente() throws Exception {
        // Initialize the database
        ligneVenteRepository.saveAndFlush(ligneVente);
        int databaseSizeBeforeDelete = ligneVenteRepository.findAll().size();

        // Get the ligneVente
        restLigneVenteMockMvc.perform(delete("/api/ligne-ventes/{id}", ligneVente.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<LigneVente> ligneVenteList = ligneVenteRepository.findAll();
        assertThat(ligneVenteList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(LigneVente.class);
        LigneVente ligneVente1 = new LigneVente();
        ligneVente1.setId(1L);
        LigneVente ligneVente2 = new LigneVente();
        ligneVente2.setId(ligneVente1.getId());
        assertThat(ligneVente1).isEqualTo(ligneVente2);
        ligneVente2.setId(2L);
        assertThat(ligneVente1).isNotEqualTo(ligneVente2);
        ligneVente1.setId(null);
        assertThat(ligneVente1).isNotEqualTo(ligneVente2);
    }
}
