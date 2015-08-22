package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.Application;
import com.mycompany.myapp.domain.OneToOneDisplayFieldEntity;
import com.mycompany.myapp.repository.OneToOneDisplayFieldEntityRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
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


/**
 * Test class for the OneToOneDisplayFieldEntityResource REST controller.
 *
 * @see OneToOneDisplayFieldEntityResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class OneToOneDisplayFieldEntityResourceTest {

    private static final String DEFAULT_DISPLAY_FIELD = "SAMPLE_TEXT";
    private static final String UPDATED_DISPLAY_FIELD = "UPDATED_TEXT";

    @Inject
    private OneToOneDisplayFieldEntityRepository oneToOneDisplayFieldEntityRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    private MockMvc restOneToOneDisplayFieldEntityMockMvc;

    private OneToOneDisplayFieldEntity oneToOneDisplayFieldEntity;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        OneToOneDisplayFieldEntityResource oneToOneDisplayFieldEntityResource = new OneToOneDisplayFieldEntityResource();
        ReflectionTestUtils.setField(oneToOneDisplayFieldEntityResource, "oneToOneDisplayFieldEntityRepository", oneToOneDisplayFieldEntityRepository);
        this.restOneToOneDisplayFieldEntityMockMvc = MockMvcBuilders.standaloneSetup(oneToOneDisplayFieldEntityResource).setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        oneToOneDisplayFieldEntity = new OneToOneDisplayFieldEntity();
        oneToOneDisplayFieldEntity.setDisplayField(DEFAULT_DISPLAY_FIELD);
    }

    @Test
    @Transactional
    public void createOneToOneDisplayFieldEntity() throws Exception {
        int databaseSizeBeforeCreate = oneToOneDisplayFieldEntityRepository.findAll().size();

        // Create the OneToOneDisplayFieldEntity

        restOneToOneDisplayFieldEntityMockMvc.perform(post("/api/oneToOneDisplayFieldEntitys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(oneToOneDisplayFieldEntity)))
                .andExpect(status().isCreated());

        // Validate the OneToOneDisplayFieldEntity in the database
        List<OneToOneDisplayFieldEntity> oneToOneDisplayFieldEntitys = oneToOneDisplayFieldEntityRepository.findAll();
        assertThat(oneToOneDisplayFieldEntitys).hasSize(databaseSizeBeforeCreate + 1);
        OneToOneDisplayFieldEntity testOneToOneDisplayFieldEntity = oneToOneDisplayFieldEntitys.get(oneToOneDisplayFieldEntitys.size() - 1);
        assertThat(testOneToOneDisplayFieldEntity.getDisplayField()).isEqualTo(DEFAULT_DISPLAY_FIELD);
    }

    @Test
    @Transactional
    public void getAllOneToOneDisplayFieldEntitys() throws Exception {
        // Initialize the database
        oneToOneDisplayFieldEntityRepository.saveAndFlush(oneToOneDisplayFieldEntity);

        // Get all the oneToOneDisplayFieldEntitys
        restOneToOneDisplayFieldEntityMockMvc.perform(get("/api/oneToOneDisplayFieldEntitys"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(oneToOneDisplayFieldEntity.getId().intValue())))
                .andExpect(jsonPath("$.[*].displayField").value(hasItem(DEFAULT_DISPLAY_FIELD.toString())));
    }

    @Test
    @Transactional
    public void getOneToOneDisplayFieldEntity() throws Exception {
        // Initialize the database
        oneToOneDisplayFieldEntityRepository.saveAndFlush(oneToOneDisplayFieldEntity);

        // Get the oneToOneDisplayFieldEntity
        restOneToOneDisplayFieldEntityMockMvc.perform(get("/api/oneToOneDisplayFieldEntitys/{id}", oneToOneDisplayFieldEntity.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(oneToOneDisplayFieldEntity.getId().intValue()))
            .andExpect(jsonPath("$.displayField").value(DEFAULT_DISPLAY_FIELD.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingOneToOneDisplayFieldEntity() throws Exception {
        // Get the oneToOneDisplayFieldEntity
        restOneToOneDisplayFieldEntityMockMvc.perform(get("/api/oneToOneDisplayFieldEntitys/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOneToOneDisplayFieldEntity() throws Exception {
        // Initialize the database
        oneToOneDisplayFieldEntityRepository.saveAndFlush(oneToOneDisplayFieldEntity);

		int databaseSizeBeforeUpdate = oneToOneDisplayFieldEntityRepository.findAll().size();

        // Update the oneToOneDisplayFieldEntity
        oneToOneDisplayFieldEntity.setDisplayField(UPDATED_DISPLAY_FIELD);
        

        restOneToOneDisplayFieldEntityMockMvc.perform(put("/api/oneToOneDisplayFieldEntitys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(oneToOneDisplayFieldEntity)))
                .andExpect(status().isOk());

        // Validate the OneToOneDisplayFieldEntity in the database
        List<OneToOneDisplayFieldEntity> oneToOneDisplayFieldEntitys = oneToOneDisplayFieldEntityRepository.findAll();
        assertThat(oneToOneDisplayFieldEntitys).hasSize(databaseSizeBeforeUpdate);
        OneToOneDisplayFieldEntity testOneToOneDisplayFieldEntity = oneToOneDisplayFieldEntitys.get(oneToOneDisplayFieldEntitys.size() - 1);
        assertThat(testOneToOneDisplayFieldEntity.getDisplayField()).isEqualTo(UPDATED_DISPLAY_FIELD);
    }

    @Test
    @Transactional
    public void deleteOneToOneDisplayFieldEntity() throws Exception {
        // Initialize the database
        oneToOneDisplayFieldEntityRepository.saveAndFlush(oneToOneDisplayFieldEntity);

		int databaseSizeBeforeDelete = oneToOneDisplayFieldEntityRepository.findAll().size();

        // Get the oneToOneDisplayFieldEntity
        restOneToOneDisplayFieldEntityMockMvc.perform(delete("/api/oneToOneDisplayFieldEntitys/{id}", oneToOneDisplayFieldEntity.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<OneToOneDisplayFieldEntity> oneToOneDisplayFieldEntitys = oneToOneDisplayFieldEntityRepository.findAll();
        assertThat(oneToOneDisplayFieldEntitys).hasSize(databaseSizeBeforeDelete - 1);
    }
}
