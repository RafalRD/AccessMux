package com.sggw.accessmux.web.rest;

import com.sggw.accessmux.AccessMuxApp;

import com.sggw.accessmux.domain.ACTIVITY;
import com.sggw.accessmux.repository.ACTIVITYRepository;
import com.sggw.accessmux.service.ACTIVITYService;
import com.sggw.accessmux.service.dto.ACTIVITYDTO;
import com.sggw.accessmux.service.mapper.ACTIVITYMapper;
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
 * Test class for the ACTIVITYResource REST controller.
 *
 * @see ACTIVITYResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AccessMuxApp.class)
public class ACTIVITYResourceIntTest {

    private static final String DEFAULT_LOGIN = "AAAAAAAAAA";
    private static final String UPDATED_LOGIN = "BBBBBBBBBB";

    private static final String DEFAULT_ROLE = "AAAAAAAAAA";
    private static final String UPDATED_ROLE = "BBBBBBBBBB";

    private static final String DEFAULT_URL = "AAAAAAAAAA";
    private static final String UPDATED_URL = "BBBBBBBBBB";

    private static final String DEFAULT_TIME = "AAAAAAAAAA";
    private static final String UPDATED_TIME = "BBBBBBBBBB";

    @Autowired
    private ACTIVITYRepository aCTIVITYRepository;

    @Autowired
    private ACTIVITYMapper aCTIVITYMapper;
    
    @Autowired
    private ACTIVITYService aCTIVITYService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restACTIVITYMockMvc;

    private ACTIVITY aCTIVITY;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ACTIVITYResource aCTIVITYResource = new ACTIVITYResource(aCTIVITYService);
        this.restACTIVITYMockMvc = MockMvcBuilders.standaloneSetup(aCTIVITYResource)
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
    public static ACTIVITY createEntity(EntityManager em) {
        ACTIVITY aCTIVITY = new ACTIVITY()
            .login(DEFAULT_LOGIN)
            .role(DEFAULT_ROLE)
            .url(DEFAULT_URL)
            .time(DEFAULT_TIME);
        return aCTIVITY;
    }

    @Before
    public void initTest() {
        aCTIVITY = createEntity(em);
    }

