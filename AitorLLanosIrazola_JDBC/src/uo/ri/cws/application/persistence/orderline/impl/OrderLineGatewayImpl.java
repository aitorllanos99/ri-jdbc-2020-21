package uo.ri.cws.application.persistence.orderline.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import alb.util.jdbc.Jdbc;
import uo.ri.cws.application.persistence.orderline.OrderLineGateway;
import uo.ri.cws.application.persistence.orderline.OrderLineRecord;
import uo.ri.cws.application.persistence.util.Conf;
import uo.ri.cws.application.persistence.util.RecordAssembler;

public class OrderLineGatewayImpl implements OrderLineGateway {

	@Override
	public void add(OrderLineRecord t) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void remove(String id) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void update(OrderLineRecord t) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public Optional<OrderLineRecord> findById(String id) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<OrderLineRecord> findAll() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<OrderLineRecord> findBySparePartId(String id) {
		Connection c = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			c = Jdbc.getCurrentConnection();
			pst = c.prepareStatement(Conf.getInstance().getProperty("TORDERLINE_FINDBYSPAREPARTID"));
			pst.setString(1, id);
			
			rs = pst.executeQuery();
			if (rs.next())
				return Optional.of(RecordAssembler.toOrderLineRecord(rs));
			return Optional.ofNullable(null);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			Jdbc.close(pst);
		}
	}

}
