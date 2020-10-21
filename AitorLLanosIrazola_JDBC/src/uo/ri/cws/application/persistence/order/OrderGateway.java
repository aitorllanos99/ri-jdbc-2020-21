package uo.ri.cws.application.persistence.order;

import java.util.List;

import uo.ri.cws.application.persistence.Gateway;

public interface OrderGateway extends Gateway<OrderRecord> {

	/**
	 * 
	 * @return the list of the orders generated
	*/
	List<OrderRecord> generateOrders(List<OrderRecord> ordersToGenerate);
}
