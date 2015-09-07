package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.Application;
import com.mycompany.myapp.domain.FieldEntity;
import com.mycompany.myapp.repository.FieldEntityRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.domain.enumeration.Language;

/**
 * Test class for the FieldEntityResource REST controller.
 *
 * @see FieldEntityResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class FieldEntityResourceTest {

    private static final String DEFAULT_NAME = "SAMPLE_TEXT";
    private static final String UPDATED_NAME = "UPDATED_TEXT";
    private static final String DEFAULT_EMAIL = "SAMPLE_TEXT";
    private static final String UPDATED_EMAIL = "UPDATED_TEXT";

    private static final Double DEFAULT_A_DOUBLE = 1D;
    private static final Double UPDATED_A_DOUBLE = 2D;

    private static final Double DEFAULT_A_REQUIRED_DOUBLE = 1D;
    private static final Double UPDATED_A_REQUIRED_DOUBLE = 2D;

    private static final Language DEFAULT_AN_ENUM = Language.FRENCH;
    private static final Language UPDATED_AN_ENUM = Language.ENGLISH;

    @Inject
    private FieldEntityRepository fieldEntityRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restFieldEntityMockMvc;

    private FieldEntity fieldEntity;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        FieldEntityResource fieldEntityResource = new FieldEntityResource();
        ReflectionTestUtils.setField(fieldEntityResource, "fieldEntityRepository", fieldEntityRepository);
        this.restFieldEntityMockMvc = MockMvcBuilders.standaloneSetup(fieldEntityResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        fieldEntity = new FieldEntity();
        fieldEntity.setName(DEFAULT_NAME);
        fieldEntity.setEmail(DEFAULT_EMAIL);
        fieldEntity.setaDouble(DEFAULT_A_DOUBLE);
        fieldEntity.setaRequiredDouble(DEFAULT_A_REQUIRED_DOUBLE);
        fieldEntity.setAnEnum(DEFAULT_AN_ENUM);
    }

    @Test
    @Transactional
    public void createFieldEntity() throws Exception {
        int databaseSizeBeforeCreate = fieldEntityRepository.findAll().size();

        // Create the FieldEntity

        restFieldEntityMockMvc.perform(post("/api/fieldEntitys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(fieldEntity)))
                .andExpect(status().isCreated());

        // Validate the FieldEntity in the database
        List<FieldEntity> fieldEntitys = fieldEntityRepository.findAll();
        assertThat(fieldEntitys).hasSize(databaseSizeBeforeCreate + 1);
        FieldEntity testFieldEntity = fieldEntitys.get(fieldEntitys.size() - 1);
        assertThat(testFieldEntity.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testFieldEntity.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testFieldEntity.getaDouble()).isEqualTo(DEFAULT_A_DOUBLE);
        assertThat(testFieldEntity.getaRequiredDouble()).isEqualTo(DEFAULT_A_REQUIRED_DOUBLE);
        assertThat(testFieldEntity.getAnEnum()).isEqualTo(DEFAULT_AN_ENUM);
    }

    @Test
    @Transactional
    public void checkEmailIsRequired() throws Exception {
        int databaseSizeBeforeTest = fieldEntityRepository.findAll().size();
        // set the field null
        fieldEntity.setEmail(null);

        // Create the FieldEntity, which fails.

        restFieldEntityMockMvc.perform(post("/api/fieldEntitys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(fieldEntity)))
                .andExpect(status().isBadRequest());

        List<FieldEntity> fieldEntitys = fieldEntityRepository.findAll();
        assertThat(fieldEntitys).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkaRequiredDoubleIsRequired() throws Exception {
        int databaseSizeBeforeTest = fieldEntityRepository.findAll().size();
        // set the field null
        fieldEntity.setaRequiredDouble(null);

        // Create the FieldEntity, which fails.

        restFieldEntityMockMvc.perform(post("/api/fieldEntitys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(fieldEntity)))
                .andExpect(status().isBadRequest());

        List<FieldEntity> fieldEntitys = fieldEntityRepository.findAll();
        assertThat(fieldEntitys).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllFieldEntitys() throws Exception {
        // Initialize the database
        fieldEntityRepository.saveAndFlush(fieldEntity);

        // Get all the fieldEntitys
        restFieldEntityMockMvc.perform(get("/api/fieldEntitys"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(fieldEntity.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
                .andExpect(jsonPath("$.[*].aDouble").value(hasItem(DEFAULT_A_DOUBLE.doubleValue())))
                .andExpect(jsonPath("$.[*].aRequiredDouble").value(hasItem(DEFAULT_A_REQUIRED_DOUBLE.doubleValue())))
                .andExpect(jsonPath("$.[*].anEnum").value(hasItem(DEFAULT_AN_ENUM.toString())));
    }

    @Test
    @Transactional
    public void getFieldEntity() throws Exception {
        // Initialize the database
        fieldEntityRepository.saveAndFlush(fieldEntity);

        // Get the fieldEntity
        restFieldEntityMockMvc.perform(get("/api/fieldEntitys/{id}", fieldEntity.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(fieldEntity.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()))
            .andExpect(jsonPath("$.aDouble").value(DEFAULT_A_DOUBLE.doubleValue()))
            .andExpect(jsonPath("$.aRequiredDouble").value(DEFAULT_A_REQUIRED_DOUBLE.doubleValue()))
            .andExpect(jsonPath("$.anEnum").value(DEFAULT_AN_ENUM.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingFieldEntity() throws Exception {
        // Get the fieldEntity
        restFieldEntityMockMvc.perform(get("/api/fieldEntitys/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFieldEntity() throws Exception {
        // Initialize the database
        fieldEntityRepository.saveAndFlush(fieldEntity);

		int databaseSizeBeforeUpdate = fieldEntityRepository.findAll().size();

        // Update the fieldEntity
        fieldEntity.setName(UPDATED_NAME);
        fieldEntity.setEmail(UPDATED_EMAIL);
        fieldEntity.setaDouble(UPDATED_A_DOUBLE);
        fieldEntity.setaRequiredDouble(UPDATED_A_REQUIRED_DOUBLE);
        fieldEntity.setAnEnum(UPDATED_AN_ENUM);
        

        restFieldEntityMockMvc.perform(put("/api/fieldEntitys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(fieldEntity)))
                .andExpect(status().isOk());

        // Validate the FieldEntity in the database
        List<FieldEntity> fieldEntitys = fieldEntityRepository.findAll();
        assertThat(fieldEntitys).hasSize(databaseSizeBeforeUpdate);
        FieldEntity testFieldEntity = fieldEntitys.get(fieldEntitys.size() - 1);
        assertThat(testFieldEntity.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testFieldEntity.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testFieldEntity.getaDouble()).isEqualTo(UPDATED_A_DOUBLE);
        assertThat(testFieldEntity.getaRequiredDouble()).isEqualTo(UPDATED_A_REQUIRED_DOUBLE);
        assertThat(testFieldEntity.getAnEnum()).isEqualTo(UPDATED_AN_ENUM);
    }

    @Test
    @Transactional
    public void deleteFieldEntity() throws Exception {
        // Initialize the database
        fieldEntityRepository.saveAndFlush(fieldEntity);

		int databaseSizeBeforeDelete = fieldEntityRepository.findAll().size();

        // Get the fieldEntity
        restFieldEntityMockMvc.perform(delete("/api/fieldEntitys/{id}", fieldEntity.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<FieldEntity> fieldEntitys = fieldEntityRepository.findAll();
        assertThat(fieldEntitys).hasSize(databaseSizeBeforeDelete - 1);
    }
}
