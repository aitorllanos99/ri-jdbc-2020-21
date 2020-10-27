package uo.ri.cws.application.business.sparepart.crud.commands;

import java.sql.SQLException;
import java.util.UUID;

import uo.ri.cws.application.business.BusinessException;
import uo.ri.cws.application.business.sparepart.SparePartDto;
import uo.ri.cws.application.business.util.DtoMapper;
import uo.ri.cws.application.business.util.command.Command;
import uo.ri.cws.application.persistence.PersistenceFactory;
import uo.ri.cws.application.persistence.sparepart.SparePartGateway;

/**
 * Comando de logica de ejecucion del metodo añadir repuesto
 * 
 * @author aitor
 *
 */
public class AddSparePart implements Command<String> {
	private SparePartDto dto;

	public AddSparePart(SparePartDto dto) {
		this.dto = dto;
	}

	@Override
	public String execute() throws BusinessException, SQLException {
		SparePartGateway spg = PersistenceFactory.forSparePart();
		dto.id = UUID.randomUUID().toString();
		// Obligatorios todos los campos y codigo no repetido
		if (dto.code == null || dto.code.isEmpty() || dto.code.isBlank())
			throw new BusinessException("[Add SparePart] The code must have a value");
		if (spg.findByCode(dto.code).isPresent())
			throw new BusinessException("[Add SparePart] There is a sparepart with this code " + dto.code);
		if (dto.description == null || dto.description.isEmpty() || dto.code.isBlank())
			throw new BusinessException("[Add SparePart] The description must have a value");
		if (dto.maxStock < dto.minStock)
			throw new BusinessException("[Add SparePart] The maxStock must be higher than the minStock");
		if (dto.stock < 0)
			throw new BusinessException("[Add SparePart] The stock must be higher than 0");
		if (dto.minStock < 0)
			throw new BusinessException("[Add SparePart] The minStock must be higher than 0");
		if (dto.maxStock < 0)
			throw new BusinessException("[Add SparePart] The maxStock must be higher than 0");
		if (dto.price < 0)
			throw new BusinessException("[Add SparePart] The price must be higher than 0");
		spg.add(DtoMapper.toRecord(dto));

		return dto.id;
	}

}
