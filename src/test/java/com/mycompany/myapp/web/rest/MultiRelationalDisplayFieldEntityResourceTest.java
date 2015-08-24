package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.Application;
import com.mycompany.myapp.domain.MultiRelationalDisplayFieldEntity;
import com.mycompany.myapp.repository.MultiRelationalDisplayFieldEntityRepository;
import com.mycompany.myapp.web.rest.dto.MultiRelationalDisplayFieldEntityDTO;
import com.mycompany.myapp.web.rest.mapper.MultiRelationalDisplayFieldEntityMapper;

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
 * Test class for the MultiRelationalDisplayFieldEntityResource REST controller.
 *
 * @see MultiRelationalDisplayFieldEntityResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class MultiRelationalDisplayFieldEntityResourceTest {


    @Inject
    private MultiRelationalDisplayFieldEntityRepository multiRelationalDisplayFieldEntityRepository;

    @Inject
    private MultiRelationalDisplayFieldEntityMapper multiRelationalDisplayFieldEntityMapper;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    private MockMvc restMultiRelationalDisplayFieldEntityMockMvc;

    private MultiRelationalDisplayFieldEntity multiRelationalDisplayFieldEntity;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        MultiRelationalDisplayFieldEntityResource multiRelationalDisplayFieldEntityResource = new MultiRelationalDisplayFieldEntityResource();
        ReflectionTestUtils.setField(multiRelationalDisplayFieldEntityResource, "multiRelationalDisplayFieldEntityRepository", multiRelationalDisplayFieldEntityRepository);
        ReflectionTestUtils.setField(multiRelationalDisplayFieldEntityResource, "multiRelationalDisplayFieldEntityMapper", multiRelationalDisplayFieldEntityMapper);
        this.restMultiRelationalDisplayFieldEntityMockMvc = MockMvcBuilders.standaloneSetup(multiRelationalDisplayFieldEntityResource).setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        multiRelationalDisplayFieldEntity = new MultiRelationalDisplayFieldEntity();
    }

    @Test
    @Transactional
    public void createMultiRelationalDisplayFieldEntity() throws Exception {
        int databaseSizeBeforeCreate = multiRelationalDisplayFieldEntityRepository.findAll().size();

        // Create the MultiRelationalDisplayFieldEntity
        MultiRelationalDisplayFieldEntityDTO multiRelationalDisplayFieldEntityDTO = multiRelationalDisplayFieldEntityMapper.multiRelationalDisplayFieldEntityToMultiRelationalDisplayFieldEntityDTO(multiRelationalDisplayFieldEntity);

        restMultiRelationalDisplayFieldEntityMockMvc.perform(post("/api/multiRelationalDisplayFieldEntitys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(multiRelationalDisplayFieldEntityDTO)))
                .andExpect(status().isCreated());

        // Validate the MultiRelationalDisplayFieldEntity in the database
        List<MultiRelationalDisplayFieldEntity> multiRelationalDisplayFieldEntitys = multiRelationalDisplayFieldEntityRepository.findAll();
        assertThat(multiRelationalDisplayFieldEntitys).hasSize(databaseSizeBeforeCreate + 1);
        MultiRelationalDisplayFieldEntity testMultiRelationalDisplayFieldEntity = multiRelationalDisplayFieldEntitys.get(multiRelationalDisplayFieldEntitys.size() - 1);
    }

    @Test
    @Transactional
    public void getAllMultiRelationalDisplayFieldEntitys() throws Exception {
        // Initialize the database
        multiRelationalDisplayFieldEntityRepository.saveAndFlush(multiRelationalDisplayFieldEntity);

        // Get all the multiRelationalDisplayFieldEntitys
        restMultiRelationalDisplayFieldEntityMockMvc.perform(get("/api/multiRelationalDisplayFieldEntitys"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(multiRelationalDisplayFieldEntity.getId().intValue())));
    }

    @Test
    @Transactional
    public void getMultiRelationalDisplayFieldEntity() throws Exception {
        // Initialize the database
        multiRelationalDisplayFieldEntityRepository.saveAndFlush(multiRelationalDisplayFieldEntity);

        // Get the multiRelationalDisplayFieldEntity
        restMultiRelationalDisplayFieldEntityMockMvc.perform(get("/api/multiRelationalDisplayFieldEntitys/{id}", multiRelationalDisplayFieldEntity.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(multiRelationalDisplayFieldEntity.getId().intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingMultiRelationalDisplayFieldEntity() throws Exception {
        // Get the multiRelationalDisplayFieldEntity
        restMultiRelationalDisplayFieldEntityMockMvc.perform(get("/api/multiRelationalDisplayFieldEntitys/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMultiRelationalDisplayFieldEntity() throws Exception {
        // Initialize the database
        multiRelationalDisplayFieldEntityRepository.saveAndFlush(multiRelationalDisplayFieldEntity);

		int databaseSizeBeforeUpdate = multiRelationalDisplayFieldEntityRepository.findAll().size();

        // Update the multiRelationalDisplayFieldEntity
        
        MultiRelationalDisplayFieldEntityDTO multiRelationalDisplayFieldEntityDTO = multiRelationalDisplayFieldEntityMapper.multiRelationalDisplayFieldEntityToMultiRelationalDisplayFieldEntityDTO(multiRelationalDisplayFieldEntity);

        restMultiRelationalDisplayFieldEntityMockMvc.perform(put("/api/multiRelationalDisplayFieldEntitys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(multiRelationalDisplayFieldEntityDTO)))
                .andExpect(status().isOk());

        // Validate the MultiRelationalDisplayFieldEntity in the database
        List<MultiRelationalDisplayFieldEntity> multiRelationalDisplayFieldEntitys = multiRelationalDisplayFieldEntityRepository.findAll();
        assertThat(multiRelationalDisplayFieldEntitys).hasSize(databaseSizeBeforeUpdate);
        MultiRelationalDisplayFieldEntity testMultiRelationalDisplayFieldEntity = multiRelationalDisplayFieldEntitys.get(multiRelationalDisplayFieldEntitys.size() - 1);
    }

    @Test
    @Transactional
    public void deleteMultiRelationalDisplayFieldEntity() throws Exception {
        // Initialize the database
        multiRelationalDisplayFieldEntityRepository.saveAndFlush(multiRelationalDisplayFieldEntity);

		int databaseSizeBeforeDelete = multiRelationalDisplayFieldEntityRepository.findAll().size();

        // Get the multiRelationalDisplayFieldEntity
        restMultiRelationalDisplayFieldEntityMockMvc.perform(delete("/api/multiRelationalDisplayFieldEntitys/{id}", multiRelationalDisplayFieldEntity.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<MultiRelationalDisplayFieldEntity> multiRelationalDisplayFieldEntitys = multiRelationalDisplayFieldEntityRepository.findAll();
        assertThat(multiRelationalDisplayFieldEntitys).hasSize(databaseSizeBeforeDelete - 1);
    }
}
