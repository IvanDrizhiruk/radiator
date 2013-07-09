package ua.dp.ardas.radiator.jobs.spira.test.bugs;

import static ua.dp.ardas.radiator.utils.DataTimeUtils.calculateMondayDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import ua.dp.ardas.radiator.dao.impl.SpiraTestStatisticDAOInMemory;

@Component
public class SpiraTestOnStartWeekStatisticController {

	@Autowired
	private SpiraTestClient spiraTestClient;
	@Autowired
	private SpiraTestStatisticDAOInMemory dao;
	@Value("${spira.test.project.id}")
	private Integer projectId;
	
	public void execute() {
		String monday = calculateMondayDate();
		
		SpiraTestStatistic statistic = dao.findByDate(monday);
		
		if (null == statistic) {
			dao.addByDate(monday, spiraTestClient.loadBugCount(projectId));
		}
	}
}
