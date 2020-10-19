package uo.ri.cws.application.business.sparepart.crud.commands;

import java.sql.SQLException;

import uo.ri.cws.application.business.BusinessException;
import uo.ri.cws.application.business.util.command.Command;
import uo.ri.cws.application.persistence.PersistenceFactory;
import uo.ri.cws.application.persistence.sparepart.SparePartGateway;

public class DeleteSparePart implements Command<Void> {

	private String code;
	public DeleteSparePart(String code) {
		this.code = code;
	}
	@Override
	public Void execute() throws BusinessException, SQLException {
		SparePartGateway spg = PersistenceFactory.forSparePart();
		//OrderGateway og = PersistenceFactory.forOrder();
		if(code == null || code.isEmpty())
			throw new BusinessException("[Delete Sparepart] The code must have a value");
		if(!spg.findByCode(code).isPresent())
			throw new BusinessException("[Delete Sparepart] There isnt any sparepart with that code " + code);
		//TODO: Check if there is in any substitution
	
		//TODO: Check if there is in any order
		//if(og.findBySparePart(code))
		spg.remove(code);
		return null;
	}

}
