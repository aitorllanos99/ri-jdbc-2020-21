package uo.ri.cws.application.business.mechanic.crud.commands;

import java.sql.SQLException;

import uo.ri.cws.application.business.mechanic.MechanicDto;
import uo.ri.cws.application.business.util.DtoMapper;
import uo.ri.cws.application.business.util.command.Command;
import uo.ri.cws.application.persistence.PersistenceFactory;
import uo.ri.cws.application.persistence.mechanic.MechanicGateway;

public class UpdateMechanic implements Command<Void> {
	private MechanicDto dto;

	public UpdateMechanic(MechanicDto dto) {
		this.dto = dto;
	}

	public Void execute() throws SQLException {
		MechanicGateway mg = PersistenceFactory.forMechanic();

		if (dto.dni.isEmpty() || dto.dni == null)
			throw new IllegalArgumentException("[Update Mechanic] Problem with the dni field");
		if (dto.id.isEmpty() || dto.id == null)
			throw new IllegalArgumentException("[Update Mechanic] Problem with the id field");
		if (dto.name.isEmpty() || dto.name == null)
			throw new IllegalArgumentException("[Update Mechanic] Problem with the name field");
		if (dto.surname.isEmpty() || dto.surname == null)
			throw new IllegalArgumentException("[Update Mechanic] Problem with the surname field");
		if (!mg.findByDni(dto.dni).isPresent())
			throw new IllegalArgumentException("[Update Mechanic] The mechanic doesnt exist");
		mg.update(DtoMapper.toRecord(dto));

		return null;
	}

}
