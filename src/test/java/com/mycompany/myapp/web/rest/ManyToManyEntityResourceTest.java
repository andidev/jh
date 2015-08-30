package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.Application;
import com.mycompany.myapp.domain.ManyToManyEntity;
import com.mycompany.myapp.repository.ManyToManyEntityRepository;

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
 * Test class for the ManyToManyEntityResource REST controller.
 *
 * @see ManyToManyEntityResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class ManyToManyEntityResourceTest {


    @Inject
    private ManyToManyEntityRepository manyToManyEntityRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    private MockMvc restManyToManyEntityMockMvc;

    private ManyToManyEntity manyToManyEntity;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ManyToManyEntityResource manyToManyEntityResource = new ManyToManyEntityResource();
        ReflectionTestUtils.setField(manyToManyEntityResource, "manyToManyEntityRepository", manyToManyEntityRepository);
        this.restManyToManyEntityMockMvc = MockMvcBuilders.standaloneSetup(manyToManyEntityResource).setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        manyToManyEntity = new ManyToManyEntity();
    }

    @Test
    @Transactional
    public void createManyToManyEntity() throws Exception {
        int databaseSizeBeforeCreate = manyToManyEntityRepository.findAll().size();

        // Create the ManyToManyEntity

        restManyToManyEntityMockMvc.perform(post("/api/manyToManyEntitys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(manyToManyEntity)))
                .andExpect(status().isCreated());

        // Validate the ManyToManyEntity in the database
        List<ManyToManyEntity> manyToManyEntitys = manyToManyEntityRepository.findAll();
        assertThat(manyToManyEntitys).hasSize(databaseSizeBeforeCreate + 1);
        ManyToManyEntity testManyToManyEntity = manyToManyEntitys.get(manyToManyEntitys.size() - 1);
    }

    @Test
    @Transactional
    public void getAllManyToManyEntitys() throws Exception {
        // Initialize the database
        manyToManyEntityRepository.saveAndFlush(manyToManyEntity);

        // Get all the manyToManyEntitys
        restManyToManyEntityMockMvc.perform(get("/api/manyToManyEntitys"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(manyToManyEntity.getId().intValue())));
    }

    @Test
    @Transactional
    public void getManyToManyEntity() throws Exception {
        // Initialize the database
        manyToManyEntityRepository.saveAndFlush(manyToManyEntity);

        // Get the manyToManyEntity
        restManyToManyEntityMockMvc.perform(get("/api/manyToManyEntitys/{id}", manyToManyEntity.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(manyToManyEntity.getId().intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingManyToManyEntity() throws Exception {
        // Get the manyToManyEntity
        restManyToManyEntityMockMvc.perform(get("/api/manyToManyEntitys/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateManyToManyEntity() throws Exception {
        // Initialize the database
        manyToManyEntityRepository.saveAndFlush(manyToManyEntity);

		int databaseSizeBeforeUpdate = manyToManyEntityRepository.findAll().size();

        // Update the manyToManyEntity
        

        restManyToManyEntityMockMvc.perform(put("/api/manyToManyEntitys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(manyToManyEntity)))
                .andExpect(status().isOk());

        // Validate the ManyToManyEntity in the database
        List<ManyToManyEntity> manyToManyEntitys = manyToManyEntityRepository.findAll();
        assertThat(manyToManyEntitys).hasSize(databaseSizeBeforeUpdate);
        ManyToManyEntity testManyToManyEntity = manyToManyEntitys.get(manyToManyEntitys.size() - 1);
    }

    @Test
    @Transactional
    public void deleteManyToManyEntity() throws Exception {
        // Initialize the database
        manyToManyEntityRepository.saveAndFlush(manyToManyEntity);

		int databaseSizeBeforeDelete = manyToManyEntityRepository.findAll().size();

        // Get the manyToManyEntity
        restManyToManyEntityMockMvc.perform(delete("/api/manyToManyEntitys/{id}", manyToManyEntity.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<ManyToManyEntity> manyToManyEntitys = manyToManyEntityRepository.findAll();
        assertThat(manyToManyEntitys).hasSize(databaseSizeBeforeDelete - 1);
    }
}
