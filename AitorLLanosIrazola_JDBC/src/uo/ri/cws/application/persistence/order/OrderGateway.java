package uo.ri.cws.application.persistence.order;

import java.util.Optional;

import uo.ri.cws.application.persistence.Gateway;

public interface OrderGateway extends Gateway<OrderRecord> {

	
	
	/**
	 * 
	 * @param code the code of the order to search
	 * @return an optional with the order or null if not
	 */
	Optional<OrderRecord> findByCode(String code);
}
