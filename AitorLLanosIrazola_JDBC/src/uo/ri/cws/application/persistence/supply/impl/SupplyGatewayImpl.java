package uo.ri.cws.application.persistence.supply.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import alb.util.jdbc.Jdbc;
import uo.ri.cws.application.persistence.supply.SupplyGateway;
import uo.ri.cws.application.persistence.supply.SupplyRecord;
import uo.ri.cws.application.persistence.util.Conf;
import uo.ri.cws.application.persistence.util.RecordAssembler;

/**
 * Coleccion de metodos de interaccion con la base de datos
 * 
 * @author aitor
 *
 */
public class SupplyGatewayImpl implements SupplyGateway {

	@Override
	public void add(SupplyRecord t) throws SQLException {

	}

	@Override
	public void remove(String id) throws SQLException {

	}

	/**
	 * Comando de persistencia de actualizar supply
	 */
	@Override
	public void update(SupplyRecord supply) throws SQLException {
		// Process
		Connection c = null;
		PreparedStatement pst = null;
		try {
			c = Jdbc.getCurrentConnection();

			pst = c.prepareStatement(Conf.getInstance().getProperty("TSPUPPLIES_UPDATE"));
			pst.setInt(1, supply.deliveryTerm);
			pst.setDouble(2, supply.price);
			pst.setString(3, supply.providerId);
			pst.setString(4, supply.sparePartId);

			pst.executeUpdate();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			Jdbc.close(pst);
		}

	}

	@Override
	public Optional<SupplyRecord> findById(String id) throws SQLException {

		return null;
	}

	@Override
	public List<SupplyRecord> findAll() throws SQLException {

		return null;
	}

	/**
	 * Comando de persistencia de buscar supply por identificador de repuesto
	 */
	@Override
	public List<SupplyRecord> findBySparePartId(String id) {
		// Process
		Connection c = null;
		PreparedStatement pst = null;
		List<SupplyRecord> list = new ArrayList<SupplyRecord>();
		try {
			c = Jdbc.getCurrentConnection();

			pst = c.prepareStatement(Conf.getInstance().getProperty("TSUPPLIES_FINDBYSPAREPARTID"));
			pst.setString(1, id);

			list = RecordAssembler.toSupplyRecordList(pst.executeQuery());
			return list;

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			Jdbc.close(pst);
		}
	}

}
