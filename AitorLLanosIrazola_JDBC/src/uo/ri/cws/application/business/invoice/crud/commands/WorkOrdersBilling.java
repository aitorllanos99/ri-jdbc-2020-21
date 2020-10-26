package uo.ri.cws.application.business.invoice.crud.commands;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import alb.util.jdbc.Jdbc;
import alb.util.math.Round;
import uo.ri.cws.application.business.BusinessException;
import uo.ri.cws.application.business.invoice.InvoiceDto;
/**
 * Comando de logica del crear invoice para ordenes de trabajo
 * @author aitor
 *
 */
public class WorkOrdersBilling {
	private static final String SQL_FIND_WORKORDERS = "select * from TWorkOrders where id = ?";
	private static final String SQL_CHECK_WORKORDER_STATUS = "select status from TWorkOrders where id = ?";
	private static final String SQL_LAST_INVOICE_NUMBER = "select max(number) from TInvoices";
	private static final String SQL_FIND_WORKORDER_AMOUNT = "select amount from TWorkOrders where id = ?";
	private static final String SQL_INSERT_INVOICE = 
			"insert into TInvoices(id, number, date, vat, amount, status) "
					+ "	values(?, ?, ?, ?, ?, ?)";
	private static final String SQL_LINK_WORKORDER_TO_INVOICE = "update TWorkOrders set invoice_id = ? where id = ?";
	private static final String SQL_MARK_WORKORDER_AS_INVOICED = "update TWorkOrders set status = 'INVOICED' where id = ?";


	private List<String> workOrdersIds;
	public WorkOrdersBilling(List<String> workOrdersIds) {
		this.workOrdersIds = workOrdersIds;
	}

	public InvoiceDto execute() throws BusinessException {
		InvoiceGateway ig = PersistenceFactory.forInvoice();
		if(!checkWorkOrdersExist(workOrdersIds))
			throw new BusinessException("Workorder does not exist");
		if(!checkWorkOrdersFinished(workOrdersIds))
			throw new BusinessException("Workorder is not finished yet");

		long numberInvoice = generateInvoiceNumber();
		LocalDate dateInvoice = LocalDate.now();
		double amount = calculateTotalInvoice(workOrdersIds); // vat not included
		double vat = vatPercentage(amount, dateInvoice);
		double total = amount * (1 + vat / 100); // vat included
		total = Round.twoCents(total);

		InvoiceDto idto = new InvoiceDto();
		idto.number = numberInvoice;
		idto.date = dateInvoice;
		idto.vat = vat;
		idto.total = total;
		String idInvoice = createInvoice(idto);
		
		linkWorkordersToInvoice(idInvoice, workOrdersIds);
		markWorkOrderAsInvoiced(workOrdersIds);
		return idto;
	}


	/*
	 * Generates next invoice number (not to be confused with the inner id)
	 */
	private Long generateInvoiceNumber() {
		Connection connection = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			connection = Jdbc.getConnection();
			pst = connection.prepareStatement(SQL_LAST_INVOICE_NUMBER);
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
	
	private boolean checkWorkOrdersExist(List<String> workOrdersIds) throws BusinessException {
		ResultSet rs = null;
		Connection connection = null;
		PreparedStatement pst = null;
		try {
			connection = Jdbc.getConnection();
			pst = connection.prepareStatement(SQL_FIND_WORKORDERS);

			for (String workOrderID : workOrdersIds) {
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
	
	private boolean checkWorkOrdersFinished(List<String> workOrdersIds) throws BusinessException {
		ResultSet rs = null;
		Connection connection = null;
		PreparedStatement pst = null;
		try {
			connection = Jdbc.getConnection();
			pst = connection.prepareStatement(SQL_CHECK_WORKORDER_STATUS);

			for (String workOrderID : workOrdersIds) {
				pst.setString(1, workOrderID);

				rs = pst.executeQuery();
				rs.next();
				String status = rs.getString(1);
				if (!"FINISHED".equalsIgnoreCase(status)) {
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

	/*
	 * Compute total amount of the invoice (as the total of individual work orders'
	 * amount
	 */
	private double calculateTotalInvoice(List<String> workOrderIDS) throws BusinessException {

		double totalInvoice = 0.0;
		for (String workOrderID : workOrderIDS) {
			totalInvoice += getWorkOrderTotal(workOrderID);
		}
		return totalInvoice;
	}

	/*
	 * checks whether every work order id is FINISHED
	 */
	private Double getWorkOrderTotal(String workOrderID) throws BusinessException {
		Connection connection = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		Double money = 0.0;

		try {
			connection = Jdbc.getConnection();
			pst = connection.prepareStatement(SQL_FIND_WORKORDER_AMOUNT);
			pst.setString(1, workOrderID);

			rs = pst.executeQuery();
			if (rs.next() == false) {
				throw new BusinessException("Workorder " + workOrderID + " doesn't exist");
			}

			money = rs.getDouble(1);

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			Jdbc.close(rs, pst);
		}
		return money;
	}

	/*
	 * returns vat percentage
	 */
	private double vatPercentage(double totalInvoice, LocalDate dateInvoice) {
		return LocalDate.parse("2012-07-01").isBefore(dateInvoice) ? 21.0 : 18.0;

	}

	/*
	 * Creates the invoice in the database; returns the id
	 */
	private String createInvoice(InvoiceDto idto) {
		Connection connection = null;
		PreparedStatement pst = null;
		String idInvoice;

		try {
			connection = Jdbc.getConnection();
			idInvoice = UUID.randomUUID().toString();

			pst = connection.prepareStatement(SQL_INSERT_INVOICE);
			pst.setString(1, idInvoice);
			pst.setLong(2, idto.number);
			pst.setDate(3, java.sql.Date.valueOf(idto.date));
			pst.setDouble(4, idto.vat);
			pst.setDouble(5, idto.total);
			pst.setString(6, "NOT_YET_PAID");

			pst.executeUpdate();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			Jdbc.close(pst);
		}
		return idto.id;
	}

	/*
	 * Set the invoice number field in work order table to the invoice number
	 * generated
	 */
	private void linkWorkordersToInvoice(String invoiceId, List<String> workOrderIds) {
		Connection connection = null;
		PreparedStatement pst = null;
		try {
			connection = Jdbc.getConnection();
			pst = connection.prepareStatement(SQL_LINK_WORKORDER_TO_INVOICE);

			for (String breakdownId : workOrderIds) {
				pst.setString(1, invoiceId);
				pst.setString(2, breakdownId);

				pst.executeUpdate();
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			Jdbc.close(pst);
		}
	}

	/*
	 * Sets status to INVOICED for every workorder
	 */
	private void markWorkOrderAsInvoiced(List<String> ids) {
		Connection connection = null;
		PreparedStatement pst = null;
		try {
			connection = Jdbc.getConnection();
			pst = connection.prepareStatement(SQL_MARK_WORKORDER_AS_INVOICED);

			for (String id : ids) {
				pst.setString(1, id);

				pst.executeUpdate();
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			Jdbc.close(pst);
		}
	}


}
