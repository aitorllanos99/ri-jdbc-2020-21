package uo.ri.cws.application.persistence.order.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import alb.util.jdbc.Jdbc;
import uo.ri.cws.application.persistence.order.OrderGateway;
import uo.ri.cws.application.persistence.order.OrderRecord;
import uo.ri.cws.application.persistence.util.Conf;
import uo.ri.cws.application.persistence.util.RecordAssembler;

public class OrderGatewayImpl implements OrderGateway {

	@Override
	public void add(OrderRecord order) throws SQLException {
		// Process
		Connection c = null;
		PreparedStatement pst = null;

		try {
			c = Jdbc.getCurrentConnection();
			pst = c.prepareStatement(Conf.getInstance().getProperty("TORDERS_ADD"));
			pst.setString(1, order.id);
			pst.setString(2, order.code);
			pst.setDouble(3, order.amount);
			pst.setDate(4, Date.valueOf(order.orderedDate));
			pst.setDate(5, Date.valueOf(order.receptionDate));
			pst.setString(6, order.status);
			pst.setString(7, order.providerId);
			pst.executeUpdate();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			Jdbc.close(pst);
		}

	}

	@Override
	public void remove(String id) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void update(OrderRecord t) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public Optional<OrderRecord> findById(String id) throws SQLException {
		// Process
		Connection c = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			c = Jdbc.getCurrentConnection();

			pst = c.prepareStatement(Conf.getInstance().getProperty("TORDERS_FINDBYID"));
			pst.setString(1, id);

			rs = pst.executeQuery();
			if (rs.next())
				return Optional.of(RecordAssembler.toOrderRecord(rs));
			return Optional.ofNullable(null);

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			Jdbc.close(rs, pst);
		}
	}

	@Override
	public List<OrderRecord> findAll() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<OrderRecord> findByCode(String code) {
		// Process
		Connection c = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			c = Jdbc.getCurrentConnection();

			pst = c.prepareStatement(Conf.getInstance().getProperty("TORDERS_FINDBYCODE"));
			pst.setString(1, code);

			rs = pst.executeQuery();
			if (rs.next())
				return Optional.of(RecordAssembler.toOrderRecord(rs));
			return Optional.ofNullable(null);

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			Jdbc.close(rs, pst);
		}
	}

	@Override
	public List<OrderRecord> findByProviderNif(String nif) {
		Connection c = null;
		PreparedStatement pst = null;
		List<OrderRecord> list = null;
		try {
			c = Jdbc.getCurrentConnection();

			pst = c.prepareStatement(Conf.getInstance().getProperty("TORDERS_FINDBYPROVIDERNIF"));
			pst.setString(1, nif);

			list = RecordAssembler.toOrderRecordList(pst.executeQuery());
			return list;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			Jdbc.close(pst);
		}
	}

}
