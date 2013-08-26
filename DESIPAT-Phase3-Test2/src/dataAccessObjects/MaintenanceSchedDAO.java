package dataAccessObjects;

import Strategy.SelectMaintenanceStrategy;
import Template.SelectMaintenanceTemplate;
import Template.SelectQueryTemplate;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author sgtan_
 */
public class MaintenanceSchedDAO extends DAO{

	private static MaintenanceSchedDAO schedDAO;

	private MaintenanceSchedDAO(){

	}

	public static MaintenanceSchedDAO getInstance(){
		if(schedDAO == null)
			schedDAO = new MaintenanceSchedDAO();

		return schedDAO;
	}

	public ArrayList<String> getAllMaintenanceSchedule() throws SQLException {
		ArrayList<String> sched = new ArrayList<String>();

		SelectQueryTemplate template = new SelectMaintenanceTemplate();
		sched = template.executeQuery(new SelectMaintenanceStrategy());

		return sched;
	}

}
