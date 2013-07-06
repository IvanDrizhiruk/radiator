package ua.dp.ardas.radiator.jobs.spira.test.bugs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import ua.dp.ardas.radiator.dao.SpiraTestStatisticDAO;

@Component
public class SpiraTestStatisticController {

	@Autowired
	private SpiraTestClient spiraTestClient;
	@Autowired
	private SpiraTestStatisticDAO dao;
	@Value("${spira.test.project.id}")
	private Integer projectId;
	
	public void execute() {
		dao.insert(spiraTestClient.loadBugCount(projectId));
	}
}
