package com.sggw.accessmux.web.rest;

import com.sggw.accessmux.AccessMuxApp;

import com.sggw.accessmux.domain.FINANCES;
import com.sggw.accessmux.repository.FINANCESRepository;
import com.sggw.accessmux.service.FINANCESService;
import com.sggw.accessmux.service.dto.FINANCESDTO;
import com.sggw.accessmux.service.mapper.FINANCESMapper;
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
 * Test class for the FINANCESResource REST controller.
 *
 * @see FINANCESResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AccessMuxApp.class)
public class FINANCESResourceIntTest {

    private static final String DEFAULT_URL = "AAAAAAAAAA";
    private static final String UPDATED_URL = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private FINANCESRepository fINANCESRepository;

    @Autowired
    private FINANCESMapper fINANCESMapper;
    
    @Autowired
    private FINANCESService fINANCESService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restFINANCESMockMvc;

    private FINANCES fINANCES;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final FINANCESResource fINANCESResource = new FINANCESResource(fINANCESService);
        this.restFINANCESMockMvc = MockMvcBuilders.standaloneSetup(fINANCESResource)
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
    public static FINANCES createEntity(EntityManager em) {
        FINANCES fINANCES = new FINANCES()
            .url(DEFAULT_URL)
            .description(DEFAULT_DESCRIPTION);
        return fINANCES;
    }

    @Before
    public void initTest() {
        fINANCES = createEntity(em);
    }

    @Test
    @Transactional
    public void createFINANCES() throws Exception {
        int databaseSizeBeforeCreate = fINANCESRepository.findAll().size();

        // Create the FINANCES
        FINANCESDTO fINANCESDTO = fINANCESMapper.toDto(fINANCES);
        restFINANCESMockMvc.perform(post("/api/finances")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fINANCESDTO)))
            .andExpect(status().isCreated());

        // Validate the FINANCES in the database
        List<FINANCES> fINANCESList = fINANCESRepository.findAll();
        assertThat(fINANCESList).hasSize(databaseSizeBeforeCreate + 1);
        FINANCES testFINANCES = fINANCESList.get(fINANCESList.size() - 1);
        assertThat(testFINANCES.getUrl()).isEqualTo(DEFAULT_URL);
        assertThat(testFINANCES.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createFINANCESWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = fINANCESRepository.findAll().size();

        // Create the FINANCES with an existing ID
        fINANCES.setId(1L);
        FINANCESDTO fINANCESDTO = fINANCESMapper.toDto(fINANCES);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFINANCESMockMvc.perform(post("/api/finances")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fINANCESDTO)))
            .andExpect(status().isBadRequest());

        // Validate the FINANCES in the database
        List<FINANCES> fINANCESList = fINANCESRepository.findAll();
        assertThat(fINANCESList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkUrlIsRequired() throws Exception {
        int databaseSizeBeforeTest = fINANCESRepository.findAll().size();
        // set the field null
        fINANCES.setUrl(null);

        // Create the FINANCES, which fails.
        FINANCESDTO fINANCESDTO = fINANCESMapper.toDto(fINANCES);

        restFINANCESMockMvc.perform(post("/api/finances")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fINANCESDTO)))
            .andExpect(status().isBadRequest());

        List<FINANCES> fINANCESList = fINANCESRepository.findAll();
        assertThat(fINANCESList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllFINANCES() throws Exception {
        // Initialize the database
        fINANCESRepository.saveAndFlush(fINANCES);

        // Get all the fINANCESList
        restFINANCESMockMvc.perform(get("/api/finances?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(fINANCES.getId().intValue())))
            .andExpect(jsonPath("$.[*].url").value(hasItem(DEFAULT_URL.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }
    
    @Test
    @Transactional
    public void getFINANCES() throws Exception {
        // Initialize the database
        fINANCESRepository.saveAndFlush(fINANCES);

        // Get the fINANCES
        restFINANCESMockMvc.perform(get("/api/finances/{id}", fINANCES.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(fINANCES.getId().intValue()))
            .andExpect(jsonPath("$.url").value(DEFAULT_URL.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingFINANCES() throws Exception {
        // Get the fINANCES
        restFINANCESMockMvc.perform(get("/api/finances/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFINANCES() throws Exception {
        // Initialize the database
        fINANCESRepository.saveAndFlush(fINANCES);

        int databaseSizeBeforeUpdate = fINANCESRepository.findAll().size();

        // Update the fINANCES
        FINANCES updatedFINANCES = fINANCESRepository.findById(fINANCES.getId()).get();
        // Disconnect from session so that the updates on updatedFINANCES are not directly saved in db
        em.detach(updatedFINANCES);
        updatedFINANCES
            .url(UPDATED_URL)
            .description(UPDATED_DESCRIPTION);
        FINANCESDTO fINANCESDTO = fINANCESMapper.toDto(updatedFINANCES);

        restFINANCESMockMvc.perform(put("/api/finances")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fINANCESDTO)))
            .andExpect(status().isOk());

        // Validate the FINANCES in the database
        List<FINANCES> fINANCESList = fINANCESRepository.findAll();
        assertThat(fINANCESList).hasSize(databaseSizeBeforeUpdate);
        FINANCES testFINANCES = fINANCESList.get(fINANCESList.size() - 1);
        assertThat(testFINANCES.getUrl()).isEqualTo(UPDATED_URL);
        assertThat(testFINANCES.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingFINANCES() throws Exception {
        int databaseSizeBeforeUpdate = fINANCESRepository.findAll().size();

        // Create the FINANCES
        FINANCESDTO fINANCESDTO = fINANCESMapper.toDto(fINANCES);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFINANCESMockMvc.perform(put("/api/finances")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fINANCESDTO)))
            .andExpect(status().isBadRequest());

        // Validate the FINANCES in the database
        List<FINANCES> fINANCESList = fINANCESRepository.findAll();
        assertThat(fINANCESList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteFINANCES() throws Exception {
        // Initialize the database
        fINANCESRepository.saveAndFlush(fINANCES);

        int databaseSizeBeforeDelete = fINANCESRepository.findAll().size();

        // Get the fINANCES
        restFINANCESMockMvc.perform(delete("/api/finances/{id}", fINANCES.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<FINANCES> fINANCESList = fINANCESRepository.findAll();
        assertThat(fINANCESList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FINANCES.class);
        FINANCES fINANCES1 = new FINANCES();
        fINANCES1.setId(1L);
        FINANCES fINANCES2 = new FINANCES();
        fINANCES2.setId(fINANCES1.getId());
        assertThat(fINANCES1).isEqualTo(fINANCES2);
        fINANCES2.setId(2L);
        assertThat(fINANCES1).isNotEqualTo(fINANCES2);
        fINANCES1.setId(null);
        assertThat(fINANCES1).isNotEqualTo(fINANCES2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(FINANCESDTO.class);
        FINANCESDTO fINANCESDTO1 = new FINANCESDTO();
        fINANCESDTO1.setId(1L);
        FINANCESDTO fINANCESDTO2 = new FINANCESDTO();
        assertThat(fINANCESDTO1).isNotEqualTo(fINANCESDTO2);
        fINANCESDTO2.setId(fINANCESDTO1.getId());
        assertThat(fINANCESDTO1).isEqualTo(fINANCESDTO2);
        fINANCESDTO2.setId(2L);
        assertThat(fINANCESDTO1).isNotEqualTo(fINANCESDTO2);
        fINANCESDTO1.setId(null);
        assertThat(fINANCESDTO1).isNotEqualTo(fINANCESDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(fINANCESMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(fINANCESMapper.fromId(null)).isNull();
    }
}
