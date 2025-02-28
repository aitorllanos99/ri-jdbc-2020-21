package uo.ri.cws.application.persistence.sparepart;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import uo.ri.cws.application.persistence.Gateway;

public interface SparePartGateway extends Gateway<SparePartRecord> {

	/**
	 * @param code
	 * @return the sparePartRecord identified by the code or null if none
	 *   @throws SQLException si falla
	 */
	Optional<SparePartRecord> findByCode(String code) throws SQLException;

	/**
	 * @param description la descripcion
	 * @return the list of sparePartRecords identified by the description that
	 *         partially have that description
	 *           @throws SQLException si falla
	 */
	List<SparePartRecord> findByDescritpion(String description) throws SQLException;

	/**
	 * @return the list of sparePartRecords identified which has lower stock than
	 *         the minimun
	 *         @throws SQLException si falla
	 */
	List<SparePartRecord> findUnderStock() throws SQLException;

}
