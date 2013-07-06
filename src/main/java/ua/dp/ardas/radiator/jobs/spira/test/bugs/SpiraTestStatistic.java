package ua.dp.ardas.radiator.jobs.spira.test.bugs;

import ua.dp.ardas.radiator.utils.JsonUtils;

public class SpiraTestStatistic {

	public int high;
	public int medium;
	public int low;
	
	public int changeRequest;
					
	@Override
	public String toString() {
		return String.format("SpiraTestStatistic %s", JsonUtils.toJSON(this));
	}
}
