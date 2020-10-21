package uo.ri.cws.application.persistence.provider.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import alb.util.jdbc.Jdbc;
import uo.ri.cws.application.persistence.provider.ProviderGateway;
import uo.ri.cws.application.persistence.provider.ProviderRecord;
import uo.ri.cws.application.persistence.util.Conf;
import uo.ri.cws.application.persistence.util.RecordAssembler;

public class ProviderGatewayImpl implements ProviderGateway {

	@Override
	public void add(ProviderRecord t) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void remove(String id) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void update(ProviderRecord t) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public Optional<ProviderRecord> findById(String id) throws SQLException {
		// Process
		Connection c = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			c = Jdbc.getCurrentConnection();

			pst = c.prepareStatement(Conf.getInstance().getProperty("TPROVIDERS_FINBYID"));
			pst.setString(1, id);

			rs = pst.executeQuery();
			if (rs.next())
				return Optional.of(RecordAssembler.toProviderRecord(rs));
			return Optional.ofNullable(null);

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			Jdbc.close(rs, pst);
		}
	}

	@Override
	public List<ProviderRecord> findAll() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

}
