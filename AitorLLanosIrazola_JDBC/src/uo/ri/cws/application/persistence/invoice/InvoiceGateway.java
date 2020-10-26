package uo.ri.cws.application.persistence.invoice;

import java.sql.SQLException;
import java.util.Optional;

import uo.ri.cws.application.persistence.Gateway;

public interface InvoiceGateway extends Gateway<InvoiceRecord> {

	/**
	 * @param invoice's number
	 * @return invoice dto or null if it does not exist
	 */
	Optional<InvoiceRecord> findByNumber(Long number);

	/**
	 * @return next invoice number to assign; that is, one greater than the greatest
	 *         number already assigned to an invoice + 1
	 * 
	 *         Notice that, in a real deployment, this way to get a new invoice
	 *         number may produce incorrect values in a concurrent environment
	 *         because two concurrent threads could get the same number.
	 * @throws SQLException
	 * 
	 */
	Long getNextInvoiceNumber() throws SQLException;

	/**
	 * Checks if a workOrder is in status finished
	 * @param workOrderID el identificador de la orden
	 * @return true if it is, false if not
	 * @throws SQLException
	 */
	boolean checkWorkOrdersFinished(String workOrderID);

	/**
	 * checks whether every work order id is FINISHED
	 * @param workOrderID el identificador de la orden
	 * @return su total
	 */
	Double getWorkOrderTotal(String workOrderID);

	/**
	 * Set the invoice number field in work order table to the invoice number
	 * generated
	 * @param invoiceId el identificador del invoice
	 * @param workOrderId el identificador de la orden
	 */
	void linkWorkordersToInvoice(String invoiceId, String workOrderId);

	/**
	 * Sets status to INVOICED for every workorder
	 * @param id el identificador del invoice
	 */
	void markWorkOrderAsInvoiced(String id);
}
