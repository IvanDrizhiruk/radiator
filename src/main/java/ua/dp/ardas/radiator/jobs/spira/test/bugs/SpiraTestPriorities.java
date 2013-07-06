package ua.dp.ardas.radiator.jobs.spira.test.bugs;

import org.apache.commons.lang.StringUtils;


public enum SpiraTestPriorities {
	HIGH{
		@Override
		public void increment(SpiraTestStatistic spiraTestStatistic) {
			spiraTestStatistic.high++;
		}
	},
	MEDIUM{
		@Override
		public void increment(SpiraTestStatistic spiraTestStatistic) {
			spiraTestStatistic.medium++;
		}
	},
	LOW{
		@Override
		public void increment(SpiraTestStatistic spiraTestStatistic) {
			spiraTestStatistic.low++;
		}
	};

	public static void increment(String priority, SpiraTestStatistic spiraTestStatistic) {
		SpiraTestPriorities spiraTestPriority = stringToSpiraTestPriorities(priority);
		
		if (null != spiraTestPriority) {
			spiraTestPriority.increment(spiraTestStatistic);
		}
	}

	private static SpiraTestPriorities stringToSpiraTestPriorities(String priority) {
		for (SpiraTestPriorities enumPriority : values()) {
			if (StringUtils.containsIgnoreCase(priority, enumPriority.toString())) {
				return enumPriority;
			}
		}
		
		return null;
	}

	public abstract void increment(SpiraTestStatistic spiraTestStatistic);
}
