package uo.ri.cws.application.persistence.sparepart.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import alb.util.jdbc.Jdbc;
import uo.ri.cws.application.persistence.sparepart.SparePartGateway;
import uo.ri.cws.application.persistence.sparepart.SparePartRecord;
import uo.ri.cws.application.persistence.util.Conf;
import uo.ri.cws.application.persistence.util.RecordAssembler;

public class SparePartGatewayImpl implements SparePartGateway {

	@Override
	public void add(SparePartRecord sparePart) throws SQLException {
		// Process
		Connection c = null;
		PreparedStatement pst = null;

		try {
			c = Jdbc.getCurrentConnection();
			pst = c.prepareStatement(Conf.getInstance().getProperty("TSPAREPARTS_ADD"));
			pst.setString(1, sparePart.id);
			pst.setString(2, sparePart.code);
			pst.setString(3, sparePart.description);
			pst.setInt(4, sparePart.stock);
			pst.setInt(5, sparePart.maxStock);
			pst.setInt(6, sparePart.minStock);
			pst.setDouble(7, sparePart.price);

			pst.executeUpdate();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			Jdbc.close(pst);
		}

	}

	@Override
	public void remove(String id) throws SQLException {
		Connection c = null;
		PreparedStatement pst = null;
		try {
			c = Jdbc.getCurrentConnection();

			pst = c.prepareStatement(Conf.getInstance().getProperty("TSPAREPARTS_REMOVE"));
			pst.setString(1, id);
			pst.executeUpdate();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			Jdbc.close(pst);
		}

	}

	@Override
	public void update(SparePartRecord sparePart) throws SQLException {
		// Process
		Connection c = null;
		PreparedStatement pst = null;
		try {
			c = Jdbc.getCurrentConnection();

			pst = c.prepareStatement(Conf.getInstance().getProperty("TSPAREPARTS_UPDATE"));
			pst.setString(1, sparePart.description);
			pst.setInt(2, sparePart.stock);
			pst.setInt(3, sparePart.maxStock);
			pst.setInt(4, sparePart.minStock);
			pst.setDouble(5, sparePart.price);
			pst.setString(6, sparePart.code);
			pst.executeUpdate();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			Jdbc.close(pst);
		}

	}

	@Override
	public Optional<SparePartRecord> findById(String id) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<SparePartRecord> findAll() throws SQLException {
		Connection c = null;
		PreparedStatement pst = null;
		List<SparePartRecord> list = null;
		try {
			c = Jdbc.getCurrentConnection();

			pst = c.prepareStatement(Conf.getInstance().getProperty("TSPAREPARTS_FINDALL"));

			list = RecordAssembler.toSparePartRecordList(pst.executeQuery());
			return list;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			Jdbc.close(pst);
		}
	}

	@Override
	public Optional<SparePartRecord> findByCode(String code) throws SQLException {
		// Process
		Connection c = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			c = Jdbc.getCurrentConnection();

			pst = c.prepareStatement(Conf.getInstance().getProperty("TSPAREPARTS_FINDBYCODE"));
			pst.setString(1, code);

			rs = pst.executeQuery();
			if (rs.next())
				return Optional.of(RecordAssembler.toSparePartRecord(rs));
			return Optional.ofNullable(null);

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			Jdbc.close(rs, pst);
		}
	}

	@Override
	public List<SparePartRecord> findByDescritpion(String description) throws SQLException {
		Connection c = null;
		PreparedStatement pst = null;
		List<SparePartRecord> list = null;
		try {
			c = Jdbc.getCurrentConnection();
			description = "%" + description + "%"; // This go here because is part of the SQL Language
			//To search any partial coincidende
			pst = c.prepareStatement(Conf.getInstance().getProperty("TSPAREPARTS_FINDBYDESCRIPTION"));
			pst.setString(1, description);
			list = RecordAssembler.toSparePartRecordList(pst.executeQuery());
			return list;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			Jdbc.close(pst);
		}
	}

	@Override
	public List<SparePartRecord> findUnderStock() throws SQLException {
		Connection c = null;
		PreparedStatement pst = null;
		List<SparePartRecord> list = null;
		try {
			c = Jdbc.getCurrentConnection();
			pst = c.prepareStatement(Conf.getInstance().getProperty("TSPAREPARTS_FINDUNDERSTOCK"));
			list = RecordAssembler.toSparePartRecordList(pst.executeQuery());
			return list;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			Jdbc.close(pst);
		}
	}

}
