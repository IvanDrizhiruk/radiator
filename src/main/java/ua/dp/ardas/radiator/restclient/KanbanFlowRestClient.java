package ua.dp.ardas.radiator.restclient;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import ua.dp.ardas.radiator.config.RadiatorProperties;
import ua.dp.ardas.radiator.dto.report.kanbanflow.TasksSet;
import ua.dp.ardas.radiator.restclient.auth.BasicAutorisationRestClient;
import ua.dp.ardas.radiator.restclient.auth.TokenAuthTokenGenerator;

import javax.inject.Inject;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class KanbanFlowRestClient {
    private static Logger LOG = Logger.getLogger(KanbanFlowRestClient.class.getName());

    @Inject
    private RadiatorProperties properties;

    private Map<String, RestTemplate> restClients = new HashMap<>();



    public RestTemplate newRestClient(String token) {
        TokenAuthTokenGenerator generator = new TokenAuthTokenGenerator(token);
        return new BasicAutorisationRestClient(generator);
    }

    private RestTemplate getRestClient(String token) {
        if (!restClients.containsKey(token)) {
            restClients.put(token, newRestClient(token));
        }

        return restClients.get(token);
    }


    public List<TasksSet> loadTaskSets(String url, String token) {
        try {
            if (LOG.isDebugEnabled()) {
                LOG.debug(String.format("Try load kanbanflow statistic from url: ", url));
            }

            RestTemplate restTemplate = getRestClient(token);

            TasksSet[] responseResult = restTemplate.getForObject(url + "tasks", TasksSet[].class);
            List<TasksSet> tasksSets = Arrays.asList(responseResult);

            loadUnloadedTasks(tasksSets, restTemplate, url);

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
