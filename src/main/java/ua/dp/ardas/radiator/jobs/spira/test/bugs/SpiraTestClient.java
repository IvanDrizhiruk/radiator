package ua.dp.ardas.radiator.jobs.spira.test.bugs;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import ua.dp.ardas.tools.sync.common.SyncException;
import ua.dp.ardas.tools.sync.xp.ImportExportSoap_PortType;
import ua.dp.ardas.tools.sync.xp.RemoteIncident;

import java.util.Arrays;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;
import static java.lang.String.format;
import static java.util.Arrays.asList;
import static ua.dp.ardas.radiator.jobs.spira.test.bugs.SpiraTestPriorities.increment;
import static ua.dp.ardas.tools.sync.common.SyncUtil.connectToSpira;
import static ua.dp.ardas.tools.sync.common.SyncUtil.connectToSpiraProject;
import static ua.dp.ardas.tools.sync.util.ObjectsUtil.getSpiraAllOpenIncidents;

@Component
public class SpiraTestClient {
	private static Logger LOG = Logger.getLogger(SpiraTestClient.class.getName());

    private static final String INCIDENT_TYPE_KNOWNISSUE = "known issue";

	public SpiraTestStatistic loadBugCount(Integer projectId) {
		try {
			if (LOG.isInfoEnabled()) {
				LOG.info(format("Try load SpiraTest statistic for project id %d", projectId));
			}
			SpiraTestStatistic statistic = calculateStatistic(loadAllOpenIncidents(projectId));

			if (LOG.isInfoEnabled()) {
				LOG.info(format("SpiraTest statistic was loaded for project %d: %s", projectId, statistic));
			}

			return statistic;
		} catch (SyncException e ) {
			LOG.error("Unable conect to SpiraTest ", e);
		} catch (Exception e) {
			LOG.error("Unable process SpiraTest statistic ", e);
		}
		
		return null;
	}


	private List<RemoteIncident> loadAllOpenIncidents( Integer projectId) throws SyncException {
		ImportExportSoap_PortType spira = connectToSpira();
		
		connectToSpiraProject(spira, projectId);

        RemoteIncident[] remoteIncidents = getSpiraAllOpenIncidents(spira, projectId).getRemoteIncident();
        return null == remoteIncidents
                ? Arrays.<RemoteIncident>asList()
                : asList(remoteIncidents);
	}
	
	
	private static SpiraTestStatistic calculateStatistic(List<RemoteIncident> incidents) {
		checkNotNull(incidents);

		SpiraTestStatistic spiraTestStatistic = new SpiraTestStatistic();
		for (RemoteIncident item : incidents) {
			if (!INCIDENT_TYPE_KNOWNISSUE.equalsIgnoreCase(item.getIncidentTypeName())){
                boolean isHasOwner = (null != item.getOwnerId());
                increment(item.getPriorityName(), isHasOwner, spiraTestStatistic);
			}
		}

		return spiraTestStatistic;
	}
}
