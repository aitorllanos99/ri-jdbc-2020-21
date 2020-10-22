package uo.ri.cws.application.persistence.orderline;

import java.util.Optional;

import uo.ri.cws.application.persistence.Gateway;

public interface OrderLineGateway extends Gateway<OrderLineRecord> {

	/**
	 * Find a OrderLine from the sparepart id
	 * @param id the id of the sparepart
	 * @return optional with a orderlinerecord if its found null if not
	 */
	Optional<OrderLineRecord> findBySparePartId(String id);
	
	/**
	 * Find a OrderLine from the order id
	 * @param id the id of the order
	 * @return optional with a orderlinerecord if its found null if not
	 */
	Optional<OrderLineRecord> findByOrderId(String id);
}
