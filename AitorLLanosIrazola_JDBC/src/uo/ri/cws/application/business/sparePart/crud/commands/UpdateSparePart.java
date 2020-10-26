package uo.ri.cws.application.business.sparepart.crud.commands;

import java.sql.SQLException;
import java.util.List;

import uo.ri.cws.application.business.BusinessException;
import uo.ri.cws.application.business.sparepart.SparePartDto;
import uo.ri.cws.application.business.supply.SupplyDto;
import uo.ri.cws.application.business.util.DtoMapper;
import uo.ri.cws.application.business.util.command.Command;
import uo.ri.cws.application.persistence.PersistenceFactory;
import uo.ri.cws.application.persistence.sparepart.SparePartGateway;
import uo.ri.cws.application.persistence.supply.SupplyGateway;

public class UpdateSparePart implements Command<Void> {
	private SparePartDto dto;

	public UpdateSparePart(SparePartDto dto) {
		this.dto = dto;
	}

	@Override
	public Void execute() throws BusinessException, SQLException {
		SparePartGateway spg = PersistenceFactory.forSparePart();
		SupplyGateway sg  = PersistenceFactory.forSupply();
		
		
		if (dto.code == null || dto.code.isEmpty())
			throw new BusinessException("[Update Sparepart] The code must have a value");
		if (!spg.findByCode(dto.code).isPresent())
			throw new BusinessException("[Update Sparepart] There isnt any sparepart with that code " + dto.code);
		// TODO: modificación del precio de una pieza afectará a todas las facturaciones
		// que se produzcan con posterioridad.
		List <SupplyDto> list = DtoMapper.toDtoListSupplyDto(sg.findBySparePartId(dto.id));
		//Actualizamos los precios en supply
		list.forEach(s -> s.price = dto.price);
		
		for(SupplyDto s :list)
			sg.update(DtoMapper.toRecord(s));
		spg.update(DtoMapper.toRecord(dto));
		return null;
	}

}
