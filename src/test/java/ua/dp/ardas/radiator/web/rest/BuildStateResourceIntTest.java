package ua.dp.ardas.radiator.web.rest;

import ua.dp.ardas.radiator.RadiatorApp;
import ua.dp.ardas.radiator.domain.BuildState;
import ua.dp.ardas.radiator.repository.BuildStateRepository;

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
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the BuildStateResource REST controller.
 *
 * @see BuildStateResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = RadiatorApp.class)
@WebAppConfiguration
@IntegrationTest
public class BuildStateResourceIntTest {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").withZone(ZoneId.of("Z"));

    private static final String DEFAULT_INSTANCES_NAME = "AAAAA";
    private static final String UPDATED_INSTANCES_NAME = "BBBBB";
    private static final String DEFAULT_STATE = "AAAAA";
    private static final String UPDATED_STATE = "BBBBB";
    private static final String DEFAULT_ERROR_MESSAGE = "AAAAA";
    private static final String UPDATED_ERROR_MESSAGE = "BBBBB";

    private static final Long DEFAULT_LAST_RUN_TIMESTEMP = 1L;
    private static final Long UPDATED_LAST_RUN_TIMESTEMP = 2L;

    private static final ZonedDateTime DEFAULT_EXTRACTING_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneId.systemDefault());
    private static final ZonedDateTime UPDATED_EXTRACTING_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_EXTRACTING_DATE_STR = dateTimeFormatter.format(DEFAULT_EXTRACTING_DATE);

    @Inject
    private BuildStateRepository buildStateRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restBuildStateMockMvc;

    private BuildState buildState;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        BuildStateResource buildStateResource = new BuildStateResource();
        ReflectionTestUtils.setField(buildStateResource, "buildStateRepository", buildStateRepository);
        this.restBuildStateMockMvc = MockMvcBuilders.standaloneSetup(buildStateResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        buildState = new BuildState();
        buildState.setInstancesName(DEFAULT_INSTANCES_NAME);
        buildState.setState(DEFAULT_STATE);
        buildState.setErrorMessage(DEFAULT_ERROR_MESSAGE);
        buildState.setLastRunTimestemp(DEFAULT_LAST_RUN_TIMESTEMP);
        buildState.setExtractingDate(DEFAULT_EXTRACTING_DATE);
    }

    @Test
    @Transactional
    public void createBuildState() throws Exception {
        int databaseSizeBeforeCreate = buildStateRepository.findAll().size();

        // Create the BuildState

        restBuildStateMockMvc.perform(post("/api/build-states")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(buildState)))
                .andExpect(status().isCreated());

        // Validate the BuildState in the database
        List<BuildState> buildStates = buildStateRepository.findAll();
        assertThat(buildStates).hasSize(databaseSizeBeforeCreate + 1);
        BuildState testBuildState = buildStates.get(buildStates.size() - 1);
        assertThat(testBuildState.getInstancesName()).isEqualTo(DEFAULT_INSTANCES_NAME);
        assertThat(testBuildState.getState()).isEqualTo(DEFAULT_STATE);
        assertThat(testBuildState.getErrorMessage()).isEqualTo(DEFAULT_ERROR_MESSAGE);
        assertThat(testBuildState.getLastRunTimestemp()).isEqualTo(DEFAULT_LAST_RUN_TIMESTEMP);
        assertThat(testBuildState.getExtractingDate()).isEqualTo(DEFAULT_EXTRACTING_DATE);
    }

    @Test
    @Transactional
    public void getAllBuildStates() throws Exception {
        // Initialize the database
        buildStateRepository.saveAndFlush(buildState);

        // Get all the buildStates
        restBuildStateMockMvc.perform(get("/api/build-states?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(buildState.getId().intValue())))
                .andExpect(jsonPath("$.[*].instancesName").value(hasItem(DEFAULT_INSTANCES_NAME.toString())))
                .andExpect(jsonPath("$.[*].state").value(hasItem(DEFAULT_STATE.toString())))
                .andExpect(jsonPath("$.[*].errorMessage").value(hasItem(DEFAULT_ERROR_MESSAGE.toString())))
                .andExpect(jsonPath("$.[*].lastRunTimestemp").value(hasItem(DEFAULT_LAST_RUN_TIMESTEMP.intValue())))
                .andExpect(jsonPath("$.[*].extractingDate").value(hasItem(DEFAULT_EXTRACTING_DATE_STR)));
    }

    @Test
    @Transactional
    public void getBuildState() throws Exception {
        // Initialize the database
        buildStateRepository.saveAndFlush(buildState);

        // Get the buildState
        restBuildStateMockMvc.perform(get("/api/build-states/{id}", buildState.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(buildState.getId().intValue()))
            .andExpect(jsonPath("$.instancesName").value(DEFAULT_INSTANCES_NAME.toString()))
            .andExpect(jsonPath("$.state").value(DEFAULT_STATE.toString()))
            .andExpect(jsonPath("$.errorMessage").value(DEFAULT_ERROR_MESSAGE.toString()))
            .andExpect(jsonPath("$.lastRunTimestemp").value(DEFAULT_LAST_RUN_TIMESTEMP.intValue()))
            .andExpect(jsonPath("$.extractingDate").value(DEFAULT_EXTRACTING_DATE_STR));
    }

    @Test
    @Transactional
    public void getNonExistingBuildState() throws Exception {
        // Get the buildState
        restBuildStateMockMvc.perform(get("/api/build-states/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBuildState() throws Exception {
        // Initialize the database
        buildStateRepository.saveAndFlush(buildState);
        int databaseSizeBeforeUpdate = buildStateRepository.findAll().size();

        // Update the buildState
        BuildState updatedBuildState = new BuildState();
        updatedBuildState.setId(buildState.getId());
        updatedBuildState.setInstancesName(UPDATED_INSTANCES_NAME);
        updatedBuildState.setState(UPDATED_STATE);
        updatedBuildState.setErrorMessage(UPDATED_ERROR_MESSAGE);
        updatedBuildState.setLastRunTimestemp(UPDATED_LAST_RUN_TIMESTEMP);
        updatedBuildState.setExtractingDate(UPDATED_EXTRACTING_DATE);

        restBuildStateMockMvc.perform(put("/api/build-states")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedBuildState)))
                .andExpect(status().isOk());

        // Validate the BuildState in the database
        List<BuildState> buildStates = buildStateRepository.findAll();
        assertThat(buildStates).hasSize(databaseSizeBeforeUpdate);
        BuildState testBuildState = buildStates.get(buildStates.size() - 1);
        assertThat(testBuildState.getInstancesName()).isEqualTo(UPDATED_INSTANCES_NAME);
        assertThat(testBuildState.getState()).isEqualTo(UPDATED_STATE);
        assertThat(testBuildState.getErrorMessage()).isEqualTo(UPDATED_ERROR_MESSAGE);
        assertThat(testBuildState.getLastRunTimestemp()).isEqualTo(UPDATED_LAST_RUN_TIMESTEMP);
        assertThat(testBuildState.getExtractingDate()).isEqualTo(UPDATED_EXTRACTING_DATE);
    }

    @Test
    @Transactional
    public void deleteBuildState() throws Exception {
        // Initialize the database
        buildStateRepository.saveAndFlush(buildState);
        int databaseSizeBeforeDelete = buildStateRepository.findAll().size();

        // Get the buildState
        restBuildStateMockMvc.perform(delete("/api/build-states/{id}", buildState.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<BuildState> buildStates = buildStateRepository.findAll();
        assertThat(buildStates).hasSize(databaseSizeBeforeDelete - 1);
    }
}
