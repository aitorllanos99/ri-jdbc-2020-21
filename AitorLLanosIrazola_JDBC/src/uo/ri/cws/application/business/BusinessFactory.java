package uo.ri.cws.application.business;

import uo.ri.cws.application.business.invoice.InvoicingService;
import uo.ri.cws.application.business.invoice.crud.InvoicingServiceImpl;
//import uo.ri.cws.application.business.invoice.CreateInvoiceService;
//import uo.ri.cws.application.business.invoice.create.CreateInvoiceServiceImpl;
import uo.ri.cws.application.business.mechanic.MechanicCrudService;
import uo.ri.cws.application.business.mechanic.crud.MechanicCrudServiceImpl;
import uo.ri.cws.application.business.order.OrdersService;
import uo.ri.cws.application.business.provider.ProvidersCrudService;
import uo.ri.cws.application.business.sparePart.SparePartCrudService;
import uo.ri.cws.application.business.sparePart.SparePartReportService;
import uo.ri.cws.application.business.sparePart.crud.SparePartCrudServiceImpl;
import uo.ri.cws.application.business.supply.SuppliesCrudService;

public class BusinessFactory {


	public static MechanicCrudService forMechanicCrudService() {
		return new MechanicCrudServiceImpl();
	}

	public static InvoicingService forCreateInvoiceService() {
	return new InvoicingServiceImpl();
	}
	public static SparePartCrudService forSparePartCrudService() {
		return new SparePartCrudServiceImpl();
	}

	public static OrdersService forOrdersService() {
		// TODO Auto-generated method stub
		return null;
	}

	public static ProvidersCrudService forProvidersService() {
		// TODO Auto-generated method stub
		return null;
	}

	public static SuppliesCrudService forSuppliesCrudService() {
		// TODO Auto-generated method stub
		return null;
	}

	public static SparePartReportService forSparePartReportService() {
		// TODO Auto-generated method stub
		return null;
	}





}

