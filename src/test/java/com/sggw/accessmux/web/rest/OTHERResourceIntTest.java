package com.sggw.accessmux.web.rest;

import com.sggw.accessmux.AccessMuxApp;

import com.sggw.accessmux.domain.OTHER;
import com.sggw.accessmux.repository.OTHERRepository;
import com.sggw.accessmux.service.OTHERService;
import com.sggw.accessmux.service.dto.OTHERDTO;
import com.sggw.accessmux.service.mapper.OTHERMapper;
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
 * Test class for the OTHERResource REST controller.
 *
 * @see OTHERResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AccessMuxApp.class)
public class OTHERResourceIntTest {

    private static final String DEFAULT_URL = "AAAAAAAAAA";
    private static final String UPDATED_URL = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private OTHERRepository oTHERRepository;

    @Autowired
    private OTHERMapper oTHERMapper;
    
    @Autowired
    private OTHERService oTHERService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restOTHERMockMvc;

    private OTHER oTHER;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final OTHERResource oTHERResource = new OTHERResource(oTHERService);
        this.restOTHERMockMvc = MockMvcBuilders.standaloneSetup(oTHERResource)
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
    public static OTHER createEntity(EntityManager em) {
        OTHER oTHER = new OTHER()
            .url(DEFAULT_URL)
            .description(DEFAULT_DESCRIPTION);
        return oTHER;
    }

    @Before
    public void initTest() {
        oTHER = createEntity(em);
    }

    @Test
    @Transactional
    public void createOTHER() throws Exception {
        int databaseSizeBeforeCreate = oTHERRepository.findAll().size();

        // Create the OTHER
        OTHERDTO oTHERDTO = oTHERMapper.toDto(oTHER);
        restOTHERMockMvc.perform(post("/api/others")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(oTHERDTO)))
            .andExpect(status().isCreated());

        // Validate the OTHER in the database
        List<OTHER> oTHERList = oTHERRepository.findAll();
        assertThat(oTHERList).hasSize(databaseSizeBeforeCreate + 1);
        OTHER testOTHER = oTHERList.get(oTHERList.size() - 1);
        assertThat(testOTHER.getUrl()).isEqualTo(DEFAULT_URL);
        assertThat(testOTHER.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createOTHERWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = oTHERRepository.findAll().size();

        // Create the OTHER with an existing ID
        oTHER.setId(1L);
        OTHERDTO oTHERDTO = oTHERMapper.toDto(oTHER);

        // An entity with an existing ID cannot be created, so this API call must fail
        restOTHERMockMvc.perform(post("/api/others")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(oTHERDTO)))
            .andExpect(status().isBadRequest());

        // Validate the OTHER in the database
        List<OTHER> oTHERList = oTHERRepository.findAll();
        assertThat(oTHERList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkUrlIsRequired() throws Exception {
        int databaseSizeBeforeTest = oTHERRepository.findAll().size();
        // set the field null
        oTHER.setUrl(null);

        // Create the OTHER, which fails.
        OTHERDTO oTHERDTO = oTHERMapper.toDto(oTHER);

        restOTHERMockMvc.perform(post("/api/others")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(oTHERDTO)))
            .andExpect(status().isBadRequest());

        List<OTHER> oTHERList = oTHERRepository.findAll();
        assertThat(oTHERList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllOTHERS() throws Exception {
        // Initialize the database
        oTHERRepository.saveAndFlush(oTHER);

        // Get all the oTHERList
        restOTHERMockMvc.perform(get("/api/others?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(oTHER.getId().intValue())))
            .andExpect(jsonPath("$.[*].url").value(hasItem(DEFAULT_URL.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }
    
    @Test
    @Transactional
    public void getOTHER() throws Exception {
        // Initialize the database
        oTHERRepository.saveAndFlush(oTHER);

        // Get the oTHER
        restOTHERMockMvc.perform(get("/api/others/{id}", oTHER.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(oTHER.getId().intValue()))
            .andExpect(jsonPath("$.url").value(DEFAULT_URL.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingOTHER() throws Exception {
        // Get the oTHER
        restOTHERMockMvc.perform(get("/api/others/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOTHER() throws Exception {
        // Initialize the database
        oTHERRepository.saveAndFlush(oTHER);

        int databaseSizeBeforeUpdate = oTHERRepository.findAll().size();

        // Update the oTHER
        OTHER updatedOTHER = oTHERRepository.findById(oTHER.getId()).get();
        // Disconnect from session so that the updates on updatedOTHER are not directly saved in db
        em.detach(updatedOTHER);
        updatedOTHER
            .url(UPDATED_URL)
            .description(UPDATED_DESCRIPTION);
        OTHERDTO oTHERDTO = oTHERMapper.toDto(updatedOTHER);

        restOTHERMockMvc.perform(put("/api/others")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(oTHERDTO)))
            .andExpect(status().isOk());

        // Validate the OTHER in the database
        List<OTHER> oTHERList = oTHERRepository.findAll();
        assertThat(oTHERList).hasSize(databaseSizeBeforeUpdate);
        OTHER testOTHER = oTHERList.get(oTHERList.size() - 1);
        assertThat(testOTHER.getUrl()).isEqualTo(UPDATED_URL);
        assertThat(testOTHER.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingOTHER() throws Exception {
        int databaseSizeBeforeUpdate = oTHERRepository.findAll().size();

        // Create the OTHER
        OTHERDTO oTHERDTO = oTHERMapper.toDto(oTHER);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOTHERMockMvc.perform(put("/api/others")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(oTHERDTO)))
            .andExpect(status().isBadRequest());

        // Validate the OTHER in the database
        List<OTHER> oTHERList = oTHERRepository.findAll();
        assertThat(oTHERList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteOTHER() throws Exception {
        // Initialize the database
        oTHERRepository.saveAndFlush(oTHER);

        int databaseSizeBeforeDelete = oTHERRepository.findAll().size();

        // Get the oTHER
        restOTHERMockMvc.perform(delete("/api/others/{id}", oTHER.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<OTHER> oTHERList = oTHERRepository.findAll();
        assertThat(oTHERList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(OTHER.class);
        OTHER oTHER1 = new OTHER();
        oTHER1.setId(1L);
        OTHER oTHER2 = new OTHER();
        oTHER2.setId(oTHER1.getId());
        assertThat(oTHER1).isEqualTo(oTHER2);
        oTHER2.setId(2L);
        assertThat(oTHER1).isNotEqualTo(oTHER2);
        oTHER1.setId(null);
        assertThat(oTHER1).isNotEqualTo(oTHER2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(OTHERDTO.class);
        OTHERDTO oTHERDTO1 = new OTHERDTO();
        oTHERDTO1.setId(1L);
        OTHERDTO oTHERDTO2 = new OTHERDTO();
        assertThat(oTHERDTO1).isNotEqualTo(oTHERDTO2);
        oTHERDTO2.setId(oTHERDTO1.getId());
        assertThat(oTHERDTO1).isEqualTo(oTHERDTO2);
        oTHERDTO2.setId(2L);
        assertThat(oTHERDTO1).isNotEqualTo(oTHERDTO2);
        oTHERDTO1.setId(null);
        assertThat(oTHERDTO1).isNotEqualTo(oTHERDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(oTHERMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(oTHERMapper.fromId(null)).isNull();
    }
}
