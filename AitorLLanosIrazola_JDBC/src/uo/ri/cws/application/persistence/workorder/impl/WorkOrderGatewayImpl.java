package uo.ri.cws.application.persistence.workorder.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import alb.util.jdbc.Jdbc;
import uo.ri.cws.application.persistence.util.Conf;
import uo.ri.cws.application.persistence.util.RecordAssembler;
import uo.ri.cws.application.persistence.workorder.WorkOrderGateway;
import uo.ri.cws.application.persistence.workorder.WorkOrderRecord;

public class WorkOrderGatewayImpl implements WorkOrderGateway {

	@Override
	public void add(WorkOrderRecord t) throws SQLException {

	}

	@Override
	public void remove(String id) throws SQLException {

	}

	@Override
	public void update(WorkOrderRecord t) throws SQLException {
		// Process
		Connection c = null;
		PreparedStatement pst = null;
		try {
			c = Jdbc.getCurrentConnection();

			pst = c.prepareStatement(Conf.getInstance().getProperty("TWORKORDERS_UPDATE"));
			pst.setDouble(1, t.total);
			pst.setDate(2, new Date(t.date.getTime()));
			pst.setString(3, t.description);
			pst.setString(4, t.status);
			pst.setString(5, t.invoiceId);
			pst.setString(6, t.mechanicId);
			pst.setString(7, t.id);

			pst.executeUpdate();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			Jdbc.close(pst);
		}

	}

	@Override
	public Optional<WorkOrderRecord> findById(String id) throws SQLException {
		// Process
		Connection c = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			c = Jdbc.getCurrentConnection();

			pst = c.prepareStatement(Conf.getInstance().getProperty("TWORKORDERS_FINBYID"));
			pst.setString(1, id);

			rs = pst.executeQuery();
			if (rs.next())
				return Optional.of(RecordAssembler.toWorkOrderRecord(rs));
			return Optional.ofNullable(null);

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			Jdbc.close(rs, pst);
		}
	}

	@Override
	public List<WorkOrderRecord> findAll() throws SQLException {

		return null;
	}

	@Override
	public List<WorkOrderRecord> findByIds(List<String> workOrderIds) throws SQLException {

		return null;
	}

	@Override
	public List<WorkOrderRecord> findByVehicleId(String id) throws SQLException {

		return null;
	}

	@Override
	public List<WorkOrderRecord> findByMechanicId(String id) throws SQLException {

		return null;
	}

	@Override
	public List<WorkOrderRecord> findByStatus(String status) {

		return null;
	}

}