    @Test
    @Transactional
    public void createACTIVITY() throws Exception {
        int databaseSizeBeforeCreate = aCTIVITYRepository.findAll().size();

        // Create the ACTIVITY
        ACTIVITYDTO aCTIVITYDTO = aCTIVITYMapper.toDto(aCTIVITY);
        restACTIVITYMockMvc.perform(post("/api/activities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(aCTIVITYDTO)))
            .andExpect(status().isCreated());

        // Validate the ACTIVITY in the database
        List<ACTIVITY> aCTIVITYList = aCTIVITYRepository.findAll();
        assertThat(aCTIVITYList).hasSize(databaseSizeBeforeCreate + 1);
        ACTIVITY testACTIVITY = aCTIVITYList.get(aCTIVITYList.size() - 1);
        assertThat(testACTIVITY.getLogin()).isEqualTo(DEFAULT_LOGIN);
        assertThat(testACTIVITY.getRole()).isEqualTo(DEFAULT_ROLE);
        assertThat(testACTIVITY.getUrl()).isEqualTo(DEFAULT_URL);
        assertThat(testACTIVITY.getTime()).isEqualTo(DEFAULT_TIME);
    }

    @Test
    @Transactional
    public void createACTIVITYWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = aCTIVITYRepository.findAll().size();

        // Create the ACTIVITY with an existing ID
        aCTIVITY.setId(1L);
        ACTIVITYDTO aCTIVITYDTO = aCTIVITYMapper.toDto(aCTIVITY);

        // An entity with an existing ID cannot be created, so this API call must fail
        restACTIVITYMockMvc.perform(post("/api/activities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(aCTIVITYDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ACTIVITY in the database
        List<ACTIVITY> aCTIVITYList = aCTIVITYRepository.findAll();
        assertThat(aCTIVITYList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkLoginIsRequired() throws Exception {
        int databaseSizeBeforeTest = aCTIVITYRepository.findAll().size();
        // set the field null
        aCTIVITY.setLogin(null);

        // Create the ACTIVITY, which fails.
        ACTIVITYDTO aCTIVITYDTO = aCTIVITYMapper.toDto(aCTIVITY);

        restACTIVITYMockMvc.perform(post("/api/activities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(aCTIVITYDTO)))
            .andExpect(status().isBadRequest());

        List<ACTIVITY> aCTIVITYList = aCTIVITYRepository.findAll();
        assertThat(aCTIVITYList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkRoleIsRequired() throws Exception {
        int databaseSizeBeforeTest = aCTIVITYRepository.findAll().size();
        // set the field null
        aCTIVITY.setRole(null);

        // Create the ACTIVITY, which fails.
        ACTIVITYDTO aCTIVITYDTO = aCTIVITYMapper.toDto(aCTIVITY);

        restACTIVITYMockMvc.perform(post("/api/activities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(aCTIVITYDTO)))
            .andExpect(status().isBadRequest());

        List<ACTIVITY> aCTIVITYList = aCTIVITYRepository.findAll();
        assertThat(aCTIVITYList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUrlIsRequired() throws Exception {
        int databaseSizeBeforeTest = aCTIVITYRepository.findAll().size();
        // set the field null
        aCTIVITY.setUrl(null);

        // Create the ACTIVITY, which fails.
        ACTIVITYDTO aCTIVITYDTO = aCTIVITYMapper.toDto(aCTIVITY);

        restACTIVITYMockMvc.perform(post("/api/activities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(aCTIVITYDTO)))
            .andExpect(status().isBadRequest());

        List<ACTIVITY> aCTIVITYList = aCTIVITYRepository.findAll();
        assertThat(aCTIVITYList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTimeIsRequired() throws Exception {
        int databaseSizeBeforeTest = aCTIVITYRepository.findAll().size();
        // set the field null
        aCTIVITY.setTime(null);

        // Create the ACTIVITY, which fails.
        ACTIVITYDTO aCTIVITYDTO = aCTIVITYMapper.toDto(aCTIVITY);

        restACTIVITYMockMvc.perform(post("/api/activities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(aCTIVITYDTO)))
            .andExpect(status().isBadRequest());

        List<ACTIVITY> aCTIVITYList = aCTIVITYRepository.findAll();
        assertThat(aCTIVITYList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllACTIVITIES() throws Exception {
        // Initialize the database
        aCTIVITYRepository.saveAndFlush(aCTIVITY);

        // Get all the aCTIVITYList
        restACTIVITYMockMvc.perform(get("/api/activities?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(aCTIVITY.getId().intValue())))
            .andExpect(jsonPath("$.[*].login").value(hasItem(DEFAULT_LOGIN.toString())))
            .andExpect(jsonPath("$.[*].role").value(hasItem(DEFAULT_ROLE.toString())))
            .andExpect(jsonPath("$.[*].url").value(hasItem(DEFAULT_URL.toString())))
            .andExpect(jsonPath("$.[*].time").value(hasItem(DEFAULT_TIME.toString())));
    }
    
    @Test
    @Transactional
    public void getACTIVITY() throws Exception {
        // Initialize the database
        aCTIVITYRepository.saveAndFlush(aCTIVITY);

        // Get the aCTIVITY
        restACTIVITYMockMvc.perform(get("/api/activities/{id}", aCTIVITY.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(aCTIVITY.getId().intValue()))
            .andExpect(jsonPath("$.login").value(DEFAULT_LOGIN.toString()))
            .andExpect(jsonPath("$.role").value(DEFAULT_ROLE.toString()))
            .andExpect(jsonPath("$.url").value(DEFAULT_URL.toString()))
            .andExpect(jsonPath("$.time").value(DEFAULT_TIME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingACTIVITY() throws Exception {
        // Get the aCTIVITY
        restACTIVITYMockMvc.perform(get("/api/activities/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateACTIVITY() throws Exception {
        // Initialize the database
        aCTIVITYRepository.saveAndFlush(aCTIVITY);

        int databaseSizeBeforeUpdate = aCTIVITYRepository.findAll().size();

        // Update the aCTIVITY
        ACTIVITY updatedACTIVITY = aCTIVITYRepository.findById(aCTIVITY.getId()).get();
        // Disconnect from session so that the updates on updatedACTIVITY are not directly saved in db
        em.detach(updatedACTIVITY);
        updatedACTIVITY
            .login(UPDATED_LOGIN)
            .role(UPDATED_ROLE)
            .url(UPDATED_URL)
            .time(UPDATED_TIME);
        ACTIVITYDTO aCTIVITYDTO = aCTIVITYMapper.toDto(updatedACTIVITY);

        restACTIVITYMockMvc.perform(put("/api/activities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(aCTIVITYDTO)))
            .andExpect(status().isOk());

        // Validate the ACTIVITY in the database
        List<ACTIVITY> aCTIVITYList = aCTIVITYRepository.findAll();
        assertThat(aCTIVITYList).hasSize(databaseSizeBeforeUpdate);
        ACTIVITY testACTIVITY = aCTIVITYList.get(aCTIVITYList.size() - 1);
        assertThat(testACTIVITY.getLogin()).isEqualTo(UPDATED_LOGIN);
        assertThat(testACTIVITY.getRole()).isEqualTo(UPDATED_ROLE);
        assertThat(testACTIVITY.getUrl()).isEqualTo(UPDATED_URL);
        assertThat(testACTIVITY.getTime()).isEqualTo(UPDATED_TIME);
    }

    @Test
    @Transactional
    public void updateNonExistingACTIVITY() throws Exception {
        int databaseSizeBeforeUpdate = aCTIVITYRepository.findAll().size();

        // Create the ACTIVITY
        ACTIVITYDTO aCTIVITYDTO = aCTIVITYMapper.toDto(aCTIVITY);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restACTIVITYMockMvc.perform(put("/api/activities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(aCTIVITYDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ACTIVITY in the database
        List<ACTIVITY> aCTIVITYList = aCTIVITYRepository.findAll();
        assertThat(aCTIVITYList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteACTIVITY() throws Exception {
        // Initialize the database
        aCTIVITYRepository.saveAndFlush(aCTIVITY);

        int databaseSizeBeforeDelete = aCTIVITYRepository.findAll().size();

        // Get the aCTIVITY
        restACTIVITYMockMvc.perform(delete("/api/activities/{id}", aCTIVITY.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ACTIVITY> aCTIVITYList = aCTIVITYRepository.findAll();
        assertThat(aCTIVITYList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ACTIVITY.class);
        ACTIVITY aCTIVITY1 = new ACTIVITY();
        aCTIVITY1.setId(1L);
        ACTIVITY aCTIVITY2 = new ACTIVITY();
        aCTIVITY2.setId(aCTIVITY1.getId());
        assertThat(aCTIVITY1).isEqualTo(aCTIVITY2);
        aCTIVITY2.setId(2L);
        assertThat(aCTIVITY1).isNotEqualTo(aCTIVITY2);
        aCTIVITY1.setId(null);
        assertThat(aCTIVITY1).isNotEqualTo(aCTIVITY2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ACTIVITYDTO.class);
        ACTIVITYDTO aCTIVITYDTO1 = new ACTIVITYDTO();
        aCTIVITYDTO1.setId(1L);
        ACTIVITYDTO aCTIVITYDTO2 = new ACTIVITYDTO();
        assertThat(aCTIVITYDTO1).isNotEqualTo(aCTIVITYDTO2);
        aCTIVITYDTO2.setId(aCTIVITYDTO1.getId());
        assertThat(aCTIVITYDTO1).isEqualTo(aCTIVITYDTO2);
        aCTIVITYDTO2.setId(2L);
        assertThat(aCTIVITYDTO1).isNotEqualTo(aCTIVITYDTO2);
        aCTIVITYDTO1.setId(null);
        assertThat(aCTIVITYDTO1).isNotEqualTo(aCTIVITYDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(aCTIVITYMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(aCTIVITYMapper.fromId(null)).isNull();
    }
}
