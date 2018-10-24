package com.sggw.accessmux.web.rest;

import com.sggw.accessmux.AccessMuxApp;

import com.sggw.accessmux.domain.HR;
import com.sggw.accessmux.repository.HRRepository;
import com.sggw.accessmux.service.HRService;
import com.sggw.accessmux.service.dto.HRDTO;
import com.sggw.accessmux.service.mapper.HRMapper;
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
 * Test class for the HRResource REST controller.
 *
 * @see HRResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AccessMuxApp.class)
public class HRResourceIntTest {

    private static final String DEFAULT_URL = "AAAAAAAAAA";
    private static final String UPDATED_URL = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private HRRepository hRRepository;

    @Autowired
    private HRMapper hRMapper;
    
    @Autowired
    private HRService hRService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restHRMockMvc;

    private HR hR;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final HRResource hRResource = new HRResource(hRService);
        this.restHRMockMvc = MockMvcBuilders.standaloneSetup(hRResource)
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
    public static HR createEntity(EntityManager em) {
        HR hR = new HR()
            .url(DEFAULT_URL)
            .description(DEFAULT_DESCRIPTION);
        return hR;
    }

    @Before
    public void initTest() {
        hR = createEntity(em);
    }

    @Test
    @Transactional
    public void createHR() throws Exception {
        int databaseSizeBeforeCreate = hRRepository.findAll().size();

        // Create the HR
        HRDTO hRDTO = hRMapper.toDto(hR);
        restHRMockMvc.perform(post("/api/hrs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(hRDTO)))
            .andExpect(status().isCreated());

        // Validate the HR in the database
        List<HR> hRList = hRRepository.findAll();
        assertThat(hRList).hasSize(databaseSizeBeforeCreate + 1);
        HR testHR = hRList.get(hRList.size() - 1);
        assertThat(testHR.getUrl()).isEqualTo(DEFAULT_URL);
        assertThat(testHR.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createHRWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = hRRepository.findAll().size();

        // Create the HR with an existing ID
        hR.setId(1L);
        HRDTO hRDTO = hRMapper.toDto(hR);

        // An entity with an existing ID cannot be created, so this API call must fail
        restHRMockMvc.perform(post("/api/hrs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(hRDTO)))
            .andExpect(status().isBadRequest());

        // Validate the HR in the database
        List<HR> hRList = hRRepository.findAll();
        assertThat(hRList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkUrlIsRequired() throws Exception {
        int databaseSizeBeforeTest = hRRepository.findAll().size();
        // set the field null
        hR.setUrl(null);

        // Create the HR, which fails.
        HRDTO hRDTO = hRMapper.toDto(hR);

        restHRMockMvc.perform(post("/api/hrs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(hRDTO)))
            .andExpect(status().isBadRequest());

        List<HR> hRList = hRRepository.findAll();
        assertThat(hRList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllHRS() throws Exception {
        // Initialize the database
        hRRepository.saveAndFlush(hR);

        // Get all the hRList
        restHRMockMvc.perform(get("/api/hrs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(hR.getId().intValue())))
            .andExpect(jsonPath("$.[*].url").value(hasItem(DEFAULT_URL.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }
    
    @Test
    @Transactional
    public void getHR() throws Exception {
        // Initialize the database
        hRRepository.saveAndFlush(hR);

        // Get the hR
        restHRMockMvc.perform(get("/api/hrs/{id}", hR.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(hR.getId().intValue()))
            .andExpect(jsonPath("$.url").value(DEFAULT_URL.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingHR() throws Exception {
        // Get the hR
        restHRMockMvc.perform(get("/api/hrs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateHR() throws Exception {
        // Initialize the database
        hRRepository.saveAndFlush(hR);

        int databaseSizeBeforeUpdate = hRRepository.findAll().size();

        // Update the hR
        HR updatedHR = hRRepository.findById(hR.getId()).get();
        // Disconnect from session so that the updates on updatedHR are not directly saved in db
        em.detach(updatedHR);
        updatedHR
            .url(UPDATED_URL)
            .description(UPDATED_DESCRIPTION);
        HRDTO hRDTO = hRMapper.toDto(updatedHR);

        restHRMockMvc.perform(put("/api/hrs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(hRDTO)))
            .andExpect(status().isOk());

        // Validate the HR in the database
        List<HR> hRList = hRRepository.findAll();
        assertThat(hRList).hasSize(databaseSizeBeforeUpdate);
        HR testHR = hRList.get(hRList.size() - 1);
        assertThat(testHR.getUrl()).isEqualTo(UPDATED_URL);
        assertThat(testHR.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingHR() throws Exception {
        int databaseSizeBeforeUpdate = hRRepository.findAll().size();

        // Create the HR
        HRDTO hRDTO = hRMapper.toDto(hR);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restHRMockMvc.perform(put("/api/hrs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(hRDTO)))
            .andExpect(status().isBadRequest());

        // Validate the HR in the database
        List<HR> hRList = hRRepository.findAll();
        assertThat(hRList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteHR() throws Exception {
        // Initialize the database
        hRRepository.saveAndFlush(hR);

        int databaseSizeBeforeDelete = hRRepository.findAll().size();

        // Get the hR
        restHRMockMvc.perform(delete("/api/hrs/{id}", hR.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<HR> hRList = hRRepository.findAll();
        assertThat(hRList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(HR.class);
        HR hR1 = new HR();
        hR1.setId(1L);
        HR hR2 = new HR();
        hR2.setId(hR1.getId());
        assertThat(hR1).isEqualTo(hR2);
        hR2.setId(2L);
        assertThat(hR1).isNotEqualTo(hR2);
        hR1.setId(null);
        assertThat(hR1).isNotEqualTo(hR2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(HRDTO.class);
        HRDTO hRDTO1 = new HRDTO();
        hRDTO1.setId(1L);
        HRDTO hRDTO2 = new HRDTO();
        assertThat(hRDTO1).isNotEqualTo(hRDTO2);
        hRDTO2.setId(hRDTO1.getId());
        assertThat(hRDTO1).isEqualTo(hRDTO2);
        hRDTO2.setId(2L);
        assertThat(hRDTO1).isNotEqualTo(hRDTO2);
        hRDTO1.setId(null);
        assertThat(hRDTO1).isNotEqualTo(hRDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(hRMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(hRMapper.fromId(null)).isNull();
    }
}
