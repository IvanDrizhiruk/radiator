package ua.dp.ardas.radiator.jobs.spira.test.bugs;

import static ua.dp.ardas.tools.sync.common.SyncUtil.connectToSpira;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.mutable.MutableInt;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import com.google.common.base.Preconditions;

import ua.dp.ardas.tools.sync.common.SyncException;
import ua.dp.ardas.tools.sync.common.SyncUtil;
import ua.dp.ardas.tools.sync.util.ObjectsUtil;
import ua.dp.ardas.tools.sync.util.TypeHelper;
import ua.dp.ardas.tools.sync.xp.ArrayOfRemoteIncident;
import ua.dp.ardas.tools.sync.xp.ImportExportSoap_PortType;
import ua.dp.ardas.tools.sync.xp.RemoteIncident;

@Component
public class SpiraTestClient {
	private static Log LOG = LogFactory.getLog(SpiraTestClient.class);
	
	private Integer projectId = 11;

	public SpiraTestStatistic loadBugCount() {
		try {
			return countIncidents(projectId, connectToSpira());
		} catch (SyncException e) {
			LOG.error("Unable connect to SpiraTest ", e);
			return null;
		}
	}
	
	
	private static SpiraTestStatistic countIncidents(Integer spiraProjectId, ImportExportSoap_PortType importExportSoap)
			throws SyncException {
		Preconditions.checkNotNull(spiraProjectId);
		
		return getIncidents(importExportSoap, spiraProjectId);
	}
	

	public static SpiraTestStatistic getIncidents(ImportExportSoap_PortType spira, Integer spiraProjectId) throws SyncException {
		SyncUtil.connectToSpiraProject(spira, spiraProjectId);
		ArrayOfRemoteIncident list = ObjectsUtil.getSpiraAllOpenIncidents(spira, spiraProjectId);
		Map<String, MutableInt> incidents = new HashMap<String, MutableInt>();
		for (RemoteIncident item : list.getRemoteIncident()) {
			if (!TypeHelper.INCIDENT_TYPE_BUG.equalsIgnoreCase(item.getIncidentTypeName())){
				continue;
			}
			String priority = item.getPriorityName();
			MutableInt incidentItem = getIncidentItem(incidents, priority);
			incidentItem.increment();
		}
		for (Map.Entry<String, MutableInt> e : incidents.entrySet()) {
			LOG.info(String.format("priority=[%s] count=[%s]",e.getKey(), e.getValue().getValue()));
		}
		
		return new SpiraTestStatistic();
	}
	
	private static MutableInt getIncidentItem(Map<String, MutableInt> incidents, String priority) {
		if (!incidents.containsKey(priority)) {
			MutableInt incidentItem = new MutableInt();
			incidents.put(priority, incidentItem);
			return incidentItem;
		} else {
			return incidents.get(priority);
		}
	}
}
