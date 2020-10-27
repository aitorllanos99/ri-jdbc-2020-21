package uo.ri.cws.application.persistence.invoice.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import alb.util.jdbc.Jdbc;
import uo.ri.cws.application.persistence.invoice.InvoiceGateway;
import uo.ri.cws.application.persistence.invoice.InvoiceRecord;
import uo.ri.cws.application.persistence.util.Conf;
import uo.ri.cws.application.persistence.util.RecordAssembler;

public class InvoiceGatewayImpl implements InvoiceGateway {
	/**
	 * Comando de persistencia que añade un invoice
	 * 
	 */
	@Override
	public void add(InvoiceRecord idto) throws SQLException {
		Connection connection = null;
		PreparedStatement pst = null;

		try {
			connection = Jdbc.getCurrentConnection();

			pst = connection.prepareStatement(Conf.getInstance().getProperty("SQL_INSERT_INVOICE"));
			pst.setString(1, idto.id);
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

	}

	@Override
	public void update(InvoiceRecord t) throws SQLException {
	}

	/**
	 * Comando de persistencia que busca un invoice por su id
	 * 
	 */
	@Override
	public Optional<InvoiceRecord> findById(String id) throws SQLException {
		ResultSet rs = null;
		Connection connection = null;
		PreparedStatement pst = null;
		try {
			connection = Jdbc.getCurrentConnection();
			pst = connection.prepareStatement(Conf.getInstance().getProperty("SQL_FIND_WORKORDERS"));

			pst.setString(1, id);

			rs = pst.executeQuery();
			if (rs.next()) {
				Optional.of(RecordAssembler.toInvoiceRecord(rs));
			}
			return Optional.of(null);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			Jdbc.close(rs, pst);

		}
	}

	@Override
	public List<InvoiceRecord> findAll() throws SQLException {
		return null;
	}

	@Override
	public Optional<InvoiceRecord> findByNumber(Long number) {
		return null;
	}

	/**
	 * Generates next invoice number (not to be confused with the inner id)
	 */
	@Override
	public Long getNextInvoiceNumber() throws SQLException {
		Connection connection = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			connection = Jdbc.getCurrentConnection();
			pst = connection.prepareStatement(Conf.getInstance().getProperty("SQL_LAST_INVOICE_NUMBER"));
			rs = pst.executeQuery();

			if (rs.next()) {
				return rs.getLong(1) + 1; // +1, next
			} else { // there is none yet
				return 1L;
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			Jdbc.close(pst);
		}
	}

	/**
	 * Comando de persistencia que busca si una orden de trabajo esta acabada
	 * 
	 */
	@Override
	public boolean checkWorkOrdersFinished(String workOrderID){
		ResultSet rs = null;
		Connection connection = null;
		PreparedStatement pst = null;
		try {
			connection = Jdbc.getCurrentConnection();
			pst = connection.prepareStatement(Conf.getInstance().getProperty("SQL_CHECK_WORKORDER_STATUS"));
			pst.setString(1, workOrderID);

			rs = pst.executeQuery();
			rs.next();
			String status = rs.getString(1);
			if (!"FINISHED".equalsIgnoreCase(status)) {
				return false;

			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			Jdbc.close(rs, pst);
		}
		return true;
	}

	/**
	 * Comando de persistencia que obtiene el valor de una orden de trabajo
	 * 
	 */
	@Override
	public Double getWorkOrderTotal(String workOrderID) {
		Connection connection = null;
		PreparedStatement pst = null;
		ResultSet rs = null;

		try {
			connection = Jdbc.getConnection();
			pst = connection.prepareStatement(Conf.getInstance().getProperty("SQL_FIND_WORKORDER_AMOUNT"));
			pst.setString(1, workOrderID);

			rs = pst.executeQuery();
			if (rs.next() == false) {
				return 0.0;
			}
			return rs.getDouble(1);

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			Jdbc.close(rs, pst);
		}

	}

	/**
	 * Set the invoice number field in work order table to the invoice number
	 * generated
	 */
	@Override
	public void linkWorkordersToInvoice(String invoiceId, String workOrderId) {
		Connection connection = null;
		PreparedStatement pst = null;
		try {
			connection = Jdbc.getConnection();
			pst = connection.prepareStatement(Conf.getInstance().getProperty("SQL_LINK_WORKORDER_TO_INVOICE"));

			pst.setString(1, invoiceId);
			pst.setString(2, workOrderId);

			pst.executeUpdate();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			Jdbc.close(pst);
		}

	}

	/**
	 * Sets status to INVOICED for every workorder
	 */
	@Override
	public void markWorkOrderAsInvoiced(String id) {
		Connection connection = null;
		PreparedStatement pst = null;
		try {
			connection = Jdbc.getConnection();
			pst = connection.prepareStatement(Conf.getInstance().getProperty("SQL_MARK_WORKORDER_AS_INVOICED"));

			pst.setString(1, id);

			pst.executeUpdate();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			Jdbc.close(pst);
		}

	}

}
