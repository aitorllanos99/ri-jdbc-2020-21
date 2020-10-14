package uo.ri.cws.application.persistence.intervention;

import java.sql.SQLException;
import java.util.List;

import uo.ri.cws.application.persistence.Gateway;

public interface InterventionGateway extends Gateway<InterventionRecord> {

	/**
	 * @param id, refers to the mechanic id
	 * @return a list with all interventions done by the mechanic
	 * or an empty list if there are none
	 * @throws SQLException 
	 */
	List<InterventionRecord> findByMechanicId(String id) throws SQLException;

}
