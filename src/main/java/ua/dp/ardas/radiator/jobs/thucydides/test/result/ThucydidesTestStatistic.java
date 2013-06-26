package ua.dp.ardas.radiator.jobs.thucydides.test.result;

import ua.dp.ardas.radiator.utils.JsonUtils;

public class ThucydidesTestStatistic {

	public Integer passed;
	public Integer pending;
	public Integer failed;
	public Integer errors;

	@Override
	public String toString() {
		return String.format("ThucydidesTestStatistic %s", JsonUtils.toJSON(this));
	}
}
