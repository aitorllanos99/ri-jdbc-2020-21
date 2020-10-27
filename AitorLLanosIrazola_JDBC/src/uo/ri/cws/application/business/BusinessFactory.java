package uo.ri.cws.application.business;

import uo.ri.cws.application.business.invoice.InvoicingService;
import uo.ri.cws.application.business.invoice.crud.InvoicingServiceImpl;
//import uo.ri.cws.application.business.invoice.CreateInvoiceService;
//import uo.ri.cws.application.business.invoice.create.CreateInvoiceServiceImpl;
import uo.ri.cws.application.business.mechanic.MechanicCrudService;
import uo.ri.cws.application.business.mechanic.crud.MechanicCrudServiceImpl;
import uo.ri.cws.application.business.order.OrdersService;
import uo.ri.cws.application.business.order.crud.OrdersServiceImpl;
import uo.ri.cws.application.business.provider.ProvidersCrudService;
import uo.ri.cws.application.business.sparepart.SparePartCrudService;
import uo.ri.cws.application.business.sparepart.SparePartReportService;
import uo.ri.cws.application.business.sparepart.crud.SparePartCrudServiceImpl;
import uo.ri.cws.application.business.sparepart.report.SparePartReportServiceImpl;
import uo.ri.cws.application.business.supply.SuppliesCrudService;

/**
 * Factoria de creacion de las capas de servicio
 * 
 * @author aitor
 *
 */
public class BusinessFactory {

	/**
	 * Creacion de la capa de servicio de la capa de servicio de los mecanicos
	 * 
	 * @return la capa de servicio implementada de los mecanicos
	 */
	public static MechanicCrudService forMechanicCrudService() {
		return new MechanicCrudServiceImpl();
	}

	public static InvoicingService forCreateInvoiceService() {
		return new InvoicingServiceImpl();
	}
	/**
	 * Creacion de la capa de servicio de la capa de servicio de los repuestos
	 * @return la capa de servicio implementada de los repuestos
	 */
	public static SparePartCrudService forSparePartCrudService() {
		return new SparePartCrudServiceImpl();
	}
	/**
	 * Creacion de la capa de servicio de la capa de servicio de los pedidos
	 * @return la capa de servicio implementada de los pedidos
	 */
	public static OrdersService forOrdersService() {
		return new OrdersServiceImpl();
	}

	public static ProvidersCrudService forProvidersService() {
		return null;
	}

	public static SuppliesCrudService forSuppliesCrudService() {
		return null;
	}
	/**
	 * Creacion de la capa de servicio de la capa de servicio de los report de repuestos
	 * @return la capa de servicio implementada de los reports de repuestos
	 */
	public static SparePartReportService forSparePartReportService() {
		return new SparePartReportServiceImpl();
	}

}
