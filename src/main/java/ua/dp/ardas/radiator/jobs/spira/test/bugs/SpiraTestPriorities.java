package ua.dp.ardas.radiator.jobs.spira.test.bugs;

import org.apache.commons.lang.StringUtils;


public enum SpiraTestPriorities {
    CRITICAL{
        @Override
        public void incrementTotalNumber(SpiraTestStatistic spiraTestStatistic) {
            spiraTestStatistic.critical++;
        }

        @Override
        public void incrementWithOwner(SpiraTestStatistic spiraTestStatistic) {
            spiraTestStatistic.criticalWithOwner++;
        }
    },
    HIGH{
        @Override
        public void incrementTotalNumber(SpiraTestStatistic spiraTestStatistic) {
			spiraTestStatistic.high++;
		}
        @Override
        public void incrementWithOwner(SpiraTestStatistic spiraTestStatistic) {
            spiraTestStatistic.highWithOwner++;
        }
	},
	MEDIUM{
		@Override
		public void incrementTotalNumber(SpiraTestStatistic spiraTestStatistic) {
			spiraTestStatistic.medium++;
		}
        @Override
        public void incrementWithOwner(SpiraTestStatistic spiraTestStatistic) {
            spiraTestStatistic.mediumWithOwner++;
        }
	},
	LOW{
		@Override
		public void incrementTotalNumber(SpiraTestStatistic spiraTestStatistic) {
			spiraTestStatistic.low++;
		}
        @Override
        public void incrementWithOwner(SpiraTestStatistic spiraTestStatistic) {
            spiraTestStatistic.lowWithOwner++;
        }
	};

	public static void increment(String priority, boolean isHasOwner, SpiraTestStatistic spiraTestStatistic) {
		SpiraTestPriorities spiraTestPriority = stringToSpiraTestPriorities(priority);
		
		if (null != spiraTestPriority) {
			spiraTestPriority.incrementTotalNumber(spiraTestStatistic);
            if(isHasOwner) {
                spiraTestPriority.incrementWithOwner(spiraTestStatistic);
            }
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

	public abstract void incrementTotalNumber(SpiraTestStatistic spiraTestStatistic);
    public abstract void incrementWithOwner(SpiraTestStatistic spiraTestStatistic);
}
