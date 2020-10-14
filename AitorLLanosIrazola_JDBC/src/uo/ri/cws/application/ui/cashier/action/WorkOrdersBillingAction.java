package uo.ri.cws.application.ui.cashier.action;

import java.util.ArrayList;
import java.util.List;

import alb.util.console.Console;
import alb.util.menu.Action;
import uo.ri.cws.application.business.BusinessException;
import uo.ri.cws.application.business.BusinessFactory;
import uo.ri.cws.application.business.invoice.InvoiceDto;
import uo.ri.cws.application.business.invoice.InvoicingService;

public class WorkOrdersBillingAction implements Action {

	@Override
	public void execute() throws BusinessException {
		List<String> workOrderIds = new ArrayList<String>();
		
		// type work order ids to be invoiced in the invoice
		do {
			String id = Console.readString("Type work order ids:  ");
			workOrderIds.add(id);
		} while (nextWorkorder());
		InvoicingService is = BusinessFactory.forCreateInvoiceService();
		InvoiceDto idto = is.createInvoiceFor(workOrderIds);

		displayInvoice(idto.total, idto);

	}

	/*
	 * read work order ids to invoice
	 */
	private boolean nextWorkorder() {
		return Console.readString(" Any other workorder? (y/n) ").equalsIgnoreCase("y");
	}

	private void displayInvoice(double totalInvoice, InvoiceDto dto) {
		Console.printf("Invoice number: %d\n", dto.number);
		Console.printf("\tDate: %1$td/%1$tm/%1$tY\n", dto.date);
		Console.printf("\tAmount: %.2f €\n", totalInvoice);
		Console.printf("\tVAT: %.1f %% \n", dto.vat);
		Console.printf("\tTotal (including VAT): %.2f €\n", dto.total);
	}
}
