package ua.dp.ardas.radiator.web.rest;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import ua.dp.ardas.radiator.RadiatorApp;
import ua.dp.ardas.radiator.domain.IntegrationTestResult;
import ua.dp.ardas.radiator.repository.IntegrationTestResultRepository;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the IntegrationTestResultResource REST controller.
 *
 * @see IntegrationTestResultResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = RadiatorApp.class)
@WebAppConfiguration
@IntegrationTest
public class IntegrationTestResultResourceIntTest {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").withZone(ZoneId.of("Z"));

    private static final String DEFAULT_INSTANCES_NAME = "AAAAA";
    private static final String UPDATED_INSTANCES_NAME = "BBBBB";

    private static final Long DEFAULT_TOTAL = 1L;
    private static final Long UPDATED_TOTAL = 2L;

    private static final Long DEFAULT_PASSED = 1L;
    private static final Long UPDATED_PASSED = 2L;

    private static final Long DEFAULT_PENDING = 1L;
    private static final Long UPDATED_PENDING = 2L;

    private static final Long DEFAULT_FAILED = 1L;
    private static final Long UPDATED_FAILED = 2L;

    private static final ZonedDateTime DEFAULT_EXTRACTING_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneId.systemDefault());
    private static final ZonedDateTime UPDATED_EXTRACTING_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_EXTRACTING_DATE_STR = dateTimeFormatter.format(DEFAULT_EXTRACTING_DATE);

    @Inject
    private IntegrationTestResultRepository integrationTestResultRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restIntegrationTestResultMockMvc;

    private IntegrationTestResult integrationTestResult;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        IntegrationTestResultResource integrationTestResultResource = new IntegrationTestResultResource();
        ReflectionTestUtils.setField(integrationTestResultResource, "integrationTestResultRepository", integrationTestResultRepository);
        this.restIntegrationTestResultMockMvc = MockMvcBuilders.standaloneSetup(integrationTestResultResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        integrationTestResult = new IntegrationTestResult();
        integrationTestResult.setInstancesName(DEFAULT_INSTANCES_NAME);
        integrationTestResult.setTotal(DEFAULT_TOTAL);
        integrationTestResult.setPassed(DEFAULT_PASSED);
        integrationTestResult.setPending(DEFAULT_PENDING);
        integrationTestResult.setFailed(DEFAULT_FAILED);
        integrationTestResult.setExtractingDate(DEFAULT_EXTRACTING_DATE);
    }

    @Test
    @Transactional
    public void createIntegrationTestResult() throws Exception {
        int databaseSizeBeforeCreate = integrationTestResultRepository.findAll().size();

        // Create the IntegrationTestResult

        restIntegrationTestResultMockMvc.perform(post("/api/integration-test-results")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(integrationTestResult)))
                .andExpect(status().isCreated());

        // Validate the IntegrationTestResult in the database
        List<IntegrationTestResult> integrationTestResults = integrationTestResultRepository.findAll();
        assertThat(integrationTestResults).hasSize(databaseSizeBeforeCreate + 1);
        IntegrationTestResult testIntegrationTestResult = integrationTestResults.get(integrationTestResults.size() - 1);
        assertThat(testIntegrationTestResult.getInstancesName()).isEqualTo(DEFAULT_INSTANCES_NAME);
        assertThat(testIntegrationTestResult.getTotal()).isEqualTo(DEFAULT_TOTAL);
        assertThat(testIntegrationTestResult.getPassed()).isEqualTo(DEFAULT_PASSED);
        assertThat(testIntegrationTestResult.getPending()).isEqualTo(DEFAULT_PENDING);
        assertThat(testIntegrationTestResult.getFailed()).isEqualTo(DEFAULT_FAILED);
        assertThat(testIntegrationTestResult.getExtractingDate()).isEqualTo(DEFAULT_EXTRACTING_DATE);
    }

    @Test
    @Transactional
    public void getAllIntegrationTestResults() throws Exception {
        // Initialize the database
        integrationTestResultRepository.saveAndFlush(integrationTestResult);

        // Get all the integrationTestResults
        restIntegrationTestResultMockMvc.perform(get("/api/integration-test-results?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(integrationTestResult.getId().intValue())))
                .andExpect(jsonPath("$.[*].instancesName").value(hasItem(DEFAULT_INSTANCES_NAME.toString())))
                .andExpect(jsonPath("$.[*].total").value(hasItem(DEFAULT_TOTAL.intValue())))
                .andExpect(jsonPath("$.[*].passed").value(hasItem(DEFAULT_PASSED.intValue())))
                .andExpect(jsonPath("$.[*].pending").value(hasItem(DEFAULT_PENDING.intValue())))
                .andExpect(jsonPath("$.[*].failed").value(hasItem(DEFAULT_FAILED.intValue())))
                .andExpect(jsonPath("$.[*].extractingDate").value(hasItem(DEFAULT_EXTRACTING_DATE_STR)));
    }

    @Test
    @Transactional
    public void getIntegrationTestResult() throws Exception {
        // Initialize the database
        integrationTestResultRepository.saveAndFlush(integrationTestResult);

        // Get the integrationTestResult
        restIntegrationTestResultMockMvc.perform(get("/api/integration-test-results/{id}", integrationTestResult.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(integrationTestResult.getId().intValue()))
            .andExpect(jsonPath("$.instancesName").value(DEFAULT_INSTANCES_NAME.toString()))
            .andExpect(jsonPath("$.total").value(DEFAULT_TOTAL.intValue()))
            .andExpect(jsonPath("$.passed").value(DEFAULT_PASSED.intValue()))
            .andExpect(jsonPath("$.pending").value(DEFAULT_PENDING.intValue()))
            .andExpect(jsonPath("$.failed").value(DEFAULT_FAILED.intValue()))
            .andExpect(jsonPath("$.extractingDate").value(DEFAULT_EXTRACTING_DATE_STR));
    }

    @Test
    @Transactional
    public void getNonExistingIntegrationTestResult() throws Exception {
        // Get the integrationTestResult
        restIntegrationTestResultMockMvc.perform(get("/api/integration-test-results/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateIntegrationTestResult() throws Exception {
        // Initialize the database
        integrationTestResultRepository.saveAndFlush(integrationTestResult);
        int databaseSizeBeforeUpdate = integrationTestResultRepository.findAll().size();

        // Update the integrationTestResult
        IntegrationTestResult updatedIntegrationTestResult = new IntegrationTestResult();
        updatedIntegrationTestResult.setId(integrationTestResult.getId());
        updatedIntegrationTestResult.setInstancesName(UPDATED_INSTANCES_NAME);
        updatedIntegrationTestResult.setTotal(UPDATED_TOTAL);
        updatedIntegrationTestResult.setPassed(UPDATED_PASSED);
        updatedIntegrationTestResult.setPending(UPDATED_PENDING);
        updatedIntegrationTestResult.setFailed(UPDATED_FAILED);
        updatedIntegrationTestResult.setExtractingDate(UPDATED_EXTRACTING_DATE);

        restIntegrationTestResultMockMvc.perform(put("/api/integration-test-results")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedIntegrationTestResult)))
                .andExpect(status().isOk());

        // Validate the IntegrationTestResult in the database
        List<IntegrationTestResult> integrationTestResults = integrationTestResultRepository.findAll();
        assertThat(integrationTestResults).hasSize(databaseSizeBeforeUpdate);
        IntegrationTestResult testIntegrationTestResult = integrationTestResults.get(integrationTestResults.size() - 1);
        assertThat(testIntegrationTestResult.getInstancesName()).isEqualTo(UPDATED_INSTANCES_NAME);
        assertThat(testIntegrationTestResult.getTotal()).isEqualTo(UPDATED_TOTAL);
        assertThat(testIntegrationTestResult.getPassed()).isEqualTo(UPDATED_PASSED);
        assertThat(testIntegrationTestResult.getPending()).isEqualTo(UPDATED_PENDING);
        assertThat(testIntegrationTestResult.getFailed()).isEqualTo(UPDATED_FAILED);
        assertThat(testIntegrationTestResult.getExtractingDate()).isEqualTo(UPDATED_EXTRACTING_DATE);
    }

    @Test
    @Transactional
    public void deleteIntegrationTestResult() throws Exception {
        // Initialize the database
        integrationTestResultRepository.saveAndFlush(integrationTestResult);
        int databaseSizeBeforeDelete = integrationTestResultRepository.findAll().size();

        // Get the integrationTestResult
        restIntegrationTestResultMockMvc.perform(delete("/api/integration-test-results/{id}", integrationTestResult.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<IntegrationTestResult> integrationTestResults = integrationTestResultRepository.findAll();
        assertThat(integrationTestResults).hasSize(databaseSizeBeforeDelete - 1);
    }
}
