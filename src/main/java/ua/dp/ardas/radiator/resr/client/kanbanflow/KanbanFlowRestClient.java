package ua.dp.ardas.radiator.resr.client.kanbanflow;

import com.google.common.collect.Maps;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.client.CommonsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import ua.dp.ardas.radiator.dto.kanbanflow.TasksSet;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Component
public class KanbanFlowRestClient {
    private static Logger LOG = Logger.getLogger(KanbanFlowRestClient.class.getName());

    @Autowired
    private CommonsClientHttpRequestFactory httpClientFactory;
    private Map<String, RestTemplate> templates = Maps.newHashMap();

    public RestTemplate getTemplate(String token) {
        if (!templates.containsKey(token)) {
            templates.put(token, newRestTemplate(token));
        }

        return templates.get(token);
    }

    private RestTemplate newRestTemplate(String token) {
        RestTemplate template = new RestTemplate(httpClientFactory);
        template.setInterceptors(Collections.singletonList(new KanbanFlowAuthInterceptor(token)));

        return template;
    }

    public List<TasksSet> loadTaskSets(String url, String token) {
        try {
            if (LOG.isDebugEnabled()) {
                LOG.debug(String.format("Try load kanbanflow statistic from url: ", url));
            }

            RestTemplate restTtemplate = getTemplate(token);

            TasksSet[] responseResult = restTtemplate.getForObject(url + "tasks", TasksSet[].class);
            List<TasksSet> tasksSets = Arrays.asList(responseResult);

            loadUnloadedTasks(tasksSets, restTtemplate, url);

            return tasksSets;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    private void loadUnloadedTasks(List<TasksSet> tasksSets, RestTemplate restTtemplate, String url) {
      for (TasksSet taskSet : tasksSets) {
          if (taskSet.tasksLimited) {
              loadAllUnloadedTasks(taskSet, restTtemplate, url);
          }
      }
    }

    private void loadAllUnloadedTasks(TasksSet taskSet, RestTemplate restTemplate, String url) {
        TasksSet currentTaskSet = taskSet;
        while (currentTaskSet.tasksLimited) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("Load tsks after" + taskSet.nextTaskId);
            }

            String requestUrl = url + "tasks" +
                    "?" +
                    "columnId=" + taskSet.columnId + "&" +
                    "swimlaneId=" + taskSet.swimlaneId + "&" +
                    "startTaskId=" + taskSet.nextTaskId + "&" +
                    "limit=100";
            TasksSet[] responseResult = restTemplate.getForObject(requestUrl, TasksSet[].class);
            currentTaskSet = responseResult[0];

            taskSet.tasks.addAll(currentTaskSet.tasks);
        }
    }
}
