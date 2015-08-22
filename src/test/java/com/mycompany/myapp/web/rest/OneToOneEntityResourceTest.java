package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.Application;
import com.mycompany.myapp.domain.OneToOneEntity;
import com.mycompany.myapp.repository.OneToOneEntityRepository;

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
 * Test class for the OneToOneEntityResource REST controller.
 *
 * @see OneToOneEntityResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class OneToOneEntityResourceTest {


    @Inject
    private OneToOneEntityRepository oneToOneEntityRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    private MockMvc restOneToOneEntityMockMvc;

    private OneToOneEntity oneToOneEntity;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        OneToOneEntityResource oneToOneEntityResource = new OneToOneEntityResource();
        ReflectionTestUtils.setField(oneToOneEntityResource, "oneToOneEntityRepository", oneToOneEntityRepository);
        this.restOneToOneEntityMockMvc = MockMvcBuilders.standaloneSetup(oneToOneEntityResource).setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        oneToOneEntity = new OneToOneEntity();
    }

    @Test
    @Transactional
    public void createOneToOneEntity() throws Exception {
        int databaseSizeBeforeCreate = oneToOneEntityRepository.findAll().size();

        // Create the OneToOneEntity

        restOneToOneEntityMockMvc.perform(post("/api/oneToOneEntitys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(oneToOneEntity)))
                .andExpect(status().isCreated());

        // Validate the OneToOneEntity in the database
        List<OneToOneEntity> oneToOneEntitys = oneToOneEntityRepository.findAll();
        assertThat(oneToOneEntitys).hasSize(databaseSizeBeforeCreate + 1);
        OneToOneEntity testOneToOneEntity = oneToOneEntitys.get(oneToOneEntitys.size() - 1);
    }

    @Test
    @Transactional
    public void getAllOneToOneEntitys() throws Exception {
        // Initialize the database
        oneToOneEntityRepository.saveAndFlush(oneToOneEntity);

        // Get all the oneToOneEntitys
        restOneToOneEntityMockMvc.perform(get("/api/oneToOneEntitys"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(oneToOneEntity.getId().intValue())));
    }

    @Test
    @Transactional
    public void getOneToOneEntity() throws Exception {
        // Initialize the database
        oneToOneEntityRepository.saveAndFlush(oneToOneEntity);

        // Get the oneToOneEntity
        restOneToOneEntityMockMvc.perform(get("/api/oneToOneEntitys/{id}", oneToOneEntity.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(oneToOneEntity.getId().intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingOneToOneEntity() throws Exception {
        // Get the oneToOneEntity
        restOneToOneEntityMockMvc.perform(get("/api/oneToOneEntitys/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOneToOneEntity() throws Exception {
        // Initialize the database
        oneToOneEntityRepository.saveAndFlush(oneToOneEntity);

		int databaseSizeBeforeUpdate = oneToOneEntityRepository.findAll().size();

        // Update the oneToOneEntity
        

        restOneToOneEntityMockMvc.perform(put("/api/oneToOneEntitys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(oneToOneEntity)))
                .andExpect(status().isOk());

        // Validate the OneToOneEntity in the database
        List<OneToOneEntity> oneToOneEntitys = oneToOneEntityRepository.findAll();
        assertThat(oneToOneEntitys).hasSize(databaseSizeBeforeUpdate);
        OneToOneEntity testOneToOneEntity = oneToOneEntitys.get(oneToOneEntitys.size() - 1);
    }

    @Test
    @Transactional
    public void deleteOneToOneEntity() throws Exception {
        // Initialize the database
        oneToOneEntityRepository.saveAndFlush(oneToOneEntity);

		int databaseSizeBeforeDelete = oneToOneEntityRepository.findAll().size();

        // Get the oneToOneEntity
        restOneToOneEntityMockMvc.perform(delete("/api/oneToOneEntitys/{id}", oneToOneEntity.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<OneToOneEntity> oneToOneEntitys = oneToOneEntityRepository.findAll();
        assertThat(oneToOneEntitys).hasSize(databaseSizeBeforeDelete - 1);
    }
}
