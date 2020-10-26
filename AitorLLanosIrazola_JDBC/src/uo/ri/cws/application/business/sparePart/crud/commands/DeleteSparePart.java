package uo.ri.cws.application.business.sparepart.crud.commands;

import java.sql.SQLException;

import uo.ri.cws.application.business.BusinessException;
import uo.ri.cws.application.business.util.command.Command;
import uo.ri.cws.application.persistence.PersistenceFactory;
import uo.ri.cws.application.persistence.orderline.OrderLineGateway;
import uo.ri.cws.application.persistence.sparepart.SparePartGateway;
import uo.ri.cws.application.persistence.substitution.SubstitutionGateway;
/**
 * Comando de ejecucion de la logica de borrar repuesto
 * @author aitor
 *
 */
public class DeleteSparePart implements Command<Void> {

	private String code;

	public DeleteSparePart(String code) {
		this.code = code;
	}

	@Override
	public Void execute() throws BusinessException, SQLException {
		SparePartGateway spg = PersistenceFactory.forSparePart();
		OrderLineGateway olg = PersistenceFactory.forOrderLine();
		SubstitutionGateway ig = PersistenceFactory.forSubstitution();

		if (code == null || code.isEmpty())
			throw new BusinessException("[Delete Sparepart] The code must have a value");
		if (!spg.findByCode(code).isPresent())
			throw new BusinessException("[Delete Sparepart] There isnt any sparepart with that code " + code);

		if (!ig.findBySparePart(spg.findByCode(code).get().id).isEmpty())
			throw new BusinessException("[Delete Sparepart] This sparepart has substitutions actives");

		if (olg.findBySparePartId(spg.findByCode(code).get().id).isPresent())
			throw new BusinessException("[Delete Sparepart] This sparepart has orders actives");
		spg.remove(code);
		return null;
	}

}
