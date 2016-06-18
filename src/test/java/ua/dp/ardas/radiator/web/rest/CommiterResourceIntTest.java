package ua.dp.ardas.radiator.web.rest;

import ua.dp.ardas.radiator.RadiatorApp;
import ua.dp.ardas.radiator.domain.Commiter;
import ua.dp.ardas.radiator.repository.CommiterRepository;

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
 * Test class for the CommiterResource REST controller.
 *
 * @see CommiterResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = RadiatorApp.class)
@WebAppConfiguration
@IntegrationTest
public class CommiterResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";
    private static final String DEFAULT_EMAIL = "AAAAA";
    private static final String UPDATED_EMAIL = "BBBBB";

    @Inject
    private CommiterRepository commiterRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restCommiterMockMvc;

    private Commiter commiter;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        CommiterResource commiterResource = new CommiterResource();
        ReflectionTestUtils.setField(commiterResource, "commiterRepository", commiterRepository);
        this.restCommiterMockMvc = MockMvcBuilders.standaloneSetup(commiterResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        commiter = new Commiter();
        commiter.setName(DEFAULT_NAME);
        commiter.setEmail(DEFAULT_EMAIL);
    }

    @Test
    @Transactional
    public void createCommiter() throws Exception {
        int databaseSizeBeforeCreate = commiterRepository.findAll().size();

        // Create the Commiter

        restCommiterMockMvc.perform(post("/api/commiters")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(commiter)))
                .andExpect(status().isCreated());

        // Validate the Commiter in the database
        List<Commiter> commiters = commiterRepository.findAll();
        assertThat(commiters).hasSize(databaseSizeBeforeCreate + 1);
        Commiter testCommiter = commiters.get(commiters.size() - 1);
        assertThat(testCommiter.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCommiter.getEmail()).isEqualTo(DEFAULT_EMAIL);
    }

    @Test
    @Transactional
    public void getAllCommiters() throws Exception {
        // Initialize the database
        commiterRepository.saveAndFlush(commiter);

        // Get all the commiters
        restCommiterMockMvc.perform(get("/api/commiters?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(commiter.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())));
    }

    @Test
    @Transactional
    public void getCommiter() throws Exception {
        // Initialize the database
        commiterRepository.saveAndFlush(commiter);

        // Get the commiter
        restCommiterMockMvc.perform(get("/api/commiters/{id}", commiter.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(commiter.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCommiter() throws Exception {
        // Get the commiter
        restCommiterMockMvc.perform(get("/api/commiters/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCommiter() throws Exception {
        // Initialize the database
        commiterRepository.saveAndFlush(commiter);
        int databaseSizeBeforeUpdate = commiterRepository.findAll().size();

        // Update the commiter
        Commiter updatedCommiter = new Commiter();
        updatedCommiter.setId(commiter.getId());
        updatedCommiter.setName(UPDATED_NAME);
        updatedCommiter.setEmail(UPDATED_EMAIL);

        restCommiterMockMvc.perform(put("/api/commiters")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedCommiter)))
                .andExpect(status().isOk());

        // Validate the Commiter in the database
        List<Commiter> commiters = commiterRepository.findAll();
        assertThat(commiters).hasSize(databaseSizeBeforeUpdate);
        Commiter testCommiter = commiters.get(commiters.size() - 1);
        assertThat(testCommiter.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCommiter.getEmail()).isEqualTo(UPDATED_EMAIL);
    }

    @Test
    @Transactional
    public void deleteCommiter() throws Exception {
        // Initialize the database
        commiterRepository.saveAndFlush(commiter);
        int databaseSizeBeforeDelete = commiterRepository.findAll().size();

        // Get the commiter
        restCommiterMockMvc.perform(delete("/api/commiters/{id}", commiter.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Commiter> commiters = commiterRepository.findAll();
        assertThat(commiters).hasSize(databaseSizeBeforeDelete - 1);
    }
}
