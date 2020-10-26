package uo.ri.cws.application.persistence.orderline.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import alb.util.jdbc.Jdbc;
import uo.ri.cws.application.persistence.orderline.OrderLineGateway;
import uo.ri.cws.application.persistence.orderline.OrderLineRecord;
import uo.ri.cws.application.persistence.util.Conf;
import uo.ri.cws.application.persistence.util.RecordAssembler;

/**
 * Coleccion de metodos de interaccion con la base de datos
 * 
 * @author aitor
 *
 */
public class OrderLineGatewayImpl implements OrderLineGateway {
	/**
	 * Comando de persistencia de añadir orderline
	 */
	@Override
	public void add(OrderLineRecord orderline) throws SQLException {
		// Process
		Connection c = null;
		PreparedStatement pst = null;

		try {
			c = Jdbc.getCurrentConnection();
			pst = c.prepareStatement(Conf.getInstance().getProperty("TORDERLINES_ADD"));
			pst.setDouble(1, orderline.price);
			pst.setInt(2, orderline.quantity);
			pst.setString(3, orderline.order_id);
			pst.setString(4, orderline.sparePart_id);
			pst.executeUpdate();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			Jdbc.close(pst);
		}

	}

	@Override
	public void remove(String id) throws SQLException {

	}

	@Override
	public void update(OrderLineRecord t) throws SQLException {

	}

	@Override
	public Optional<OrderLineRecord> findById(String id) throws SQLException {
		return null;
	}

	@Override
	public List<OrderLineRecord> findAll() throws SQLException {
		return null;
	}

	/**
	 * Comando de persistencia de buscar orderline por identificador de repuesto
	 */
	@Override
	public Optional<OrderLineRecord> findBySparePartId(String id) {
		Connection c = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			c = Jdbc.getCurrentConnection();
			pst = c.prepareStatement(Conf.getInstance().getProperty("TORDERLINES_FINDBYSPAREPARTID"));
			pst.setString(1, id);

			rs = pst.executeQuery();
			if (rs.next())
				return Optional.of(RecordAssembler.toOrderLineRecord(rs));
			return Optional.ofNullable(null);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			Jdbc.close(pst);
		}
	}

	/**
	 * Comando de persistencia de busqueda de orderline por identificador de pedido
	 */
	@Override
	public Optional<OrderLineRecord> findByOrderId(String id) {
		Connection c = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			c = Jdbc.getCurrentConnection();
			pst = c.prepareStatement(Conf.getInstance().getProperty("TORDERLINES_FINDBYORDERID"));
			pst.setString(1, id);

			rs = pst.executeQuery();
			if (rs.next())
				return Optional.of(RecordAssembler.toOrderLineRecord(rs));
			return Optional.ofNullable(null);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			Jdbc.close(pst);
		}
	}

}
