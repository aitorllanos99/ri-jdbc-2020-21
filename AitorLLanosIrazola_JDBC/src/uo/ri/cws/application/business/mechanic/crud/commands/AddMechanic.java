package uo.ri.cws.application.business.mechanic.crud.commands;

import java.sql.SQLException;
import java.util.UUID;

import uo.ri.cws.application.business.mechanic.MechanicDto;
import uo.ri.cws.application.business.util.DtoMapper;
import uo.ri.cws.application.business.util.command.Command;
import uo.ri.cws.application.persistence.PersistenceFactory;
import uo.ri.cws.application.persistence.mechanic.MechanicGateway;
/**
 * Comando de logica de añadir mecanico
 * @author aitor
 *
 */
public class AddMechanic implements Command<MechanicDto> {

	private MechanicDto dto;

	public AddMechanic(MechanicDto dto) {
		this.dto = dto;
	}

	public MechanicDto execute() throws SQLException {
		MechanicGateway mg = PersistenceFactory.forMechanic();
		dto.id = UUID.randomUUID().toString();
		if (dto.dni.isEmpty() || dto.dni == null)
			throw new IllegalArgumentException("[Add Mechanic] Problem with the dni field");
		mg.add(DtoMapper.toRecord(dto));

		return dto;
	}

}
