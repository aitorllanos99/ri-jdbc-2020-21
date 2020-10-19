package uo.ri.cws.application.business.sparePart.crud.commands;

import java.sql.SQLException;

import uo.ri.cws.application.business.BusinessException;
import uo.ri.cws.application.business.sparepart.SparePartDto;
import uo.ri.cws.application.business.util.DtoMapper;
import uo.ri.cws.application.business.util.command.Command;
import uo.ri.cws.application.persistence.PersistenceFactory;
import uo.ri.cws.application.persistence.sparepart.SparePartGateway;

public class UpdateSparePart implements Command<Void> {
	private SparePartDto dto;

	public UpdateSparePart(SparePartDto dto) {
		this.dto = dto;
	}

	@Override
	public Void execute() throws BusinessException, SQLException {
		SparePartGateway spg = PersistenceFactory.forSparePart();
		if (dto.code == null || dto.code.isEmpty())
			throw new BusinessException("[Update Sparepart] The code must have a value");
		if (!spg.findByCode(dto.code).isPresent())
			throw new BusinessException("[Update Sparepart] There isnt any sparepart with that code " + dto.code);
		// TODO: modificación del precio de una pieza afectará a todas las facturaciones
		// que se produzcan con posterioridad.
		spg.update(DtoMapper.toRecord(dto));
		return null;
	}

}
