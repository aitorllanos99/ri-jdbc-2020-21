package uo.ri.cws.application.business.sparePart.crud.commands;

import java.sql.SQLException;

import uo.ri.cws.application.business.BusinessException;
import uo.ri.cws.application.business.sparePart.SparePartDto;
import uo.ri.cws.application.business.util.command.Command;

public class AddSparePart implements Command<String> {
	private SparePartDto dto;
	
	public AddSparePart(SparePartDto dto) {
		this.dto = dto;
	}
	@Override
	public String execute() throws BusinessException, SQLException {
		//Obligatorios todos los campos
		
		return null;
	}

}
