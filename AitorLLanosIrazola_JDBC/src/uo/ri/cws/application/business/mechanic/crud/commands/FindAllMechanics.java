package uo.ri.cws.application.business.mechanic.crud.commands;

import java.sql.SQLException;
import java.util.List;

import uo.ri.cws.application.business.mechanic.MechanicDto;
import uo.ri.cws.application.business.util.DtoMapper;
import uo.ri.cws.application.business.util.command.Command;
import uo.ri.cws.application.persistence.PersistenceFactory;
import uo.ri.cws.application.persistence.mechanic.MechanicGateway;
import uo.ri.cws.application.persistence.mechanic.MechanicRecord;

public class FindAllMechanics implements Command<List<MechanicDto>> {

	public List<MechanicDto> execute() throws SQLException {
		MechanicGateway mg = PersistenceFactory.forMechanic();

		List<MechanicRecord> l = mg.findAll();
		return DtoMapper.toDtoList(l);

	}
}
