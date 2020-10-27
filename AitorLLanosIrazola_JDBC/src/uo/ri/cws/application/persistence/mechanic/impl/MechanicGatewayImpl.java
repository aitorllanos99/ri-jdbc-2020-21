package uo.ri.cws.application.persistence.mechanic.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import alb.util.jdbc.Jdbc;
import uo.ri.cws.application.persistence.mechanic.MechanicGateway;
import uo.ri.cws.application.persistence.mechanic.MechanicRecord;
import uo.ri.cws.application.persistence.util.Conf;
import uo.ri.cws.application.persistence.util.RecordAssembler;
/**
 * Coleccion de metodos de interaccion con la base de datos
 * @author aitor
 *
 */
public class MechanicGatewayImpl implements MechanicGateway {
	/**
	 * Comando de persistencia de añadir mecanico
	 */
	@Override
	public void add(MechanicRecord mechanic) throws SQLException {
		// Process
		Connection c = null;
		PreparedStatement pst = null;

		try {
			c = Jdbc.getCurrentConnection();
			pst = c.prepareStatement(Conf.getInstance().getProperty("TMECHANICS_ADD"));
			pst.setString(1, mechanic.id);
			pst.setString(2, mechanic.dni);
			pst.setString(3, mechanic.name);
			pst.setString(4, mechanic.surname);

			pst.executeUpdate();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			Jdbc.close(pst);
		}

	}

	/**
	 * Comando de persistencia de borrar mecanico
	 */
	@Override
	public void remove(String id) throws SQLException {
		Connection c = null;
		PreparedStatement pst = null;
		try {
			c = Jdbc.getCurrentConnection();

			pst = c.prepareStatement(Conf.getInstance().getProperty("TMECHANICS_REMOVE"));
			pst.setString(1, id);
			pst.executeUpdate();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			Jdbc.close(pst);
		}

	}

	/**
	 * Comando de persistencia de actualizar mecanico
	 */
	@Override
	public void update(MechanicRecord mechanic) throws SQLException {
		// Process
		Connection c = null;
		PreparedStatement pst = null;
		try {
			c = Jdbc.getCurrentConnection();

			pst = c.prepareStatement(Conf.getInstance().getProperty("TMECHANICS_UPDATE"));
			pst.setString(1, mechanic.name);
			pst.setString(2, mechanic.surname);
			pst.setString(3, mechanic.id);

			pst.executeUpdate();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			Jdbc.close(pst);
		}
	}

	/**
	 * Comando de persistencia de buscar mecanicos por el identificador
	 */
	@Override
	public Optional<MechanicRecord> findById(String id) throws SQLException {
		// Process
		Connection c = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			c = Jdbc.getCurrentConnection();

			pst = c.prepareStatement(Conf.getInstance().getProperty("TMECHANICS_FINBYID"));
			pst.setString(1, id);

			rs = pst.executeQuery();
			if (rs.next())
				return Optional.of(RecordAssembler.toMechanicRecord(rs));
			return Optional.ofNullable(null);

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			Jdbc.close(rs, pst);
		}
	}

	/**
	 * Comando de persistencia de listar todos los mecanicos
	 */
	@Override
	public List<MechanicRecord> findAll() throws SQLException {
		Connection c = null;
		PreparedStatement pst = null;
		List<MechanicRecord> list = null;
		try {
			c = Jdbc.getCurrentConnection();

			pst = c.prepareStatement(Conf.getInstance().getProperty("TMECHANICS_FINDALL"));

			list = RecordAssembler.toMechanicRecordList(pst.executeQuery());
			return list;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			Jdbc.close(pst);
		}
	}

	/**
	 * Comando de persistencia de buscar por dni
	 */
	@Override
	public Optional<MechanicRecord> findByDni(String dni) throws SQLException {
		// Process
		Connection c = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			c = Jdbc.getCurrentConnection();

			pst = c.prepareStatement(Conf.getInstance().getProperty("TMECHANICS_FINBYDNI"));
			pst.setString(1, dni);

			rs = pst.executeQuery();
			if (rs.next())
				return Optional.of(RecordAssembler.toMechanicRecord(rs));
			return Optional.ofNullable(null);

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			Jdbc.close(rs, pst);
		}
	}

}
