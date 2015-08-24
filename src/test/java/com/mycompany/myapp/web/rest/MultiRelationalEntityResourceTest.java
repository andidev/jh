package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.Application;
import com.mycompany.myapp.domain.MultiRelationalEntity;
import com.mycompany.myapp.repository.MultiRelationalEntityRepository;
import com.mycompany.myapp.web.rest.dto.MultiRelationalEntityDTO;
import com.mycompany.myapp.web.rest.mapper.MultiRelationalEntityMapper;

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
 * Test class for the MultiRelationalEntityResource REST controller.
 *
 * @see MultiRelationalEntityResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class MultiRelationalEntityResourceTest {


    @Inject
    private MultiRelationalEntityRepository multiRelationalEntityRepository;

    @Inject
    private MultiRelationalEntityMapper multiRelationalEntityMapper;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    private MockMvc restMultiRelationalEntityMockMvc;

    private MultiRelationalEntity multiRelationalEntity;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        MultiRelationalEntityResource multiRelationalEntityResource = new MultiRelationalEntityResource();
        ReflectionTestUtils.setField(multiRelationalEntityResource, "multiRelationalEntityRepository", multiRelationalEntityRepository);
        ReflectionTestUtils.setField(multiRelationalEntityResource, "multiRelationalEntityMapper", multiRelationalEntityMapper);
        this.restMultiRelationalEntityMockMvc = MockMvcBuilders.standaloneSetup(multiRelationalEntityResource).setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        multiRelationalEntity = new MultiRelationalEntity();
    }

    @Test
    @Transactional
    public void createMultiRelationalEntity() throws Exception {
        int databaseSizeBeforeCreate = multiRelationalEntityRepository.findAll().size();

        // Create the MultiRelationalEntity
        MultiRelationalEntityDTO multiRelationalEntityDTO = multiRelationalEntityMapper.multiRelationalEntityToMultiRelationalEntityDTO(multiRelationalEntity);

        restMultiRelationalEntityMockMvc.perform(post("/api/multiRelationalEntitys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(multiRelationalEntityDTO)))
                .andExpect(status().isCreated());

        // Validate the MultiRelationalEntity in the database
        List<MultiRelationalEntity> multiRelationalEntitys = multiRelationalEntityRepository.findAll();
        assertThat(multiRelationalEntitys).hasSize(databaseSizeBeforeCreate + 1);
        MultiRelationalEntity testMultiRelationalEntity = multiRelationalEntitys.get(multiRelationalEntitys.size() - 1);
    }

    @Test
    @Transactional
    public void getAllMultiRelationalEntitys() throws Exception {
        // Initialize the database
        multiRelationalEntityRepository.saveAndFlush(multiRelationalEntity);

        // Get all the multiRelationalEntitys
        restMultiRelationalEntityMockMvc.perform(get("/api/multiRelationalEntitys"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(multiRelationalEntity.getId().intValue())));
    }

    @Test
    @Transactional
    public void getMultiRelationalEntity() throws Exception {
        // Initialize the database
        multiRelationalEntityRepository.saveAndFlush(multiRelationalEntity);

        // Get the multiRelationalEntity
        restMultiRelationalEntityMockMvc.perform(get("/api/multiRelationalEntitys/{id}", multiRelationalEntity.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(multiRelationalEntity.getId().intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingMultiRelationalEntity() throws Exception {
        // Get the multiRelationalEntity
        restMultiRelationalEntityMockMvc.perform(get("/api/multiRelationalEntitys/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMultiRelationalEntity() throws Exception {
        // Initialize the database
        multiRelationalEntityRepository.saveAndFlush(multiRelationalEntity);

		int databaseSizeBeforeUpdate = multiRelationalEntityRepository.findAll().size();

        // Update the multiRelationalEntity
        
        MultiRelationalEntityDTO multiRelationalEntityDTO = multiRelationalEntityMapper.multiRelationalEntityToMultiRelationalEntityDTO(multiRelationalEntity);

        restMultiRelationalEntityMockMvc.perform(put("/api/multiRelationalEntitys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(multiRelationalEntityDTO)))
                .andExpect(status().isOk());

        // Validate the MultiRelationalEntity in the database
        List<MultiRelationalEntity> multiRelationalEntitys = multiRelationalEntityRepository.findAll();
        assertThat(multiRelationalEntitys).hasSize(databaseSizeBeforeUpdate);
        MultiRelationalEntity testMultiRelationalEntity = multiRelationalEntitys.get(multiRelationalEntitys.size() - 1);
    }

    @Test
    @Transactional
    public void deleteMultiRelationalEntity() throws Exception {
        // Initialize the database
        multiRelationalEntityRepository.saveAndFlush(multiRelationalEntity);

		int databaseSizeBeforeDelete = multiRelationalEntityRepository.findAll().size();

        // Get the multiRelationalEntity
        restMultiRelationalEntityMockMvc.perform(delete("/api/multiRelationalEntitys/{id}", multiRelationalEntity.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<MultiRelationalEntity> multiRelationalEntitys = multiRelationalEntityRepository.findAll();
        assertThat(multiRelationalEntitys).hasSize(databaseSizeBeforeDelete - 1);
    }
}
