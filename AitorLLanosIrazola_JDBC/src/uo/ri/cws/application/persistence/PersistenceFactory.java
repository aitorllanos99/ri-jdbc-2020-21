package uo.ri.cws.application.persistence;

import uo.ri.cws.application.persistence.intervention.InterventionGateway;
import uo.ri.cws.application.persistence.intervention.impl.InterventionGatewayImpl;
import uo.ri.cws.application.persistence.invoice.InvoiceGateway;
import uo.ri.cws.application.persistence.invoice.impl.InvoiceGatewayImpl;
import uo.ri.cws.application.persistence.mechanic.MechanicGateway;
import uo.ri.cws.application.persistence.mechanic.impl.MechanicGatewayImpl;
import uo.ri.cws.application.persistence.order.OrderGateway;
import uo.ri.cws.application.persistence.order.impl.OrderGatewayImpl;
import uo.ri.cws.application.persistence.orderline.OrderLineGateway;
import uo.ri.cws.application.persistence.orderline.impl.OrderLineGatewayImpl;
import uo.ri.cws.application.persistence.provider.ProviderGateway;
import uo.ri.cws.application.persistence.provider.impl.ProviderGatewayImpl;
import uo.ri.cws.application.persistence.sparepart.SparePartGateway;
import uo.ri.cws.application.persistence.sparepart.impl.SparePartGatewayImpl;
import uo.ri.cws.application.persistence.substitution.SubstitutionGateway;
import uo.ri.cws.application.persistence.substitution.impl.SubstitutionGatewayImpl;
import uo.ri.cws.application.persistence.supply.SupplyGateway;
import uo.ri.cws.application.persistence.supply.impl.SupplyGatewayImpl;
import uo.ri.cws.application.persistence.workorder.WorkOrderGateway;
import uo.ri.cws.application.persistence.workorder.impl.WorkOrderGatewayImpl;

/**
 * Factoria de creacion de gateways de persistencia
 * 
 * @author aitor
 *
 */
public class PersistenceFactory {
	/**
	 * Creador de la gateway de mecanicos
	 * 
	 * @return la implementacion de la gateway de mecanicos
	 */
	public static MechanicGateway forMechanic() {
		return new MechanicGatewayImpl();
	}

	/**
	 * Creador de la gateway de ordenes de trabajo
	 * 
	 * @return la implementacion de la gateway de ordenes de trabajo
	 */
	public static WorkOrderGateway forWorkOrder() {
		return new WorkOrderGatewayImpl();
	}

	/**
	 * Creador de la gateway de invoices
	 * 
	 * @return la implementacion de la gateway de invoices
	 */
	public static InvoiceGateway forInvoice() {
		return new InvoiceGatewayImpl();
	}

	/**
	 * Creador de la gateway de intervenciones
	 * 
	 * @return la implementacion de la gateway de intervenciones
	 */
	public static InterventionGateway forIntervention() {
		return new InterventionGatewayImpl();
	}

	/**
	 * Creador de la gateway de repuestos
	 * 
	 * @return la implementacion de la gateway de repuestos
	 */
	public static SparePartGateway forSparePart() {
		return new SparePartGatewayImpl();
	}

	/**
	 * Creador de la gateway de orderline
	 * 
	 * @return la implementacion de la gateway de orderline
	 */
	public static OrderLineGateway forOrderLine() {
		return new OrderLineGatewayImpl();
	}

	/**
	 * Creador de la gateway de pedidos
	 * 
	 * @return la implementacion de la gateway de pedidos
	 */
	public static OrderGateway forOrders() {
		return new OrderGatewayImpl();
	}

	/**
	 * Creador de la gateway de supply
	 * 
	 * @return la implementacion de la gateway de supply
	 */
	public static SupplyGateway forSupply() {
		return new SupplyGatewayImpl();
	}

	/**
	 * Creador de la gateway de proveedores
	 * 
	 * @return la implementacion de la gateway de proveedores
	 */
	public static ProviderGateway forProvider() {
		return new ProviderGatewayImpl();
	}

	/**
	 * Creador de la gateway de substituciones
	 * 
	 * @return la implementacion de la gateway de substituciones
	 */
	public static SubstitutionGateway forSubstitution() {
		return new SubstitutionGatewayImpl();
	}
}
