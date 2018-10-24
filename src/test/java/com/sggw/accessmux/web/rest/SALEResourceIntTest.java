package com.sggw.accessmux.web.rest;

import com.sggw.accessmux.AccessMuxApp;

import com.sggw.accessmux.domain.SALE;
import com.sggw.accessmux.repository.SALERepository;
import com.sggw.accessmux.service.SALEService;
import com.sggw.accessmux.service.dto.SALEDTO;
import com.sggw.accessmux.service.mapper.SALEMapper;
import com.sggw.accessmux.web.rest.errors.ExceptionTranslator;

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


import static com.sggw.accessmux.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the SALEResource REST controller.
 *
 * @see SALEResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AccessMuxApp.class)
public class SALEResourceIntTest {

    private static final String DEFAULT_URL = "AAAAAAAAAA";
    private static final String UPDATED_URL = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private SALERepository sALERepository;

    @Autowired
    private SALEMapper sALEMapper;
    
    @Autowired
    private SALEService sALEService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restSALEMockMvc;

    private SALE sALE;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SALEResource sALEResource = new SALEResource(sALEService);
        this.restSALEMockMvc = MockMvcBuilders.standaloneSetup(sALEResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SALE createEntity(EntityManager em) {
        SALE sALE = new SALE()
            .url(DEFAULT_URL)
            .description(DEFAULT_DESCRIPTION);
        return sALE;
    }

    @Before
    public void initTest() {
        sALE = createEntity(em);
    }

    @Test
    @Transactional
    public void createSALE() throws Exception {
        int databaseSizeBeforeCreate = sALERepository.findAll().size();

        // Create the SALE
        SALEDTO sALEDTO = sALEMapper.toDto(sALE);
        restSALEMockMvc.perform(post("/api/sales")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sALEDTO)))
            .andExpect(status().isCreated());

        // Validate the SALE in the database
        List<SALE> sALEList = sALERepository.findAll();
        assertThat(sALEList).hasSize(databaseSizeBeforeCreate + 1);
        SALE testSALE = sALEList.get(sALEList.size() - 1);
        assertThat(testSALE.getUrl()).isEqualTo(DEFAULT_URL);
        assertThat(testSALE.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createSALEWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = sALERepository.findAll().size();

        // Create the SALE with an existing ID
        sALE.setId(1L);
        SALEDTO sALEDTO = sALEMapper.toDto(sALE);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSALEMockMvc.perform(post("/api/sales")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sALEDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SALE in the database
        List<SALE> sALEList = sALERepository.findAll();
        assertThat(sALEList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkUrlIsRequired() throws Exception {
        int databaseSizeBeforeTest = sALERepository.findAll().size();
        // set the field null
        sALE.setUrl(null);

        // Create the SALE, which fails.
        SALEDTO sALEDTO = sALEMapper.toDto(sALE);

        restSALEMockMvc.perform(post("/api/sales")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sALEDTO)))
            .andExpect(status().isBadRequest());

        List<SALE> sALEList = sALERepository.findAll();
        assertThat(sALEList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSALES() throws Exception {
        // Initialize the database
        sALERepository.saveAndFlush(sALE);

        // Get all the sALEList
        restSALEMockMvc.perform(get("/api/sales?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sALE.getId().intValue())))
            .andExpect(jsonPath("$.[*].url").value(hasItem(DEFAULT_URL.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }
    
    @Test
    @Transactional
    public void getSALE() throws Exception {
        // Initialize the database
        sALERepository.saveAndFlush(sALE);

        // Get the sALE
        restSALEMockMvc.perform(get("/api/sales/{id}", sALE.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(sALE.getId().intValue()))
            .andExpect(jsonPath("$.url").value(DEFAULT_URL.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingSALE() throws Exception {
        // Get the sALE
        restSALEMockMvc.perform(get("/api/sales/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSALE() throws Exception {
        // Initialize the database
        sALERepository.saveAndFlush(sALE);

        int databaseSizeBeforeUpdate = sALERepository.findAll().size();

        // Update the sALE
        SALE updatedSALE = sALERepository.findById(sALE.getId()).get();
        // Disconnect from session so that the updates on updatedSALE are not directly saved in db
        em.detach(updatedSALE);
        updatedSALE
            .url(UPDATED_URL)
            .description(UPDATED_DESCRIPTION);
        SALEDTO sALEDTO = sALEMapper.toDto(updatedSALE);

        restSALEMockMvc.perform(put("/api/sales")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sALEDTO)))
            .andExpect(status().isOk());

        // Validate the SALE in the database
        List<SALE> sALEList = sALERepository.findAll();
        assertThat(sALEList).hasSize(databaseSizeBeforeUpdate);
        SALE testSALE = sALEList.get(sALEList.size() - 1);
        assertThat(testSALE.getUrl()).isEqualTo(UPDATED_URL);
        assertThat(testSALE.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingSALE() throws Exception {
        int databaseSizeBeforeUpdate = sALERepository.findAll().size();

        // Create the SALE
        SALEDTO sALEDTO = sALEMapper.toDto(sALE);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSALEMockMvc.perform(put("/api/sales")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sALEDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SALE in the database
        List<SALE> sALEList = sALERepository.findAll();
        assertThat(sALEList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSALE() throws Exception {
        // Initialize the database
        sALERepository.saveAndFlush(sALE);

        int databaseSizeBeforeDelete = sALERepository.findAll().size();

        // Get the sALE
        restSALEMockMvc.perform(delete("/api/sales/{id}", sALE.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<SALE> sALEList = sALERepository.findAll();
        assertThat(sALEList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SALE.class);
        SALE sALE1 = new SALE();
        sALE1.setId(1L);
        SALE sALE2 = new SALE();
        sALE2.setId(sALE1.getId());
        assertThat(sALE1).isEqualTo(sALE2);
        sALE2.setId(2L);
        assertThat(sALE1).isNotEqualTo(sALE2);
        sALE1.setId(null);
        assertThat(sALE1).isNotEqualTo(sALE2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SALEDTO.class);
        SALEDTO sALEDTO1 = new SALEDTO();
        sALEDTO1.setId(1L);
        SALEDTO sALEDTO2 = new SALEDTO();
        assertThat(sALEDTO1).isNotEqualTo(sALEDTO2);
        sALEDTO2.setId(sALEDTO1.getId());
        assertThat(sALEDTO1).isEqualTo(sALEDTO2);
        sALEDTO2.setId(2L);
        assertThat(sALEDTO1).isNotEqualTo(sALEDTO2);
        sALEDTO1.setId(null);
        assertThat(sALEDTO1).isNotEqualTo(sALEDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(sALEMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(sALEMapper.fromId(null)).isNull();
    }
}
