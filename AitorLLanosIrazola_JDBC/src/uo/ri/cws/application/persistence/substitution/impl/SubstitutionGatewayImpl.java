package uo.ri.cws.application.persistence.substitution.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import alb.util.jdbc.Jdbc;
import uo.ri.cws.application.persistence.substitution.SubstitutionGateway;
import uo.ri.cws.application.persistence.substitution.SubstitutionRecord;
import uo.ri.cws.application.persistence.util.Conf;
import uo.ri.cws.application.persistence.util.RecordAssembler;
/**
 * Coleccion de metodos de interaccion con la base de datos
 * @author aitor
 *
 */
public class SubstitutionGatewayImpl implements SubstitutionGateway {

	@Override
	public void add(SubstitutionRecord t) throws SQLException {
	

	}

	@Override
	public void remove(String id) throws SQLException {
		

	}

	@Override
	public void update(SubstitutionRecord t) throws SQLException {
	

	}

	@Override
	public Optional<SubstitutionRecord> findById(String id) throws SQLException {

		return null;
	}

	@Override
	public List<SubstitutionRecord> findAll() throws SQLException {
	
		return null;
	}

	/**
	 * Metodo de persistencia que busca por identificador de un repuesto en la tabla substituciones
	 */
	@Override
	public List<SubstitutionRecord> findBySparePart(String id) {
		// Process
		Connection c = null;
		PreparedStatement pst = null;
		List<SubstitutionRecord> list = new ArrayList<SubstitutionRecord>();
		try {
			c = Jdbc.getCurrentConnection();

			pst = c.prepareStatement(Conf.getInstance().getProperty("TSUBSTITUTIONS_FINDBYSPAREPARTID"));
			pst.setString(1, id);

			list = RecordAssembler.toSubstitutionRecordList(pst.executeQuery());
			return list;

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			Jdbc.close(pst);
		}
	}

}
