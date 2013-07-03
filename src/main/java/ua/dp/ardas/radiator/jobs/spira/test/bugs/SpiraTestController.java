package ua.dp.ardas.radiator.jobs.spira.test.bugs;

import org.springframework.beans.factory.annotation.Autowired;

import ua.dp.ardas.radiator.dao.SpiraTestDAO;

public class SpiraTestController {

	@Autowired
	private SpiraTestClient spiraTestClient;
	@Autowired
	private SpiraTestDAO dao;
	
	public void execute() {
		dao.insert(spiraTestClient.loadBugCount());
	}
}
