package ua.dp.ardas.radiator.resr.server;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import ua.dp.ardas.radiator.dto.report.Report;
import ua.dp.ardas.radiator.jobs.report.builder.ReportBuilder;

@Controller
@Path("report")
public class ReportController {
	
	@Autowired
	private ReportBuilder reportBuilder;
	
	@GET
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public Report getAllCustomers() {
		return reportBuilder.agregateReportObject();
	}
}