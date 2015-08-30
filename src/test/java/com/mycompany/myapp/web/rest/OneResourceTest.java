package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.Application;
import com.mycompany.myapp.domain.One;
import com.mycompany.myapp.repository.OneRepository;

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
import org.springframework.util.Base64Utils;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the OneResource REST controller.
 *
 * @see OneResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class OneResourceTest {


    private static final byte[] DEFAULT_IMAGE = TestUtil.createByteArray(6000, "0");
    private static final byte[] UPDATED_IMAGE = TestUtil.createByteArray(10000, "1");

    @Inject
    private OneRepository oneRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    private MockMvc restOneMockMvc;

    private One one;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        OneResource oneResource = new OneResource();
        ReflectionTestUtils.setField(oneResource, "oneRepository", oneRepository);
        this.restOneMockMvc = MockMvcBuilders.standaloneSetup(oneResource).setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        one = new One();
        one.setImage(DEFAULT_IMAGE);
    }

    @Test
    @Transactional
    public void createOne() throws Exception {
        int databaseSizeBeforeCreate = oneRepository.findAll().size();

        // Create the One

        restOneMockMvc.perform(post("/api/ones")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(one)))
                .andExpect(status().isCreated());

        // Validate the One in the database
        List<One> ones = oneRepository.findAll();
        assertThat(ones).hasSize(databaseSizeBeforeCreate + 1);
        One testOne = ones.get(ones.size() - 1);
        assertThat(testOne.getImage()).isEqualTo(DEFAULT_IMAGE);
    }

    @Test
    @Transactional
    public void checkImageIsRequired() throws Exception {
        int databaseSizeBeforeTest = oneRepository.findAll().size();
        // set the field null
        one.setImage(null);

        // Create the One, which fails.

        restOneMockMvc.perform(post("/api/ones")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(one)))
                .andExpect(status().isBadRequest());

        List<One> ones = oneRepository.findAll();
        assertThat(ones).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllOnes() throws Exception {
        // Initialize the database
        oneRepository.saveAndFlush(one);

        // Get all the ones
        restOneMockMvc.perform(get("/api/ones"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(one.getId().intValue())))
                .andExpect(jsonPath("$.[*].image").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGE))));
    }

    @Test
    @Transactional
    public void getOne() throws Exception {
        // Initialize the database
        oneRepository.saveAndFlush(one);

        // Get the one
        restOneMockMvc.perform(get("/api/ones/{id}", one.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(one.getId().intValue()))
            .andExpect(jsonPath("$.image").value(Base64Utils.encodeToString(DEFAULT_IMAGE)));
    }

    @Test
    @Transactional
    public void getNonExistingOne() throws Exception {
        // Get the one
        restOneMockMvc.perform(get("/api/ones/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOne() throws Exception {
        // Initialize the database
        oneRepository.saveAndFlush(one);

		int databaseSizeBeforeUpdate = oneRepository.findAll().size();

        // Update the one
        one.setImage(UPDATED_IMAGE);
        

        restOneMockMvc.perform(put("/api/ones")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(one)))
                .andExpect(status().isOk());

        // Validate the One in the database
        List<One> ones = oneRepository.findAll();
        assertThat(ones).hasSize(databaseSizeBeforeUpdate);
        One testOne = ones.get(ones.size() - 1);
        assertThat(testOne.getImage()).isEqualTo(UPDATED_IMAGE);
    }

    @Test
    @Transactional
    public void deleteOne() throws Exception {
        // Initialize the database
        oneRepository.saveAndFlush(one);

		int databaseSizeBeforeDelete = oneRepository.findAll().size();

        // Get the one
        restOneMockMvc.perform(delete("/api/ones/{id}", one.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<One> ones = oneRepository.findAll();
        assertThat(ones).hasSize(databaseSizeBeforeDelete - 1);
    }
}
