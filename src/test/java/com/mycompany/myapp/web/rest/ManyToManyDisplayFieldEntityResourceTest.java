package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.Application;
import com.mycompany.myapp.domain.ManyToManyDisplayFieldEntity;
import com.mycompany.myapp.repository.ManyToManyDisplayFieldEntityRepository;

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


/**
 * Test class for the ManyToManyDisplayFieldEntityResource REST controller.
 *
 * @see ManyToManyDisplayFieldEntityResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class ManyToManyDisplayFieldEntityResourceTest {

    private static final String DEFAULT_DISPLAY_FIELD = "SAMPLE_TEXT";
    private static final String UPDATED_DISPLAY_FIELD = "UPDATED_TEXT";

    @Inject
    private ManyToManyDisplayFieldEntityRepository manyToManyDisplayFieldEntityRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restManyToManyDisplayFieldEntityMockMvc;

    private ManyToManyDisplayFieldEntity manyToManyDisplayFieldEntity;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ManyToManyDisplayFieldEntityResource manyToManyDisplayFieldEntityResource = new ManyToManyDisplayFieldEntityResource();
        ReflectionTestUtils.setField(manyToManyDisplayFieldEntityResource, "manyToManyDisplayFieldEntityRepository", manyToManyDisplayFieldEntityRepository);
        this.restManyToManyDisplayFieldEntityMockMvc = MockMvcBuilders.standaloneSetup(manyToManyDisplayFieldEntityResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        manyToManyDisplayFieldEntity = new ManyToManyDisplayFieldEntity();
        manyToManyDisplayFieldEntity.setDisplayField(DEFAULT_DISPLAY_FIELD);
    }

    @Test
    @Transactional
    public void createManyToManyDisplayFieldEntity() throws Exception {
        int databaseSizeBeforeCreate = manyToManyDisplayFieldEntityRepository.findAll().size();

        // Create the ManyToManyDisplayFieldEntity

        restManyToManyDisplayFieldEntityMockMvc.perform(post("/api/manyToManyDisplayFieldEntitys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(manyToManyDisplayFieldEntity)))
                .andExpect(status().isCreated());

        // Validate the ManyToManyDisplayFieldEntity in the database
        List<ManyToManyDisplayFieldEntity> manyToManyDisplayFieldEntitys = manyToManyDisplayFieldEntityRepository.findAll();
        assertThat(manyToManyDisplayFieldEntitys).hasSize(databaseSizeBeforeCreate + 1);
        ManyToManyDisplayFieldEntity testManyToManyDisplayFieldEntity = manyToManyDisplayFieldEntitys.get(manyToManyDisplayFieldEntitys.size() - 1);
        assertThat(testManyToManyDisplayFieldEntity.getDisplayField()).isEqualTo(DEFAULT_DISPLAY_FIELD);
    }

    @Test
    @Transactional
    public void getAllManyToManyDisplayFieldEntitys() throws Exception {
        // Initialize the database
        manyToManyDisplayFieldEntityRepository.saveAndFlush(manyToManyDisplayFieldEntity);

        // Get all the manyToManyDisplayFieldEntitys
        restManyToManyDisplayFieldEntityMockMvc.perform(get("/api/manyToManyDisplayFieldEntitys"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(manyToManyDisplayFieldEntity.getId().intValue())))
                .andExpect(jsonPath("$.[*].displayField").value(hasItem(DEFAULT_DISPLAY_FIELD.toString())));
    }

    @Test
    @Transactional
    public void getManyToManyDisplayFieldEntity() throws Exception {
        // Initialize the database
        manyToManyDisplayFieldEntityRepository.saveAndFlush(manyToManyDisplayFieldEntity);

        // Get the manyToManyDisplayFieldEntity
        restManyToManyDisplayFieldEntityMockMvc.perform(get("/api/manyToManyDisplayFieldEntitys/{id}", manyToManyDisplayFieldEntity.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(manyToManyDisplayFieldEntity.getId().intValue()))
            .andExpect(jsonPath("$.displayField").value(DEFAULT_DISPLAY_FIELD.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingManyToManyDisplayFieldEntity() throws Exception {
        // Get the manyToManyDisplayFieldEntity
        restManyToManyDisplayFieldEntityMockMvc.perform(get("/api/manyToManyDisplayFieldEntitys/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateManyToManyDisplayFieldEntity() throws Exception {
        // Initialize the database
        manyToManyDisplayFieldEntityRepository.saveAndFlush(manyToManyDisplayFieldEntity);

		int databaseSizeBeforeUpdate = manyToManyDisplayFieldEntityRepository.findAll().size();

        // Update the manyToManyDisplayFieldEntity
        manyToManyDisplayFieldEntity.setDisplayField(UPDATED_DISPLAY_FIELD);
        

        restManyToManyDisplayFieldEntityMockMvc.perform(put("/api/manyToManyDisplayFieldEntitys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(manyToManyDisplayFieldEntity)))
                .andExpect(status().isOk());

        // Validate the ManyToManyDisplayFieldEntity in the database
        List<ManyToManyDisplayFieldEntity> manyToManyDisplayFieldEntitys = manyToManyDisplayFieldEntityRepository.findAll();
        assertThat(manyToManyDisplayFieldEntitys).hasSize(databaseSizeBeforeUpdate);
        ManyToManyDisplayFieldEntity testManyToManyDisplayFieldEntity = manyToManyDisplayFieldEntitys.get(manyToManyDisplayFieldEntitys.size() - 1);
        assertThat(testManyToManyDisplayFieldEntity.getDisplayField()).isEqualTo(UPDATED_DISPLAY_FIELD);
    }

    @Test
    @Transactional
    public void deleteManyToManyDisplayFieldEntity() throws Exception {
        // Initialize the database
        manyToManyDisplayFieldEntityRepository.saveAndFlush(manyToManyDisplayFieldEntity);

		int databaseSizeBeforeDelete = manyToManyDisplayFieldEntityRepository.findAll().size();

        // Get the manyToManyDisplayFieldEntity
        restManyToManyDisplayFieldEntityMockMvc.perform(delete("/api/manyToManyDisplayFieldEntitys/{id}", manyToManyDisplayFieldEntity.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<ManyToManyDisplayFieldEntity> manyToManyDisplayFieldEntitys = manyToManyDisplayFieldEntityRepository.findAll();
        assertThat(manyToManyDisplayFieldEntitys).hasSize(databaseSizeBeforeDelete - 1);
    }
}
