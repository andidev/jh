package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.Application;
import com.mycompany.myapp.domain.OneToManyEntity;
import com.mycompany.myapp.repository.OneToManyEntityRepository;

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
 * Test class for the OneToManyEntityResource REST controller.
 *
 * @see OneToManyEntityResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class OneToManyEntityResourceTest {


    @Inject
    private OneToManyEntityRepository oneToManyEntityRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restOneToManyEntityMockMvc;

    private OneToManyEntity oneToManyEntity;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        OneToManyEntityResource oneToManyEntityResource = new OneToManyEntityResource();
        ReflectionTestUtils.setField(oneToManyEntityResource, "oneToManyEntityRepository", oneToManyEntityRepository);
        this.restOneToManyEntityMockMvc = MockMvcBuilders.standaloneSetup(oneToManyEntityResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        oneToManyEntity = new OneToManyEntity();
    }

    @Test
    @Transactional
    public void createOneToManyEntity() throws Exception {
        int databaseSizeBeforeCreate = oneToManyEntityRepository.findAll().size();

        // Create the OneToManyEntity

        restOneToManyEntityMockMvc.perform(post("/api/oneToManyEntitys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(oneToManyEntity)))
                .andExpect(status().isCreated());

        // Validate the OneToManyEntity in the database
        List<OneToManyEntity> oneToManyEntitys = oneToManyEntityRepository.findAll();
        assertThat(oneToManyEntitys).hasSize(databaseSizeBeforeCreate + 1);
        OneToManyEntity testOneToManyEntity = oneToManyEntitys.get(oneToManyEntitys.size() - 1);
    }

    @Test
    @Transactional
    public void getAllOneToManyEntitys() throws Exception {
        // Initialize the database
        oneToManyEntityRepository.saveAndFlush(oneToManyEntity);

        // Get all the oneToManyEntitys
        restOneToManyEntityMockMvc.perform(get("/api/oneToManyEntitys"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(oneToManyEntity.getId().intValue())));
    }

    @Test
    @Transactional
    public void getOneToManyEntity() throws Exception {
        // Initialize the database
        oneToManyEntityRepository.saveAndFlush(oneToManyEntity);

        // Get the oneToManyEntity
        restOneToManyEntityMockMvc.perform(get("/api/oneToManyEntitys/{id}", oneToManyEntity.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(oneToManyEntity.getId().intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingOneToManyEntity() throws Exception {
        // Get the oneToManyEntity
        restOneToManyEntityMockMvc.perform(get("/api/oneToManyEntitys/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOneToManyEntity() throws Exception {
        // Initialize the database
        oneToManyEntityRepository.saveAndFlush(oneToManyEntity);

		int databaseSizeBeforeUpdate = oneToManyEntityRepository.findAll().size();

        // Update the oneToManyEntity
        

        restOneToManyEntityMockMvc.perform(put("/api/oneToManyEntitys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(oneToManyEntity)))
                .andExpect(status().isOk());

        // Validate the OneToManyEntity in the database
        List<OneToManyEntity> oneToManyEntitys = oneToManyEntityRepository.findAll();
        assertThat(oneToManyEntitys).hasSize(databaseSizeBeforeUpdate);
        OneToManyEntity testOneToManyEntity = oneToManyEntitys.get(oneToManyEntitys.size() - 1);
    }

    @Test
    @Transactional
    public void deleteOneToManyEntity() throws Exception {
        // Initialize the database
        oneToManyEntityRepository.saveAndFlush(oneToManyEntity);

		int databaseSizeBeforeDelete = oneToManyEntityRepository.findAll().size();

        // Get the oneToManyEntity
        restOneToManyEntityMockMvc.perform(delete("/api/oneToManyEntitys/{id}", oneToManyEntity.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<OneToManyEntity> oneToManyEntitys = oneToManyEntityRepository.findAll();
        assertThat(oneToManyEntitys).hasSize(databaseSizeBeforeDelete - 1);
    }
}
