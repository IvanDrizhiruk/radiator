package ua.dp.ardas.radiator.web.rest;

import ua.dp.ardas.radiator.RadiatorApp;
import ua.dp.ardas.radiator.domain.KanbanFlowCellInfo;
import ua.dp.ardas.radiator.repository.KanbanFlowCellInfoRepository;

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
 * Test class for the KanbanFlowCellInfoResource REST controller.
 *
 * @see KanbanFlowCellInfoResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = RadiatorApp.class)
@WebAppConfiguration
@IntegrationTest
public class KanbanFlowCellInfoResourceIntTest {


    private static final Long DEFAULT_TOTAL_SECONDS_ESTIMATED = 1L;
    private static final Long UPDATED_TOTAL_SECONDS_ESTIMATED = 2L;

    private static final Long DEFAULT_TOTAL_SECONDS_SPENT = 1L;
    private static final Long UPDATED_TOTAL_SECONDS_SPENT = 2L;

    @Inject
    private KanbanFlowCellInfoRepository kanbanFlowCellInfoRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restKanbanFlowCellInfoMockMvc;

    private KanbanFlowCellInfo kanbanFlowCellInfo;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        KanbanFlowCellInfoResource kanbanFlowCellInfoResource = new KanbanFlowCellInfoResource();
        ReflectionTestUtils.setField(kanbanFlowCellInfoResource, "kanbanFlowCellInfoRepository", kanbanFlowCellInfoRepository);
        this.restKanbanFlowCellInfoMockMvc = MockMvcBuilders.standaloneSetup(kanbanFlowCellInfoResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        kanbanFlowCellInfo = new KanbanFlowCellInfo();
        kanbanFlowCellInfo.setTotalSecondsEstimated(DEFAULT_TOTAL_SECONDS_ESTIMATED);
        kanbanFlowCellInfo.setTotalSecondsSpent(DEFAULT_TOTAL_SECONDS_SPENT);
    }

    @Test
    @Transactional
    public void createKanbanFlowCellInfo() throws Exception {
        int databaseSizeBeforeCreate = kanbanFlowCellInfoRepository.findAll().size();

        // Create the KanbanFlowCellInfo

        restKanbanFlowCellInfoMockMvc.perform(post("/api/kanban-flow-cell-infos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(kanbanFlowCellInfo)))
                .andExpect(status().isCreated());

        // Validate the KanbanFlowCellInfo in the database
        List<KanbanFlowCellInfo> kanbanFlowCellInfos = kanbanFlowCellInfoRepository.findAll();
        assertThat(kanbanFlowCellInfos).hasSize(databaseSizeBeforeCreate + 1);
        KanbanFlowCellInfo testKanbanFlowCellInfo = kanbanFlowCellInfos.get(kanbanFlowCellInfos.size() - 1);
        assertThat(testKanbanFlowCellInfo.getTotalSecondsEstimated()).isEqualTo(DEFAULT_TOTAL_SECONDS_ESTIMATED);
        assertThat(testKanbanFlowCellInfo.getTotalSecondsSpent()).isEqualTo(DEFAULT_TOTAL_SECONDS_SPENT);
    }

    @Test
    @Transactional
    public void getAllKanbanFlowCellInfos() throws Exception {
        // Initialize the database
        kanbanFlowCellInfoRepository.saveAndFlush(kanbanFlowCellInfo);

        // Get all the kanbanFlowCellInfos
        restKanbanFlowCellInfoMockMvc.perform(get("/api/kanban-flow-cell-infos?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(kanbanFlowCellInfo.getId().intValue())))
                .andExpect(jsonPath("$.[*].totalSecondsEstimated").value(hasItem(DEFAULT_TOTAL_SECONDS_ESTIMATED.intValue())))
                .andExpect(jsonPath("$.[*].totalSecondsSpent").value(hasItem(DEFAULT_TOTAL_SECONDS_SPENT.intValue())));
    }

    @Test
    @Transactional
    public void getKanbanFlowCellInfo() throws Exception {
        // Initialize the database
        kanbanFlowCellInfoRepository.saveAndFlush(kanbanFlowCellInfo);

        // Get the kanbanFlowCellInfo
        restKanbanFlowCellInfoMockMvc.perform(get("/api/kanban-flow-cell-infos/{id}", kanbanFlowCellInfo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(kanbanFlowCellInfo.getId().intValue()))
            .andExpect(jsonPath("$.totalSecondsEstimated").value(DEFAULT_TOTAL_SECONDS_ESTIMATED.intValue()))
            .andExpect(jsonPath("$.totalSecondsSpent").value(DEFAULT_TOTAL_SECONDS_SPENT.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingKanbanFlowCellInfo() throws Exception {
        // Get the kanbanFlowCellInfo
        restKanbanFlowCellInfoMockMvc.perform(get("/api/kanban-flow-cell-infos/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateKanbanFlowCellInfo() throws Exception {
        // Initialize the database
        kanbanFlowCellInfoRepository.saveAndFlush(kanbanFlowCellInfo);
        int databaseSizeBeforeUpdate = kanbanFlowCellInfoRepository.findAll().size();

        // Update the kanbanFlowCellInfo
        KanbanFlowCellInfo updatedKanbanFlowCellInfo = new KanbanFlowCellInfo();
        updatedKanbanFlowCellInfo.setId(kanbanFlowCellInfo.getId());
        updatedKanbanFlowCellInfo.setTotalSecondsEstimated(UPDATED_TOTAL_SECONDS_ESTIMATED);
        updatedKanbanFlowCellInfo.setTotalSecondsSpent(UPDATED_TOTAL_SECONDS_SPENT);

        restKanbanFlowCellInfoMockMvc.perform(put("/api/kanban-flow-cell-infos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedKanbanFlowCellInfo)))
                .andExpect(status().isOk());

        // Validate the KanbanFlowCellInfo in the database
        List<KanbanFlowCellInfo> kanbanFlowCellInfos = kanbanFlowCellInfoRepository.findAll();
        assertThat(kanbanFlowCellInfos).hasSize(databaseSizeBeforeUpdate);
        KanbanFlowCellInfo testKanbanFlowCellInfo = kanbanFlowCellInfos.get(kanbanFlowCellInfos.size() - 1);
        assertThat(testKanbanFlowCellInfo.getTotalSecondsEstimated()).isEqualTo(UPDATED_TOTAL_SECONDS_ESTIMATED);
        assertThat(testKanbanFlowCellInfo.getTotalSecondsSpent()).isEqualTo(UPDATED_TOTAL_SECONDS_SPENT);
    }

    @Test
    @Transactional
    public void deleteKanbanFlowCellInfo() throws Exception {
        // Initialize the database
        kanbanFlowCellInfoRepository.saveAndFlush(kanbanFlowCellInfo);
        int databaseSizeBeforeDelete = kanbanFlowCellInfoRepository.findAll().size();

        // Get the kanbanFlowCellInfo
        restKanbanFlowCellInfoMockMvc.perform(delete("/api/kanban-flow-cell-infos/{id}", kanbanFlowCellInfo.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<KanbanFlowCellInfo> kanbanFlowCellInfos = kanbanFlowCellInfoRepository.findAll();
        assertThat(kanbanFlowCellInfos).hasSize(databaseSizeBeforeDelete - 1);
    }
}
