package ua.dp.ardas.radiator.jobs.spira.test.bugs;

import static com.google.common.base.Preconditions.checkNotNull;
import static java.lang.String.format;
import static java.util.Arrays.asList;
import static ua.dp.ardas.radiator.jobs.spira.test.bugs.SpiraTestPriorities.increment;
import static ua.dp.ardas.tools.sync.common.SyncUtil.connectToSpira;
import static ua.dp.ardas.tools.sync.common.SyncUtil.connectToSpiraProject;
import static ua.dp.ardas.tools.sync.util.ObjectsUtil.getSpiraAllOpenIncidents;
import static ua.dp.ardas.tools.sync.util.TypeHelper.INCIDENT_TYPE_BUG;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import ua.dp.ardas.tools.sync.common.SyncException;
import ua.dp.ardas.tools.sync.xp.ImportExportSoap_PortType;
import ua.dp.ardas.tools.sync.xp.RemoteIncident;

@Component
public class SpiraTestClient {
	private static Log LOG = LogFactory.getLog(SpiraTestClient.class);

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
		
		return asList(getSpiraAllOpenIncidents(spira, projectId).getRemoteIncident());
	}
	
	
	private static SpiraTestStatistic calculateStatistic(List<RemoteIncident> incidents) {
		checkNotNull(incidents);

		SpiraTestStatistic spiraTestStatistic = new SpiraTestStatistic();
		for (RemoteIncident item : incidents) {
			if (INCIDENT_TYPE_BUG.equalsIgnoreCase(item.getIncidentTypeName())){
				increment(item.getPriorityName(), spiraTestStatistic);
			} else if ("change request".equalsIgnoreCase(item.getIncidentTypeName())) {
				spiraTestStatistic.changeRequest++;
			}
		}
		return spiraTestStatistic;
	}
}
