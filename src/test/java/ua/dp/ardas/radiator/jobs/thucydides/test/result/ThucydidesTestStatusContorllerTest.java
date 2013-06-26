package ua.dp.ardas.radiator.jobs.thucydides.test.result;

import static org.fest.assertions.Assertions.assertThat;

import org.junit.Test;

public class ThucydidesTestStatusContorllerTest {

	@Test
	public void shuldExecuteStatistickFromHtml() {
		//given
		String report = "            <h2>Test Results: All Tests</h2>\r\n" +
				"            <table class='overview'>\r\n" +
				"                <tr>\r\n" +
				"                    <td width=\"375px\" valign=\"top\">\r\n" +
				"                        <div class=\"test-count-summary\">\r\n" +
				"                            <span class=\"test-count-title\">766 test scenarios :</span>\r\n" +
				"\r\n" +
				"\r\n" +
				"                            <span class=\"test-count\">\r\n" +
				"                                    717\r\n" +
				"                                        <a href=\"01f5288b80adbd3af52a8ed68a4a616d3164f750229f80da1ef344382d948835.html\">passed</a>\r\n" +
				"                                    ,\r\n" +
				"                                </span>\r\n" +
				"                                <span class=\"test-count\">\r\n" +
				"                                30\r\n" +
				"                                    <a href=\"bde4fca42ca8baa0fe2eac76a241c84327dfe4ca903a1daa7cae83a1035d4745.html\">pending</a>\r\n" +
				"                                ,\r\n" +
				"                                </span>\r\n" +
				"                                <span class=\"test-count\">\r\n" +
				"                                    5\r\n" +
				"                                        <a href=\"da5b0c51b332adf0ef993a9f086d6a1b51a3d800248ec6ae281212092f35bd37.html\">failed</a>\r\n" +
				"                                    ,\r\n" +
				"                                </span>\r\n" +
				"                                <span class=\"test-count\">\r\n" +
				"                                    14\r\n" +
				"                                        <a href=\"c8a926210aeff57f8aa7ea799262d4b730915a909cd5ecd6a705b1fa13bc7aa8.html\">with errors</a>\r\n" +
				"                                    \r\n" +
				"                                </span>\r\n" +
				"                        </div>\r\n" +
				"\r\n" +
				"                        <div id=\"test-results-tabs\">\r\n" +
				"                            <ul>\r\n" +
				"                                <li><a href=\"#test-results-tabs-1\">Test Count</a></li>\r\n" +
				"                                <li><a href=\"#test-results-tabs-2\">Weighted Tests</a></li>\r\n" +
				"                            </ul>\r\n" +
				"                            <div id=\"test-results-tabs-1\">\r\n" +
				"                                <table>\r\n" +
				"                                    <tr>\r\n" +
				"                                        <td colspan=\"2\">\r\n" +
				"                                            <span class=\"caption\">Total number of tests that pass, fail, or are pending.</span>\r\n" +
				"                                        </td>\r\n" +
				"                                    </tr>\r\n" +
				"                                    <tr>\r\n" +
				"                                        <td>\r\n" +
				"                                            <div id=\"test_results_pie_chart\"></div>\r\n" +
				"                                        </td>\r\n" +
				"                                        <td class=\"related-tags-section\">\r\n" +
				"                                            <div>\r\n" +
				"<h4>All available tags</h4>";
		ThucydidesTestStatusContorllerImpl contorller = new ThucydidesTestStatusContorllerImpl();
		ThucydidesTestStatistic expected;
		//when
		ThucydidesTestStatistic actual = contorller.extractStatistic(report);
		//then
		assertThat(actual.passed).isEqualTo(717);
		assertThat(actual.pending).isEqualTo(30);
		assertThat(actual.failed).isEqualTo(5);
		assertThat(actual.errors).isEqualTo(14);
	}
	
	
	private class ThucydidesTestStatusContorllerImpl extends ThucydidesTestStatusContorller {
		@Override
		public ThucydidesTestStatistic extractStatistic(String report) {
			return super.extractStatistic(report);
		}
	}

}
