package uo.ri.cws.application.persistence.invoice.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import alb.util.jdbc.Jdbc;
import uo.ri.cws.application.persistence.invoice.InvoiceGateway;
import uo.ri.cws.application.persistence.invoice.InvoiceRecord;
import uo.ri.cws.application.persistence.util.Conf;

public class InvoiceGatewayImpl implements InvoiceGateway {

	@Override
	public void add(InvoiceRecord idto) throws SQLException {
		Connection connection = null;
		PreparedStatement pst = null;
		String idInvoice;

		try {
			connection = Jdbc.getConnection();
			idInvoice = UUID.randomUUID().toString();

			pst = connection.prepareStatement(Conf.getInstance().getProperty("SQL_INSERT_INVOICE"));
			pst.setString(1, idInvoice);
			pst.setLong(2, idto.number);
			pst.setDate(3, new java.sql.Date(idto.date.getTime()));
			pst.setDouble(4, idto.vat);
			pst.setDouble(5, idto.total);
			pst.setString(6, "NOT_YET_PAID");

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
	public void update(InvoiceRecord t) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public Optional<InvoiceRecord> findById(String id) throws SQLException {
		ResultSet rs = null;
		Connection connection = null;
		PreparedStatement pst = null;
		try {
			connection = Jdbc.getConnection();
			pst = connection.prepareStatement(Conf.getInstance().getProperty(SQL_FIND_WORKORDERS));

				pst.setString(1, workOrderID);

				rs = pst.executeQuery();
				if (rs.next() == false) {
					return false;
				}

			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			Jdbc.close(rs, pst);
		}
		return true;
	}

	@Override
	public List<InvoiceRecord> findAll() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<InvoiceRecord> findByNumber(Long number) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long getNextInvoiceNumber() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

}
