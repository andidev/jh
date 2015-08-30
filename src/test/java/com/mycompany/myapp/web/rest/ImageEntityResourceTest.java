package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.Application;
import com.mycompany.myapp.domain.ImageEntity;
import com.mycompany.myapp.repository.ImageEntityRepository;

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
 * Test class for the ImageEntityResource REST controller.
 *
 * @see ImageEntityResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class ImageEntityResourceTest {


    private static final byte[] DEFAULT_IMAGE_FIELD = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_IMAGE_FIELD = TestUtil.createByteArray(2, "1");

    private static final byte[] DEFAULT_VALIDATED_IMAGE_FIELD = TestUtil.createByteArray(6000, "0");
    private static final byte[] UPDATED_VALIDATED_IMAGE_FIELD = TestUtil.createByteArray(10000, "1");

    private static final byte[] DEFAULT_BLOB_FIELD = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_BLOB_FIELD = TestUtil.createByteArray(2, "1");

    private static final byte[] DEFAULT_VALIDATED_BLOB_FIELD = TestUtil.createByteArray(6000, "0");
    private static final byte[] UPDATED_VALIDATED_BLOB_FIELD = TestUtil.createByteArray(10000, "1");
    private static final String DEFAULT_STRING_FIELD = "SAMPLE_TEXT";
    private static final String UPDATED_STRING_FIELD = "UPDATED_TEXT";

    @Inject
    private ImageEntityRepository imageEntityRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    private MockMvc restImageEntityMockMvc;

    private ImageEntity imageEntity;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ImageEntityResource imageEntityResource = new ImageEntityResource();
        ReflectionTestUtils.setField(imageEntityResource, "imageEntityRepository", imageEntityRepository);
        this.restImageEntityMockMvc = MockMvcBuilders.standaloneSetup(imageEntityResource).setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        imageEntity = new ImageEntity();
        imageEntity.setImageField(DEFAULT_IMAGE_FIELD);
        imageEntity.setValidatedImageField(DEFAULT_VALIDATED_IMAGE_FIELD);
        imageEntity.setBlobField(DEFAULT_BLOB_FIELD);
        imageEntity.setValidatedBlobField(DEFAULT_VALIDATED_BLOB_FIELD);
        imageEntity.setStringField(DEFAULT_STRING_FIELD);
    }

    @Test
    @Transactional
    public void createImageEntity() throws Exception {
        int databaseSizeBeforeCreate = imageEntityRepository.findAll().size();

        // Create the ImageEntity

        restImageEntityMockMvc.perform(post("/api/imageEntitys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(imageEntity)))
                .andExpect(status().isCreated());

        // Validate the ImageEntity in the database
        List<ImageEntity> imageEntitys = imageEntityRepository.findAll();
        assertThat(imageEntitys).hasSize(databaseSizeBeforeCreate + 1);
        ImageEntity testImageEntity = imageEntitys.get(imageEntitys.size() - 1);
        assertThat(testImageEntity.getImageField()).isEqualTo(DEFAULT_IMAGE_FIELD);
        assertThat(testImageEntity.getValidatedImageField()).isEqualTo(DEFAULT_VALIDATED_IMAGE_FIELD);
        assertThat(testImageEntity.getBlobField()).isEqualTo(DEFAULT_BLOB_FIELD);
        assertThat(testImageEntity.getValidatedBlobField()).isEqualTo(DEFAULT_VALIDATED_BLOB_FIELD);
        assertThat(testImageEntity.getStringField()).isEqualTo(DEFAULT_STRING_FIELD);
    }

    @Test
    @Transactional
    public void checkValidatedImageFieldIsRequired() throws Exception {
        int databaseSizeBeforeTest = imageEntityRepository.findAll().size();
        // set the field null
        imageEntity.setValidatedImageField(null);

        // Create the ImageEntity, which fails.

        restImageEntityMockMvc.perform(post("/api/imageEntitys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(imageEntity)))
                .andExpect(status().isBadRequest());

        List<ImageEntity> imageEntitys = imageEntityRepository.findAll();
        assertThat(imageEntitys).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkValidatedBlobFieldIsRequired() throws Exception {
        int databaseSizeBeforeTest = imageEntityRepository.findAll().size();
        // set the field null
        imageEntity.setValidatedBlobField(null);

        // Create the ImageEntity, which fails.

        restImageEntityMockMvc.perform(post("/api/imageEntitys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(imageEntity)))
                .andExpect(status().isBadRequest());

        List<ImageEntity> imageEntitys = imageEntityRepository.findAll();
        assertThat(imageEntitys).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllImageEntitys() throws Exception {
        // Initialize the database
        imageEntityRepository.saveAndFlush(imageEntity);

        // Get all the imageEntitys
        restImageEntityMockMvc.perform(get("/api/imageEntitys"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(imageEntity.getId().intValue())))
                .andExpect(jsonPath("$.[*].imageField").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGE_FIELD))))
                .andExpect(jsonPath("$.[*].validatedImageField").value(hasItem(Base64Utils.encodeToString(DEFAULT_VALIDATED_IMAGE_FIELD))))
                .andExpect(jsonPath("$.[*].blobField").value(hasItem(Base64Utils.encodeToString(DEFAULT_BLOB_FIELD))))
                .andExpect(jsonPath("$.[*].validatedBlobField").value(hasItem(Base64Utils.encodeToString(DEFAULT_VALIDATED_BLOB_FIELD))))
                .andExpect(jsonPath("$.[*].stringField").value(hasItem(DEFAULT_STRING_FIELD.toString())));
    }

    @Test
    @Transactional
    public void getImageEntity() throws Exception {
        // Initialize the database
        imageEntityRepository.saveAndFlush(imageEntity);

        // Get the imageEntity
        restImageEntityMockMvc.perform(get("/api/imageEntitys/{id}", imageEntity.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(imageEntity.getId().intValue()))
            .andExpect(jsonPath("$.imageField").value(Base64Utils.encodeToString(DEFAULT_IMAGE_FIELD)))
            .andExpect(jsonPath("$.validatedImageField").value(Base64Utils.encodeToString(DEFAULT_VALIDATED_IMAGE_FIELD)))
            .andExpect(jsonPath("$.blobField").value(Base64Utils.encodeToString(DEFAULT_BLOB_FIELD)))
            .andExpect(jsonPath("$.validatedBlobField").value(Base64Utils.encodeToString(DEFAULT_VALIDATED_BLOB_FIELD)))
            .andExpect(jsonPath("$.stringField").value(DEFAULT_STRING_FIELD.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingImageEntity() throws Exception {
        // Get the imageEntity
        restImageEntityMockMvc.perform(get("/api/imageEntitys/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateImageEntity() throws Exception {
        // Initialize the database
        imageEntityRepository.saveAndFlush(imageEntity);

		int databaseSizeBeforeUpdate = imageEntityRepository.findAll().size();

        // Update the imageEntity
        imageEntity.setImageField(UPDATED_IMAGE_FIELD);
        imageEntity.setValidatedImageField(UPDATED_VALIDATED_IMAGE_FIELD);
        imageEntity.setBlobField(UPDATED_BLOB_FIELD);
        imageEntity.setValidatedBlobField(UPDATED_VALIDATED_BLOB_FIELD);
        imageEntity.setStringField(UPDATED_STRING_FIELD);
        

        restImageEntityMockMvc.perform(put("/api/imageEntitys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(imageEntity)))
                .andExpect(status().isOk());

        // Validate the ImageEntity in the database
        List<ImageEntity> imageEntitys = imageEntityRepository.findAll();
        assertThat(imageEntitys).hasSize(databaseSizeBeforeUpdate);
        ImageEntity testImageEntity = imageEntitys.get(imageEntitys.size() - 1);
        assertThat(testImageEntity.getImageField()).isEqualTo(UPDATED_IMAGE_FIELD);
        assertThat(testImageEntity.getValidatedImageField()).isEqualTo(UPDATED_VALIDATED_IMAGE_FIELD);
        assertThat(testImageEntity.getBlobField()).isEqualTo(UPDATED_BLOB_FIELD);
        assertThat(testImageEntity.getValidatedBlobField()).isEqualTo(UPDATED_VALIDATED_BLOB_FIELD);
        assertThat(testImageEntity.getStringField()).isEqualTo(UPDATED_STRING_FIELD);
    }

    @Test
    @Transactional
    public void deleteImageEntity() throws Exception {
        // Initialize the database
        imageEntityRepository.saveAndFlush(imageEntity);

		int databaseSizeBeforeDelete = imageEntityRepository.findAll().size();

        // Get the imageEntity
        restImageEntityMockMvc.perform(delete("/api/imageEntitys/{id}", imageEntity.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<ImageEntity> imageEntitys = imageEntityRepository.findAll();
        assertThat(imageEntitys).hasSize(databaseSizeBeforeDelete - 1);
    }
}
