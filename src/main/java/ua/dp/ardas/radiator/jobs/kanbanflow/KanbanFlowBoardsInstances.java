package ua.dp.ardas.radiator.jobs.kanbanflow;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class KanbanFlowBoardsInstances {
    private static Logger LOG = Logger.getLogger(KanbanFlowBoardsInstances.class.getName());

//    @Autowired
//    protected AppConfig config;
    @Value("${kanbanflow.board.instances}")
    protected String instancesString;

//    private List<KanbanFlowBoardConfig> instances;
//
//    @PostConstruct
//    public List<KanbanFlowBoardConfig> getBoardsInstances() {
//        if (null == instances) {
//            return init(instancesString);
//        }
//        return instances;
//    }
//
//    private List<KanbanFlowBoardConfig> init(String instancesString) {
//        List<KanbanFlowBoardConfig> instances = Lists.newArrayList();
//
//        for (String instansName : splitToInstances(instancesString)) {
//            instances.add(new KanbanFlowBoardConfig(instansName,
//                    getConfigUrl(instansName),
//                    getConfigToken(instansName),
//                    getColumnNumbers(instansName)));
//        }
//
//        return instances;
//    }
//
//    private Iterable<String> splitToInstances(String instancesString) {
//        return Splitter.on(',')
//                .omitEmptyStrings()
//                .trimResults()
//                .split(defaultString(instancesString));
//    }
//
//    private String getConfigUrl(String instansesNames) {
//        return config.stringProperty(format("kanbanflow.board.%s.url", instansesNames));
//    }
//
//    private String getConfigToken(String instansesNames) {
//        return config.stringProperty(format("kanbanflow.board.%s.token", instansesNames));
//    }
//
//    private Set<Integer> getColumnNumbers(String instansesNames) {
//        String value = config.stringProperty(format("kanbanflow.board.%s.column.numbers", instansesNames));
//
//        Iterable<String> splitedValue = Splitter.on(',').trimResults().omitEmptyStrings().split(value);
//
//        Set<Integer> res = Sets.newHashSet();
//
//        for (String columnStringIndex : splitedValue) {
//            Integer columnIndexndex = TypeUtils.toIntegerOrNull(columnStringIndex);
//            if (null != columnIndexndex) {
//                res.add(columnIndexndex);
//            }
//        }
//
//        return res;
//    }
}
