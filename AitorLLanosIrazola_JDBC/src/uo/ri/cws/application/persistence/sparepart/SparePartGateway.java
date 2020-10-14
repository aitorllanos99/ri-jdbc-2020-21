package uo.ri.cws.application.persistence.sparepart;

import java.sql.SQLException;
import java.util.Optional;

import uo.ri.cws.application.persistence.Gateway;

public interface SparePartGateway extends Gateway<SparePartRecord> {
	
	/**
	 * @param code
	 * @return the sparePartRecord identified by the code or null if none
	 */
	Optional<SparePartRecord> findByCode(String code) throws SQLException;

}
