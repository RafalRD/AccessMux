package com.sggw.accessmux.web.rest;

import com.sggw.accessmux.AccessMuxApp;

import com.sggw.accessmux.domain.MARKETING;
import com.sggw.accessmux.repository.MARKETINGRepository;
import com.sggw.accessmux.service.MARKETINGService;
import com.sggw.accessmux.service.dto.MARKETINGDTO;
import com.sggw.accessmux.service.mapper.MARKETINGMapper;
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
 * Test class for the MARKETINGResource REST controller.
 *
 * @see MARKETINGResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AccessMuxApp.class)
public class MARKETINGResourceIntTest {

    private static final String DEFAULT_URL = "AAAAAAAAAA";
    private static final String UPDATED_URL = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private MARKETINGRepository mARKETINGRepository;

    @Autowired
    private MARKETINGMapper mARKETINGMapper;
    
    @Autowired
    private MARKETINGService mARKETINGService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restMARKETINGMockMvc;

    private MARKETING mARKETING;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MARKETINGResource mARKETINGResource = new MARKETINGResource(mARKETINGService);
        this.restMARKETINGMockMvc = MockMvcBuilders.standaloneSetup(mARKETINGResource)
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
    public static MARKETING createEntity(EntityManager em) {
        MARKETING mARKETING = new MARKETING()
            .url(DEFAULT_URL)
            .description(DEFAULT_DESCRIPTION);
        return mARKETING;
    }

    @Before
    public void initTest() {
        mARKETING = createEntity(em);
    }

