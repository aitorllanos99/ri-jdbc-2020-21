package uo.ri.cws.application.business.invoice.crud;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import uo.ri.cws.application.business.BusinessException;
import uo.ri.cws.application.business.invoice.InvoiceDto;
import uo.ri.cws.application.business.invoice.InvoicingService;
import uo.ri.cws.application.business.invoice.InvoicingWorkOrderDto;
import uo.ri.cws.application.business.invoice.PaymentMeanDto;
import uo.ri.cws.application.business.invoice.crud.commands.WorkOrdersBilling;

/**
 * Capa de servicio de los invoice
 * 
 * @author aitor
 *
 */
public class InvoicingServiceImpl implements InvoicingService {
	/**
	 * Capa de servicio de la creacion de invoice para unas ordenes de trabajo
	 */
	@Override
	public InvoiceDto createInvoiceFor(List<String> workOrderIds) throws BusinessException {
		return new WorkOrdersBilling(workOrderIds).execute();
	}

	@Override
	public List<InvoicingWorkOrderDto> findWorkOrdersByClientDni(String dni) throws BusinessException {
		return null;
	}

	@Override
	public Optional<InvoiceDto> findInvoice(Long number) throws BusinessException {
		return null;
	}

	@Override
	public List<PaymentMeanDto> findPayMeansByClientDni(String dni) throws BusinessException {
		return null;
	}

	@Override
	public void settleInvoice(String invoiceId, Map<Long, Double> charges) throws BusinessException {
	}

}
