package ua.dp.ardas.radiator.jobs.thucydides.test.result;

import ua.dp.ardas.radiator.utils.JsonUtils;

public class ThucydidesTestStatistic {

	public int passed;
	public int pending;
	public int failed;
	public int errors;

	@Override
	public String toString() {
		return String.format("ThucydidesTestStatistic %s", JsonUtils.toJSON(this));
	}
}
