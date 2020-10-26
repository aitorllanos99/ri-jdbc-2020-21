package uo.ri.cws.application.business.invoice.crud.commands;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import alb.util.math.Round;
import uo.ri.cws.application.business.BusinessException;
import uo.ri.cws.application.business.invoice.InvoiceDto;
import uo.ri.cws.application.business.util.DtoMapper;
import uo.ri.cws.application.business.util.command.Command;
import uo.ri.cws.application.persistence.PersistenceFactory;
import uo.ri.cws.application.persistence.invoice.InvoiceGateway;

/**
 * Comando de logica del crear invoice para ordenes de trabajo
 * 
 * @author aitor
 *
 */
public class WorkOrdersBilling implements Command<InvoiceDto>{

	private InvoiceGateway ig = PersistenceFactory.forInvoice();
	private List<String> workOrdersIds;

	public WorkOrdersBilling(List<String> workOrdersIds) {
		this.workOrdersIds = workOrdersIds;
	}

	public InvoiceDto execute() throws BusinessException, SQLException {

		if (!checkWorkOrdersExist(workOrdersIds))
			throw new BusinessException("Workorder does not exist");
		if (!checkWorkOrdersFinished(workOrdersIds))
			throw new BusinessException("Workorder is not finished yet");

		long numberInvoice = ig.getNextInvoiceNumber();
		LocalDate dateInvoice = LocalDate.now();
		double amount = calculateTotalInvoice(workOrdersIds); // vat not included
		double vat = vatPercentage(amount, dateInvoice);
		double total = amount * (1 + vat / 100); // vat included
		total = Round.twoCents(total);

		InvoiceDto idto = new InvoiceDto();
		idto.id = UUID.randomUUID().toString();
		idto.number = numberInvoice;
		idto.date = dateInvoice;
		idto.vat = vat;
		idto.total = total;
		ig.add(DtoMapper.toRecord(idto));

		linkWorkordersToInvoice(idto.id, workOrdersIds);
		markWorkOrderAsInvoiced(workOrdersIds);
		return idto;
	}

	private boolean checkWorkOrdersExist(List<String> workOrdersIds) throws BusinessException, SQLException {
		for (String workOrderID : workOrdersIds)
			if (ig.findById(workOrderID).isEmpty())
				return false;
		return true;

	}

	private boolean checkWorkOrdersFinished(List<String> workOrdersIds) throws BusinessException, SQLException {
		for (String workOrderID : workOrdersIds) {
			if(!ig.checkWorkOrdersFinished(workOrderID))
				return false;
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
		return ig.getWorkOrderTotal(workOrderID);
	}

	/*
	 * returns vat percentage
	 */
	private double vatPercentage(double totalInvoice, LocalDate dateInvoice) {
		return LocalDate.parse("2012-07-01").isBefore(dateInvoice) ? 21.0 : 18.0;

	}

	/*
	 * Set the invoice number field in work order table to the invoice number
	 * generated
	 */
	private void linkWorkordersToInvoice(String invoiceId, List<String> workOrderIds) {
		for(String id: workOrderIds)
			ig.linkWorkordersToInvoice(invoiceId, id);
	}

	/*
	 * Sets status to INVOICED for every workorder
	 */
	private void markWorkOrderAsInvoiced(List<String> ids) {
		for(String id: ids)
			ig.markWorkOrderAsInvoiced(id);
	}

}
