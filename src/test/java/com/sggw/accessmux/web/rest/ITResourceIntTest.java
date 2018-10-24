package com.sggw.accessmux.web.rest;

import com.sggw.accessmux.AccessMuxApp;

import com.sggw.accessmux.domain.IT;
import com.sggw.accessmux.repository.ITRepository;
import com.sggw.accessmux.service.ITService;
import com.sggw.accessmux.service.dto.ITDTO;
import com.sggw.accessmux.service.mapper.ITMapper;
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
 * Test class for the ITResource REST controller.
 *
 * @see ITResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AccessMuxApp.class)
public class ITResourceIntTest {

    private static final String DEFAULT_URL = "AAAAAAAAAA";
    private static final String UPDATED_URL = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private ITRepository iTRepository;

    @Autowired
    private ITMapper iTMapper;
    
    @Autowired
    private ITService iTService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restITMockMvc;

    private IT iT;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ITResource iTResource = new ITResource(iTService);
        this.restITMockMvc = MockMvcBuilders.standaloneSetup(iTResource)
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
    public static IT createEntity(EntityManager em) {
        IT iT = new IT()
            .url(DEFAULT_URL)
            .description(DEFAULT_DESCRIPTION);
        return iT;
    }

    @Before
    public void initTest() {
        iT = createEntity(em);
    }

    @Test
    @Transactional
    public void createIT() throws Exception {
        int databaseSizeBeforeCreate = iTRepository.findAll().size();

        // Create the IT
        ITDTO iTDTO = iTMapper.toDto(iT);
        restITMockMvc.perform(post("/api/its")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(iTDTO)))
            .andExpect(status().isCreated());

        // Validate the IT in the database
        List<IT> iTList = iTRepository.findAll();
        assertThat(iTList).hasSize(databaseSizeBeforeCreate + 1);
        IT testIT = iTList.get(iTList.size() - 1);
        assertThat(testIT.getUrl()).isEqualTo(DEFAULT_URL);
        assertThat(testIT.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createITWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = iTRepository.findAll().size();

        // Create the IT with an existing ID
        iT.setId(1L);
        ITDTO iTDTO = iTMapper.toDto(iT);

        // An entity with an existing ID cannot be created, so this API call must fail
        restITMockMvc.perform(post("/api/its")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(iTDTO)))
            .andExpect(status().isBadRequest());

        // Validate the IT in the database
        List<IT> iTList = iTRepository.findAll();
        assertThat(iTList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkUrlIsRequired() throws Exception {
        int databaseSizeBeforeTest = iTRepository.findAll().size();
        // set the field null
        iT.setUrl(null);

        // Create the IT, which fails.
        ITDTO iTDTO = iTMapper.toDto(iT);

        restITMockMvc.perform(post("/api/its")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(iTDTO)))
            .andExpect(status().isBadRequest());

        List<IT> iTList = iTRepository.findAll();
        assertThat(iTList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllITS() throws Exception {
        // Initialize the database
        iTRepository.saveAndFlush(iT);

        // Get all the iTList
        restITMockMvc.perform(get("/api/its?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(iT.getId().intValue())))
            .andExpect(jsonPath("$.[*].url").value(hasItem(DEFAULT_URL.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }
    
    @Test
    @Transactional
    public void getIT() throws Exception {
        // Initialize the database
        iTRepository.saveAndFlush(iT);

        // Get the iT
        restITMockMvc.perform(get("/api/its/{id}", iT.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(iT.getId().intValue()))
            .andExpect(jsonPath("$.url").value(DEFAULT_URL.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingIT() throws Exception {
        // Get the iT
        restITMockMvc.perform(get("/api/its/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateIT() throws Exception {
        // Initialize the database
        iTRepository.saveAndFlush(iT);

        int databaseSizeBeforeUpdate = iTRepository.findAll().size();

        // Update the iT
        IT updatedIT = iTRepository.findById(iT.getId()).get();
        // Disconnect from session so that the updates on updatedIT are not directly saved in db
        em.detach(updatedIT);
        updatedIT
            .url(UPDATED_URL)
            .description(UPDATED_DESCRIPTION);
        ITDTO iTDTO = iTMapper.toDto(updatedIT);

        restITMockMvc.perform(put("/api/its")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(iTDTO)))
            .andExpect(status().isOk());

        // Validate the IT in the database
        List<IT> iTList = iTRepository.findAll();
        assertThat(iTList).hasSize(databaseSizeBeforeUpdate);
        IT testIT = iTList.get(iTList.size() - 1);
        assertThat(testIT.getUrl()).isEqualTo(UPDATED_URL);
        assertThat(testIT.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingIT() throws Exception {
        int databaseSizeBeforeUpdate = iTRepository.findAll().size();

        // Create the IT
        ITDTO iTDTO = iTMapper.toDto(iT);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restITMockMvc.perform(put("/api/its")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(iTDTO)))
            .andExpect(status().isBadRequest());

        // Validate the IT in the database
        List<IT> iTList = iTRepository.findAll();
        assertThat(iTList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteIT() throws Exception {
        // Initialize the database
        iTRepository.saveAndFlush(iT);

        int databaseSizeBeforeDelete = iTRepository.findAll().size();

        // Get the iT
        restITMockMvc.perform(delete("/api/its/{id}", iT.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<IT> iTList = iTRepository.findAll();
        assertThat(iTList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(IT.class);
        IT iT1 = new IT();
        iT1.setId(1L);
        IT iT2 = new IT();
        iT2.setId(iT1.getId());
        assertThat(iT1).isEqualTo(iT2);
        iT2.setId(2L);
        assertThat(iT1).isNotEqualTo(iT2);
        iT1.setId(null);
        assertThat(iT1).isNotEqualTo(iT2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ITDTO.class);
        ITDTO iTDTO1 = new ITDTO();
        iTDTO1.setId(1L);
        ITDTO iTDTO2 = new ITDTO();
        assertThat(iTDTO1).isNotEqualTo(iTDTO2);
        iTDTO2.setId(iTDTO1.getId());
        assertThat(iTDTO1).isEqualTo(iTDTO2);
        iTDTO2.setId(2L);
        assertThat(iTDTO1).isNotEqualTo(iTDTO2);
        iTDTO1.setId(null);
        assertThat(iTDTO1).isNotEqualTo(iTDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(iTMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(iTMapper.fromId(null)).isNull();
    }
}
