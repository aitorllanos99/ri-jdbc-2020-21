package uo.ri.cws.application.persistence.intervention.impl;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import uo.ri.cws.application.persistence.intervention.InterventionGateway;
import uo.ri.cws.application.persistence.intervention.InterventionRecord;

public class InterventionGatewayImpl implements InterventionGateway {

	@Override
	public void add(InterventionRecord t) throws SQLException {
	}

	@Override
	public void remove(String id) throws SQLException {
	}

	@Override
	public void update(InterventionRecord t) throws SQLException {
	}

	@Override
	public Optional<InterventionRecord> findById(String id) throws SQLException {
		return null;
	}

	@Override
	public List<InterventionRecord> findAll() throws SQLException {
		return null;
	}

	@Override
	public List<InterventionRecord> findByMechanicId(String id) throws SQLException {
		return null;
	}

}
