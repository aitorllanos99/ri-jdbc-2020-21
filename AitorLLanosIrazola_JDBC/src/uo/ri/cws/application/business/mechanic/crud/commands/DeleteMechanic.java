package uo.ri.cws.application.business.mechanic.crud.commands;

import java.sql.SQLException;

import uo.ri.cws.application.business.util.command.Command;
import uo.ri.cws.application.persistence.PersistenceFactory;
import uo.ri.cws.application.persistence.mechanic.MechanicGateway;

public class DeleteMechanic implements Command<Void> {

	private String id;

	public DeleteMechanic(String id) {
		this.id = id;
	}

	public Void execute() throws SQLException {
		MechanicGateway mg = PersistenceFactory.forMechanic();

		if (id.isEmpty() || id == null)
			throw new IllegalArgumentException("[Delete Mechanic] Problem with the id field");
		if (!mg.findById(id).isPresent())
			throw new IllegalArgumentException("[Delete Mechanic] The mechanic doesnt exist");
		mg.remove(id);

		return null;

	}

}
