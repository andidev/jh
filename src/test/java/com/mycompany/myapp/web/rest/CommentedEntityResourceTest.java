package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.Application;
import com.mycompany.myapp.domain.CommentedEntity;
import com.mycompany.myapp.repository.CommentedEntityRepository;

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
 * Test class for the CommentedEntityResource REST controller.
 *
 * @see CommentedEntityResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class CommentedEntityResourceTest {

    private static final String DEFAULT_COMMENTED_FIELD = "SAMPLE_TEXT";
    private static final String UPDATED_COMMENTED_FIELD = "UPDATED_TEXT";

    @Inject
    private CommentedEntityRepository commentedEntityRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    private MockMvc restCommentedEntityMockMvc;

    private CommentedEntity commentedEntity;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        CommentedEntityResource commentedEntityResource = new CommentedEntityResource();
        ReflectionTestUtils.setField(commentedEntityResource, "commentedEntityRepository", commentedEntityRepository);
        this.restCommentedEntityMockMvc = MockMvcBuilders.standaloneSetup(commentedEntityResource).setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        commentedEntity = new CommentedEntity();
        commentedEntity.setCommentedField(DEFAULT_COMMENTED_FIELD);
    }

    @Test
    @Transactional
    public void createCommentedEntity() throws Exception {
        int databaseSizeBeforeCreate = commentedEntityRepository.findAll().size();

        // Create the CommentedEntity

        restCommentedEntityMockMvc.perform(post("/api/commentedEntitys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(commentedEntity)))
                .andExpect(status().isCreated());

        // Validate the CommentedEntity in the database
        List<CommentedEntity> commentedEntitys = commentedEntityRepository.findAll();
        assertThat(commentedEntitys).hasSize(databaseSizeBeforeCreate + 1);
        CommentedEntity testCommentedEntity = commentedEntitys.get(commentedEntitys.size() - 1);
        assertThat(testCommentedEntity.getCommentedField()).isEqualTo(DEFAULT_COMMENTED_FIELD);
    }

    @Test
    @Transactional
    public void getAllCommentedEntitys() throws Exception {
        // Initialize the database
        commentedEntityRepository.saveAndFlush(commentedEntity);

        // Get all the commentedEntitys
        restCommentedEntityMockMvc.perform(get("/api/commentedEntitys"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(commentedEntity.getId().intValue())))
                .andExpect(jsonPath("$.[*].commentedField").value(hasItem(DEFAULT_COMMENTED_FIELD.toString())));
    }

    @Test
    @Transactional
    public void getCommentedEntity() throws Exception {
        // Initialize the database
        commentedEntityRepository.saveAndFlush(commentedEntity);

        // Get the commentedEntity
        restCommentedEntityMockMvc.perform(get("/api/commentedEntitys/{id}", commentedEntity.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(commentedEntity.getId().intValue()))
            .andExpect(jsonPath("$.commentedField").value(DEFAULT_COMMENTED_FIELD.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCommentedEntity() throws Exception {
        // Get the commentedEntity
        restCommentedEntityMockMvc.perform(get("/api/commentedEntitys/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCommentedEntity() throws Exception {
        // Initialize the database
        commentedEntityRepository.saveAndFlush(commentedEntity);

		int databaseSizeBeforeUpdate = commentedEntityRepository.findAll().size();

        // Update the commentedEntity
        commentedEntity.setCommentedField(UPDATED_COMMENTED_FIELD);
        

        restCommentedEntityMockMvc.perform(put("/api/commentedEntitys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(commentedEntity)))
                .andExpect(status().isOk());

        // Validate the CommentedEntity in the database
        List<CommentedEntity> commentedEntitys = commentedEntityRepository.findAll();
        assertThat(commentedEntitys).hasSize(databaseSizeBeforeUpdate);
        CommentedEntity testCommentedEntity = commentedEntitys.get(commentedEntitys.size() - 1);
        assertThat(testCommentedEntity.getCommentedField()).isEqualTo(UPDATED_COMMENTED_FIELD);
    }

    @Test
    @Transactional
    public void deleteCommentedEntity() throws Exception {
        // Initialize the database
        commentedEntityRepository.saveAndFlush(commentedEntity);

		int databaseSizeBeforeDelete = commentedEntityRepository.findAll().size();

        // Get the commentedEntity
        restCommentedEntityMockMvc.perform(delete("/api/commentedEntitys/{id}", commentedEntity.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<CommentedEntity> commentedEntitys = commentedEntityRepository.findAll();
        assertThat(commentedEntitys).hasSize(databaseSizeBeforeDelete - 1);
    }
}