    @Test
    @Transactional
    public void createMARKETING() throws Exception {
        int databaseSizeBeforeCreate = mARKETINGRepository.findAll().size();

        // Create the MARKETING
        MARKETINGDTO mARKETINGDTO = mARKETINGMapper.toDto(mARKETING);
        restMARKETINGMockMvc.perform(post("/api/marketings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mARKETINGDTO)))
            .andExpect(status().isCreated());

        // Validate the MARKETING in the database
        List<MARKETING> mARKETINGList = mARKETINGRepository.findAll();
        assertThat(mARKETINGList).hasSize(databaseSizeBeforeCreate + 1);
        MARKETING testMARKETING = mARKETINGList.get(mARKETINGList.size() - 1);
        assertThat(testMARKETING.getUrl()).isEqualTo(DEFAULT_URL);
        assertThat(testMARKETING.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createMARKETINGWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = mARKETINGRepository.findAll().size();

        // Create the MARKETING with an existing ID
        mARKETING.setId(1L);
        MARKETINGDTO mARKETINGDTO = mARKETINGMapper.toDto(mARKETING);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMARKETINGMockMvc.perform(post("/api/marketings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mARKETINGDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MARKETING in the database
        List<MARKETING> mARKETINGList = mARKETINGRepository.findAll();
        assertThat(mARKETINGList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkUrlIsRequired() throws Exception {
        int databaseSizeBeforeTest = mARKETINGRepository.findAll().size();
        // set the field null
        mARKETING.setUrl(null);

        // Create the MARKETING, which fails.
        MARKETINGDTO mARKETINGDTO = mARKETINGMapper.toDto(mARKETING);

        restMARKETINGMockMvc.perform(post("/api/marketings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mARKETINGDTO)))
            .andExpect(status().isBadRequest());

        List<MARKETING> mARKETINGList = mARKETINGRepository.findAll();
        assertThat(mARKETINGList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMARKETINGS() throws Exception {
        // Initialize the database
        mARKETINGRepository.saveAndFlush(mARKETING);

        // Get all the mARKETINGList
        restMARKETINGMockMvc.perform(get("/api/marketings?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mARKETING.getId().intValue())))
            .andExpect(jsonPath("$.[*].url").value(hasItem(DEFAULT_URL.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }
    
    @Test
    @Transactional
    public void getMARKETING() throws Exception {
        // Initialize the database
        mARKETINGRepository.saveAndFlush(mARKETING);

        // Get the mARKETING
        restMARKETINGMockMvc.perform(get("/api/marketings/{id}", mARKETING.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(mARKETING.getId().intValue()))
            .andExpect(jsonPath("$.url").value(DEFAULT_URL.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingMARKETING() throws Exception {
        // Get the mARKETING
        restMARKETINGMockMvc.perform(get("/api/marketings/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMARKETING() throws Exception {
        // Initialize the database
        mARKETINGRepository.saveAndFlush(mARKETING);

        int databaseSizeBeforeUpdate = mARKETINGRepository.findAll().size();

        // Update the mARKETING
        MARKETING updatedMARKETING = mARKETINGRepository.findById(mARKETING.getId()).get();
        // Disconnect from session so that the updates on updatedMARKETING are not directly saved in db
        em.detach(updatedMARKETING);
        updatedMARKETING
            .url(UPDATED_URL)
            .description(UPDATED_DESCRIPTION);
        MARKETINGDTO mARKETINGDTO = mARKETINGMapper.toDto(updatedMARKETING);

        restMARKETINGMockMvc.perform(put("/api/marketings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mARKETINGDTO)))
            .andExpect(status().isOk());

        // Validate the MARKETING in the database
        List<MARKETING> mARKETINGList = mARKETINGRepository.findAll();
        assertThat(mARKETINGList).hasSize(databaseSizeBeforeUpdate);
        MARKETING testMARKETING = mARKETINGList.get(mARKETINGList.size() - 1);
        assertThat(testMARKETING.getUrl()).isEqualTo(UPDATED_URL);
        assertThat(testMARKETING.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingMARKETING() throws Exception {
        int databaseSizeBeforeUpdate = mARKETINGRepository.findAll().size();

        // Create the MARKETING
        MARKETINGDTO mARKETINGDTO = mARKETINGMapper.toDto(mARKETING);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMARKETINGMockMvc.perform(put("/api/marketings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mARKETINGDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MARKETING in the database
        List<MARKETING> mARKETINGList = mARKETINGRepository.findAll();
        assertThat(mARKETINGList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMARKETING() throws Exception {
        // Initialize the database
        mARKETINGRepository.saveAndFlush(mARKETING);

        int databaseSizeBeforeDelete = mARKETINGRepository.findAll().size();

        // Get the mARKETING
        restMARKETINGMockMvc.perform(delete("/api/marketings/{id}", mARKETING.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<MARKETING> mARKETINGList = mARKETINGRepository.findAll();
        assertThat(mARKETINGList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MARKETING.class);
        MARKETING mARKETING1 = new MARKETING();
        mARKETING1.setId(1L);
        MARKETING mARKETING2 = new MARKETING();
        mARKETING2.setId(mARKETING1.getId());
        assertThat(mARKETING1).isEqualTo(mARKETING2);
        mARKETING2.setId(2L);
        assertThat(mARKETING1).isNotEqualTo(mARKETING2);
        mARKETING1.setId(null);
        assertThat(mARKETING1).isNotEqualTo(mARKETING2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MARKETINGDTO.class);
        MARKETINGDTO mARKETINGDTO1 = new MARKETINGDTO();
        mARKETINGDTO1.setId(1L);
        MARKETINGDTO mARKETINGDTO2 = new MARKETINGDTO();
        assertThat(mARKETINGDTO1).isNotEqualTo(mARKETINGDTO2);
        mARKETINGDTO2.setId(mARKETINGDTO1.getId());
        assertThat(mARKETINGDTO1).isEqualTo(mARKETINGDTO2);
        mARKETINGDTO2.setId(2L);
        assertThat(mARKETINGDTO1).isNotEqualTo(mARKETINGDTO2);
        mARKETINGDTO1.setId(null);
        assertThat(mARKETINGDTO1).isNotEqualTo(mARKETINGDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(mARKETINGMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(mARKETINGMapper.fromId(null)).isNull();
    }
}
