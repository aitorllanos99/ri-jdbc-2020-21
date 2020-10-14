package uo.ri.cws.application.persistence;

import uo.ri.cws.application.persistence.mechanic.MechanicGateway;
import uo.ri.cws.application.persistence.mechanic.impl.MechanicGatewayImpl;

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

}

