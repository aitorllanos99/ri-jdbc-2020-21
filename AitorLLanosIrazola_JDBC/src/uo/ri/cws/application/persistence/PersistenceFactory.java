package uo.ri.cws.application.persistence;

import uo.ri.cws.application.persistence.mechanic.MechanicGateway;
import uo.ri.cws.application.persistence.mechanic.impl.MechanicGatewayImpl;
import uo.ri.cws.application.persistence.order.OrderGateway;
import uo.ri.cws.application.persistence.order.impl.OrderGatewayImpl;
import uo.ri.cws.application.persistence.orderline.OrderLineGateway;
import uo.ri.cws.application.persistence.orderline.impl.OrderLineGatewayImpl;
import uo.ri.cws.application.persistence.sparepart.SparePartGateway;
import uo.ri.cws.application.persistence.sparepart.impl.SparePartGatewayImpl;

public class PersistenceFactory {

	public static MechanicGateway forMechanic() {
		return new MechanicGatewayImpl();
	}

//	public static WorkOrderGateway forWorkOrder() {
//		return new WorkOrderGatewayImpl();
//	}

//	public static InvoiceGateway forInvoice() {
	//	return new InvoiceGatewayImpl();
	//}

//	public static InterventionGateway forIntervention() {
	//	return new InterventionGatewayImpl();
	//}
	public static SparePartGateway forSparePart() {
		return new SparePartGatewayImpl();
	}
	
	public static OrderLineGateway forOrderLine(){
		return new OrderLineGatewayImpl();
	}
	
	public static OrderGateway forOrders(){
		return new OrderGatewayImpl();
	}
}

