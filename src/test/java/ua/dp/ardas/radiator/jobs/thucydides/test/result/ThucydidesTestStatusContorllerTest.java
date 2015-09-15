package ua.dp.ardas.radiator.jobs.thucydides.test.result;

import org.junit.Test;
import ua.dp.ardas.radiator.dto.thucydides.test.ThucydidesTestStatistic;

import static org.fest.assertions.Assertions.assertThat;
import static ua.dp.ardas.radiator.utils.ResourcesUtils.resourceAsString;

public class ThucydidesTestStatusContorllerTest {

	@Test
	public void shuldExecuteStatistickFromHtmlWithErrorEndFaildTests() {
		//given
		String report = resourceAsString("thucydidesReports/withErrorsAndFailds.html");
		
		ThucydidesTestStatusContorllerImpl contorller = new ThucydidesTestStatusContorllerImpl();
		ThucydidesTestStatistic expected;
		//when
		ThucydidesTestStatistic actual = contorller.extractStatistic(report);
		//then
		assertThat(actual.passed).isEqualTo(1005);
		assertThat(actual.pending).isEqualTo(30);
		assertThat(actual.failed).isEqualTo(5);
		assertThat(actual.errors).isEqualTo(14);
	}
	
	@Test
	public void shuldExecuteStatistickFromHtmlWithoutFaildTests() {
		//given
		String report = resourceAsString("thucydidesReports/withoutFailds.html");
		
		ThucydidesTestStatusContorllerImpl contorller = new ThucydidesTestStatusContorllerImpl();
		ThucydidesTestStatistic expected;
		//when
		ThucydidesTestStatistic actual = contorller.extractStatistic(report);
		//then
		assertThat(actual.passed).isEqualTo(779);
		assertThat(actual.pending).isEqualTo(20);
		assertThat(actual.failed).isEqualTo(0);
		assertThat(actual.errors).isEqualTo(0);
	}

	private class ThucydidesTestStatusContorllerImpl extends ThucydidesTestStatisticContorller {
		@Override
		public ThucydidesTestStatistic extractStatistic(String report) {
			return super.extractStatistic(report);
		}
	}

}
