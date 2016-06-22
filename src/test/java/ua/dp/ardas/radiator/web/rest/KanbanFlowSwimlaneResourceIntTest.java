package ua.dp.ardas.radiator.web.rest;

import ua.dp.ardas.radiator.RadiatorApp;
import ua.dp.ardas.radiator.domain.KanbanFlowSwimlane;
import ua.dp.ardas.radiator.repository.KanbanFlowSwimlaneRepository;

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
 * Test class for the KanbanFlowSwimlaneResource REST controller.
 *
 * @see KanbanFlowSwimlaneResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = RadiatorApp.class)
@WebAppConfiguration
@IntegrationTest
public class KanbanFlowSwimlaneResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";

    private static final Integer DEFAULT_INDEX_NUMBER = 1;
    private static final Integer UPDATED_INDEX_NUMBER = 2;

    @Inject
    private KanbanFlowSwimlaneRepository kanbanFlowSwimlaneRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restKanbanFlowSwimlaneMockMvc;

    private KanbanFlowSwimlane kanbanFlowSwimlane;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        KanbanFlowSwimlaneResource kanbanFlowSwimlaneResource = new KanbanFlowSwimlaneResource();
        ReflectionTestUtils.setField(kanbanFlowSwimlaneResource, "kanbanFlowSwimlaneRepository", kanbanFlowSwimlaneRepository);
        this.restKanbanFlowSwimlaneMockMvc = MockMvcBuilders.standaloneSetup(kanbanFlowSwimlaneResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        kanbanFlowSwimlane = new KanbanFlowSwimlane();
        kanbanFlowSwimlane.setName(DEFAULT_NAME);
        kanbanFlowSwimlane.setIndexNumber(DEFAULT_INDEX_NUMBER);
    }

    @Test
    @Transactional
    public void createKanbanFlowSwimlane() throws Exception {
        int databaseSizeBeforeCreate = kanbanFlowSwimlaneRepository.findAll().size();

        // Create the KanbanFlowSwimlane

        restKanbanFlowSwimlaneMockMvc.perform(post("/api/kanban-flow-swimlanes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(kanbanFlowSwimlane)))
                .andExpect(status().isCreated());

        // Validate the KanbanFlowSwimlane in the database
        List<KanbanFlowSwimlane> kanbanFlowSwimlanes = kanbanFlowSwimlaneRepository.findAll();
        assertThat(kanbanFlowSwimlanes).hasSize(databaseSizeBeforeCreate + 1);
        KanbanFlowSwimlane testKanbanFlowSwimlane = kanbanFlowSwimlanes.get(kanbanFlowSwimlanes.size() - 1);
        assertThat(testKanbanFlowSwimlane.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testKanbanFlowSwimlane.getIndexNumber()).isEqualTo(DEFAULT_INDEX_NUMBER);
    }

    @Test
    @Transactional
    public void getAllKanbanFlowSwimlanes() throws Exception {
        // Initialize the database
        kanbanFlowSwimlaneRepository.saveAndFlush(kanbanFlowSwimlane);

        // Get all the kanbanFlowSwimlanes
        restKanbanFlowSwimlaneMockMvc.perform(get("/api/kanban-flow-swimlanes?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(kanbanFlowSwimlane.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].indexNumber").value(hasItem(DEFAULT_INDEX_NUMBER)));
    }

    @Test
    @Transactional
    public void getKanbanFlowSwimlane() throws Exception {
        // Initialize the database
        kanbanFlowSwimlaneRepository.saveAndFlush(kanbanFlowSwimlane);

        // Get the kanbanFlowSwimlane
        restKanbanFlowSwimlaneMockMvc.perform(get("/api/kanban-flow-swimlanes/{id}", kanbanFlowSwimlane.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(kanbanFlowSwimlane.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.indexNumber").value(DEFAULT_INDEX_NUMBER));
    }

    @Test
    @Transactional
    public void getNonExistingKanbanFlowSwimlane() throws Exception {
        // Get the kanbanFlowSwimlane
        restKanbanFlowSwimlaneMockMvc.perform(get("/api/kanban-flow-swimlanes/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateKanbanFlowSwimlane() throws Exception {
        // Initialize the database
        kanbanFlowSwimlaneRepository.saveAndFlush(kanbanFlowSwimlane);
        int databaseSizeBeforeUpdate = kanbanFlowSwimlaneRepository.findAll().size();

        // Update the kanbanFlowSwimlane
        KanbanFlowSwimlane updatedKanbanFlowSwimlane = new KanbanFlowSwimlane();
        updatedKanbanFlowSwimlane.setId(kanbanFlowSwimlane.getId());
        updatedKanbanFlowSwimlane.setName(UPDATED_NAME);
        updatedKanbanFlowSwimlane.setIndexNumber(UPDATED_INDEX_NUMBER);

        restKanbanFlowSwimlaneMockMvc.perform(put("/api/kanban-flow-swimlanes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedKanbanFlowSwimlane)))
                .andExpect(status().isOk());

        // Validate the KanbanFlowSwimlane in the database
        List<KanbanFlowSwimlane> kanbanFlowSwimlanes = kanbanFlowSwimlaneRepository.findAll();
        assertThat(kanbanFlowSwimlanes).hasSize(databaseSizeBeforeUpdate);
        KanbanFlowSwimlane testKanbanFlowSwimlane = kanbanFlowSwimlanes.get(kanbanFlowSwimlanes.size() - 1);
        assertThat(testKanbanFlowSwimlane.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testKanbanFlowSwimlane.getIndexNumber()).isEqualTo(UPDATED_INDEX_NUMBER);
    }

    @Test
    @Transactional
    public void deleteKanbanFlowSwimlane() throws Exception {
        // Initialize the database
        kanbanFlowSwimlaneRepository.saveAndFlush(kanbanFlowSwimlane);
        int databaseSizeBeforeDelete = kanbanFlowSwimlaneRepository.findAll().size();

        // Get the kanbanFlowSwimlane
        restKanbanFlowSwimlaneMockMvc.perform(delete("/api/kanban-flow-swimlanes/{id}", kanbanFlowSwimlane.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<KanbanFlowSwimlane> kanbanFlowSwimlanes = kanbanFlowSwimlaneRepository.findAll();
        assertThat(kanbanFlowSwimlanes).hasSize(databaseSizeBeforeDelete - 1);
    }
}
