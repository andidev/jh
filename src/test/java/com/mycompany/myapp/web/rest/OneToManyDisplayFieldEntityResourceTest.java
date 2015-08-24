package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.Application;
import com.mycompany.myapp.domain.OneToManyDisplayFieldEntity;
import com.mycompany.myapp.repository.OneToManyDisplayFieldEntityRepository;
import com.mycompany.myapp.web.rest.dto.OneToManyDisplayFieldEntityDTO;
import com.mycompany.myapp.web.rest.mapper.OneToManyDisplayFieldEntityMapper;

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
 * Test class for the OneToManyDisplayFieldEntityResource REST controller.
 *
 * @see OneToManyDisplayFieldEntityResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class OneToManyDisplayFieldEntityResourceTest {

    private static final String DEFAULT_DISPLAY_FIELD = "SAMPLE_TEXT";
    private static final String UPDATED_DISPLAY_FIELD = "UPDATED_TEXT";

    @Inject
    private OneToManyDisplayFieldEntityRepository oneToManyDisplayFieldEntityRepository;

    @Inject
    private OneToManyDisplayFieldEntityMapper oneToManyDisplayFieldEntityMapper;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    private MockMvc restOneToManyDisplayFieldEntityMockMvc;

    private OneToManyDisplayFieldEntity oneToManyDisplayFieldEntity;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        OneToManyDisplayFieldEntityResource oneToManyDisplayFieldEntityResource = new OneToManyDisplayFieldEntityResource();
        ReflectionTestUtils.setField(oneToManyDisplayFieldEntityResource, "oneToManyDisplayFieldEntityRepository", oneToManyDisplayFieldEntityRepository);
        ReflectionTestUtils.setField(oneToManyDisplayFieldEntityResource, "oneToManyDisplayFieldEntityMapper", oneToManyDisplayFieldEntityMapper);
        this.restOneToManyDisplayFieldEntityMockMvc = MockMvcBuilders.standaloneSetup(oneToManyDisplayFieldEntityResource).setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        oneToManyDisplayFieldEntity = new OneToManyDisplayFieldEntity();
        oneToManyDisplayFieldEntity.setDisplayField(DEFAULT_DISPLAY_FIELD);
    }

    @Test
    @Transactional
    public void createOneToManyDisplayFieldEntity() throws Exception {
        int databaseSizeBeforeCreate = oneToManyDisplayFieldEntityRepository.findAll().size();

        // Create the OneToManyDisplayFieldEntity
        OneToManyDisplayFieldEntityDTO oneToManyDisplayFieldEntityDTO = oneToManyDisplayFieldEntityMapper.oneToManyDisplayFieldEntityToOneToManyDisplayFieldEntityDTO(oneToManyDisplayFieldEntity);

        restOneToManyDisplayFieldEntityMockMvc.perform(post("/api/oneToManyDisplayFieldEntitys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(oneToManyDisplayFieldEntityDTO)))
                .andExpect(status().isCreated());

        // Validate the OneToManyDisplayFieldEntity in the database
        List<OneToManyDisplayFieldEntity> oneToManyDisplayFieldEntitys = oneToManyDisplayFieldEntityRepository.findAll();
        assertThat(oneToManyDisplayFieldEntitys).hasSize(databaseSizeBeforeCreate + 1);
        OneToManyDisplayFieldEntity testOneToManyDisplayFieldEntity = oneToManyDisplayFieldEntitys.get(oneToManyDisplayFieldEntitys.size() - 1);
        assertThat(testOneToManyDisplayFieldEntity.getDisplayField()).isEqualTo(DEFAULT_DISPLAY_FIELD);
    }

    @Test
    @Transactional
    public void getAllOneToManyDisplayFieldEntitys() throws Exception {
        // Initialize the database
        oneToManyDisplayFieldEntityRepository.saveAndFlush(oneToManyDisplayFieldEntity);

        // Get all the oneToManyDisplayFieldEntitys
        restOneToManyDisplayFieldEntityMockMvc.perform(get("/api/oneToManyDisplayFieldEntitys"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(oneToManyDisplayFieldEntity.getId().intValue())))
                .andExpect(jsonPath("$.[*].displayField").value(hasItem(DEFAULT_DISPLAY_FIELD.toString())));
    }

    @Test
    @Transactional
    public void getOneToManyDisplayFieldEntity() throws Exception {
        // Initialize the database
        oneToManyDisplayFieldEntityRepository.saveAndFlush(oneToManyDisplayFieldEntity);

        // Get the oneToManyDisplayFieldEntity
        restOneToManyDisplayFieldEntityMockMvc.perform(get("/api/oneToManyDisplayFieldEntitys/{id}", oneToManyDisplayFieldEntity.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(oneToManyDisplayFieldEntity.getId().intValue()))
            .andExpect(jsonPath("$.displayField").value(DEFAULT_DISPLAY_FIELD.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingOneToManyDisplayFieldEntity() throws Exception {
        // Get the oneToManyDisplayFieldEntity
        restOneToManyDisplayFieldEntityMockMvc.perform(get("/api/oneToManyDisplayFieldEntitys/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOneToManyDisplayFieldEntity() throws Exception {
        // Initialize the database
        oneToManyDisplayFieldEntityRepository.saveAndFlush(oneToManyDisplayFieldEntity);

		int databaseSizeBeforeUpdate = oneToManyDisplayFieldEntityRepository.findAll().size();

        // Update the oneToManyDisplayFieldEntity
        oneToManyDisplayFieldEntity.setDisplayField(UPDATED_DISPLAY_FIELD);
        
        OneToManyDisplayFieldEntityDTO oneToManyDisplayFieldEntityDTO = oneToManyDisplayFieldEntityMapper.oneToManyDisplayFieldEntityToOneToManyDisplayFieldEntityDTO(oneToManyDisplayFieldEntity);

        restOneToManyDisplayFieldEntityMockMvc.perform(put("/api/oneToManyDisplayFieldEntitys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(oneToManyDisplayFieldEntityDTO)))
                .andExpect(status().isOk());

        // Validate the OneToManyDisplayFieldEntity in the database
        List<OneToManyDisplayFieldEntity> oneToManyDisplayFieldEntitys = oneToManyDisplayFieldEntityRepository.findAll();
        assertThat(oneToManyDisplayFieldEntitys).hasSize(databaseSizeBeforeUpdate);
        OneToManyDisplayFieldEntity testOneToManyDisplayFieldEntity = oneToManyDisplayFieldEntitys.get(oneToManyDisplayFieldEntitys.size() - 1);
        assertThat(testOneToManyDisplayFieldEntity.getDisplayField()).isEqualTo(UPDATED_DISPLAY_FIELD);
    }

    @Test
    @Transactional
    public void deleteOneToManyDisplayFieldEntity() throws Exception {
        // Initialize the database
        oneToManyDisplayFieldEntityRepository.saveAndFlush(oneToManyDisplayFieldEntity);

		int databaseSizeBeforeDelete = oneToManyDisplayFieldEntityRepository.findAll().size();

        // Get the oneToManyDisplayFieldEntity
        restOneToManyDisplayFieldEntityMockMvc.perform(delete("/api/oneToManyDisplayFieldEntitys/{id}", oneToManyDisplayFieldEntity.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<OneToManyDisplayFieldEntity> oneToManyDisplayFieldEntitys = oneToManyDisplayFieldEntityRepository.findAll();
        assertThat(oneToManyDisplayFieldEntitys).hasSize(databaseSizeBeforeDelete - 1);
    }
}
