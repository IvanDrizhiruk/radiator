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
import ua.dp.ardas.radiator.domain.KanbanFlowColumn;
import ua.dp.ardas.radiator.repository.KanbanFlowColumnRepository;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the KanbanFlowColumnResource REST controller.
 *
 * @see KanbanFlowColumnResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = RadiatorApp.class)
@WebAppConfiguration
@IntegrationTest
public class KanbanFlowColumnResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";

    private static final Integer DEFAULT_INDEX_NUMBER = 1;
    private static final Integer UPDATED_INDEX_NUMBER = 2;

    @Inject
    private KanbanFlowColumnRepository kanbanFlowColumnRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restKanbanFlowColumnMockMvc;

    private KanbanFlowColumn kanbanFlowColumn;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        KanbanFlowColumnResource kanbanFlowColumnResource = new KanbanFlowColumnResource();
        ReflectionTestUtils.setField(kanbanFlowColumnResource, "kanbanFlowColumnRepository", kanbanFlowColumnRepository);
        this.restKanbanFlowColumnMockMvc = MockMvcBuilders.standaloneSetup(kanbanFlowColumnResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        kanbanFlowColumn = new KanbanFlowColumn();
        kanbanFlowColumn.setName(DEFAULT_NAME);
        kanbanFlowColumn.setIndexNumber(DEFAULT_INDEX_NUMBER);
    }

    @Test
    @Transactional
    public void createKanbanFlowColumn() throws Exception {
        int databaseSizeBeforeCreate = kanbanFlowColumnRepository.findAll().size();

        // Create the KanbanFlowColumn

        restKanbanFlowColumnMockMvc.perform(post("/api/kanban-flow-columns")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(kanbanFlowColumn)))
                .andExpect(status().isCreated());

        // Validate the KanbanFlowColumn in the database
        List<KanbanFlowColumn> kanbanFlowColumns = kanbanFlowColumnRepository.findAll();
        assertThat(kanbanFlowColumns).hasSize(databaseSizeBeforeCreate + 1);
        KanbanFlowColumn testKanbanFlowColumn = kanbanFlowColumns.get(kanbanFlowColumns.size() - 1);
        assertThat(testKanbanFlowColumn.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testKanbanFlowColumn.getIndexNumber()).isEqualTo(DEFAULT_INDEX_NUMBER);
    }

    @Test
    @Transactional
    public void getAllKanbanFlowColumns() throws Exception {
        // Initialize the database
        kanbanFlowColumnRepository.saveAndFlush(kanbanFlowColumn);

        // Get all the kanbanFlowColumns
        restKanbanFlowColumnMockMvc.perform(get("/api/kanban-flow-columns?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(kanbanFlowColumn.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].indexNumber").value(hasItem(DEFAULT_INDEX_NUMBER)));
    }

    @Test
    @Transactional
    public void getKanbanFlowColumn() throws Exception {
        // Initialize the database
        kanbanFlowColumnRepository.saveAndFlush(kanbanFlowColumn);

        // Get the kanbanFlowColumn
        restKanbanFlowColumnMockMvc.perform(get("/api/kanban-flow-columns/{id}", kanbanFlowColumn.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(kanbanFlowColumn.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.indexNumber").value(DEFAULT_INDEX_NUMBER));
    }

    @Test
    @Transactional
    public void getNonExistingKanbanFlowColumn() throws Exception {
        // Get the kanbanFlowColumn
        restKanbanFlowColumnMockMvc.perform(get("/api/kanban-flow-columns/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateKanbanFlowColumn() throws Exception {
        // Initialize the database
        kanbanFlowColumnRepository.saveAndFlush(kanbanFlowColumn);
        int databaseSizeBeforeUpdate = kanbanFlowColumnRepository.findAll().size();

        // Update the kanbanFlowColumn
        KanbanFlowColumn updatedKanbanFlowColumn = new KanbanFlowColumn();
        updatedKanbanFlowColumn.setId(kanbanFlowColumn.getId());
        updatedKanbanFlowColumn.setName(UPDATED_NAME);
        updatedKanbanFlowColumn.setIndexNumber(UPDATED_INDEX_NUMBER);

        restKanbanFlowColumnMockMvc.perform(put("/api/kanban-flow-columns")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedKanbanFlowColumn)))
                .andExpect(status().isOk());

        // Validate the KanbanFlowColumn in the database
        List<KanbanFlowColumn> kanbanFlowColumns = kanbanFlowColumnRepository.findAll();
        assertThat(kanbanFlowColumns).hasSize(databaseSizeBeforeUpdate);
        KanbanFlowColumn testKanbanFlowColumn = kanbanFlowColumns.get(kanbanFlowColumns.size() - 1);
        assertThat(testKanbanFlowColumn.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testKanbanFlowColumn.getIndexNumber()).isEqualTo(UPDATED_INDEX_NUMBER);
    }

    @Test
    @Transactional
    public void deleteKanbanFlowColumn() throws Exception {
        // Initialize the database
        kanbanFlowColumnRepository.saveAndFlush(kanbanFlowColumn);
        int databaseSizeBeforeDelete = kanbanFlowColumnRepository.findAll().size();

        // Get the kanbanFlowColumn
        restKanbanFlowColumnMockMvc.perform(delete("/api/kanban-flow-columns/{id}", kanbanFlowColumn.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<KanbanFlowColumn> kanbanFlowColumns = kanbanFlowColumnRepository.findAll();
        assertThat(kanbanFlowColumns).hasSize(databaseSizeBeforeDelete - 1);
    }
}
