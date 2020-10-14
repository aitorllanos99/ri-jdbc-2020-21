package uo.ri.cws.application.business.sparePart.crud.commands;

import java.sql.SQLException;
import java.util.UUID;

import uo.ri.cws.application.business.BusinessException;
import uo.ri.cws.application.business.sparePart.SparePartDto;
import uo.ri.cws.application.business.util.DtoMapper;
import uo.ri.cws.application.business.util.command.Command;
import uo.ri.cws.application.persistence.PersistenceFactory;
import uo.ri.cws.application.persistence.sparepart.SparePartGateway;

public class AddSparePart implements Command<String> {
	private SparePartDto dto;
	
	public AddSparePart(SparePartDto dto) {
		this.dto = dto;
	}
	@Override
	public String execute() throws BusinessException, SQLException {
		SparePartGateway spg = PersistenceFactory.forSparePart();
		dto.id = UUID.randomUUID().toString();
		//Obligatorios todos los campos y codigo no repetido
		if(dto.code == null || dto.code.isEmpty())
			throw new BusinessException("[Add SparePart] The code must have a value");
		if(spg.findByCode(dto.code).isPresent())
			throw new BusinessException("[Add SparePart] There is a sparepart with this code " + dto.code);
		if(dto.maxStock < dto.minStock)
			throw new BusinessException("[Add SparePart] The maxStock must be higher than the minStock");
		spg.add(DtoMapper.toRecord(dto));
		
		return dto.id;
	}

}
