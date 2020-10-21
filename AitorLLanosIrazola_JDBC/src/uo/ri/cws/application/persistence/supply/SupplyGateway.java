package uo.ri.cws.application.persistence.supply;

import java.util.List;

import uo.ri.cws.application.persistence.Gateway;

public interface SupplyGateway extends Gateway<SupplyRecord> {

	/**
	 * Returns an optional if it finds the supply from the provider
	 * @param id the identificator of the sparePart
	 * @return an optional with the supply if it finds it, null if not
	 */
	List<SupplyRecord> findBySparePartId(String id);
}
