package ua.dp.ardas.radiator.web.rest;

import ua.dp.ardas.radiator.RadiatorApp;
import ua.dp.ardas.radiator.domain.KanbanFlowBoard;
import ua.dp.ardas.radiator.repository.KanbanFlowBoardRepository;

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
 * Test class for the KanbanFlowBoardResource REST controller.
 *
 * @see KanbanFlowBoardResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = RadiatorApp.class)
@WebAppConfiguration
@IntegrationTest
public class KanbanFlowBoardResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";

    @Inject
    private KanbanFlowBoardRepository kanbanFlowBoardRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restKanbanFlowBoardMockMvc;

    private KanbanFlowBoard kanbanFlowBoard;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        KanbanFlowBoardResource kanbanFlowBoardResource = new KanbanFlowBoardResource();
        ReflectionTestUtils.setField(kanbanFlowBoardResource, "kanbanFlowBoardRepository", kanbanFlowBoardRepository);
        this.restKanbanFlowBoardMockMvc = MockMvcBuilders.standaloneSetup(kanbanFlowBoardResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        kanbanFlowBoard = new KanbanFlowBoard();
        kanbanFlowBoard.setName(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createKanbanFlowBoard() throws Exception {
        int databaseSizeBeforeCreate = kanbanFlowBoardRepository.findAll().size();

        // Create the KanbanFlowBoard

        restKanbanFlowBoardMockMvc.perform(post("/api/kanban-flow-boards")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(kanbanFlowBoard)))
                .andExpect(status().isCreated());

        // Validate the KanbanFlowBoard in the database
        List<KanbanFlowBoard> kanbanFlowBoards = kanbanFlowBoardRepository.findAll();
        assertThat(kanbanFlowBoards).hasSize(databaseSizeBeforeCreate + 1);
        KanbanFlowBoard testKanbanFlowBoard = kanbanFlowBoards.get(kanbanFlowBoards.size() - 1);
        assertThat(testKanbanFlowBoard.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void getAllKanbanFlowBoards() throws Exception {
        // Initialize the database
        kanbanFlowBoardRepository.saveAndFlush(kanbanFlowBoard);

        // Get all the kanbanFlowBoards
        restKanbanFlowBoardMockMvc.perform(get("/api/kanban-flow-boards?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(kanbanFlowBoard.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }

    @Test
    @Transactional
    public void getKanbanFlowBoard() throws Exception {
        // Initialize the database
        kanbanFlowBoardRepository.saveAndFlush(kanbanFlowBoard);

        // Get the kanbanFlowBoard
        restKanbanFlowBoardMockMvc.perform(get("/api/kanban-flow-boards/{id}", kanbanFlowBoard.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(kanbanFlowBoard.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingKanbanFlowBoard() throws Exception {
        // Get the kanbanFlowBoard
        restKanbanFlowBoardMockMvc.perform(get("/api/kanban-flow-boards/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateKanbanFlowBoard() throws Exception {
        // Initialize the database
        kanbanFlowBoardRepository.saveAndFlush(kanbanFlowBoard);
        int databaseSizeBeforeUpdate = kanbanFlowBoardRepository.findAll().size();

        // Update the kanbanFlowBoard
        KanbanFlowBoard updatedKanbanFlowBoard = new KanbanFlowBoard();
        updatedKanbanFlowBoard.setId(kanbanFlowBoard.getId());
        updatedKanbanFlowBoard.setName(UPDATED_NAME);

        restKanbanFlowBoardMockMvc.perform(put("/api/kanban-flow-boards")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedKanbanFlowBoard)))
                .andExpect(status().isOk());

        // Validate the KanbanFlowBoard in the database
        List<KanbanFlowBoard> kanbanFlowBoards = kanbanFlowBoardRepository.findAll();
        assertThat(kanbanFlowBoards).hasSize(databaseSizeBeforeUpdate);
        KanbanFlowBoard testKanbanFlowBoard = kanbanFlowBoards.get(kanbanFlowBoards.size() - 1);
        assertThat(testKanbanFlowBoard.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void deleteKanbanFlowBoard() throws Exception {
        // Initialize the database
        kanbanFlowBoardRepository.saveAndFlush(kanbanFlowBoard);
        int databaseSizeBeforeDelete = kanbanFlowBoardRepository.findAll().size();

        // Get the kanbanFlowBoard
        restKanbanFlowBoardMockMvc.perform(delete("/api/kanban-flow-boards/{id}", kanbanFlowBoard.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<KanbanFlowBoard> kanbanFlowBoards = kanbanFlowBoardRepository.findAll();
        assertThat(kanbanFlowBoards).hasSize(databaseSizeBeforeDelete - 1);
    }
}
