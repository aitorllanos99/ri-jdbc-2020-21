package uo.ri.cws.application.business.sparepart.crud.commands;

import java.sql.SQLException;
import java.util.Optional;

import uo.ri.cws.application.business.BusinessException;
import uo.ri.cws.application.business.sparepart.SparePartDto;
import uo.ri.cws.application.business.util.DtoMapper;
import uo.ri.cws.application.business.util.command.Command;
import uo.ri.cws.application.persistence.PersistenceFactory;
import uo.ri.cws.application.persistence.sparepart.SparePartGateway;

/**
 * Logica del comando Buscar por codigo repuesto
 * @author aitor
 *
 */
public class FindByCode implements Command<Optional<SparePartDto>> {
	private String code;

	public FindByCode(String code) {
		this.code = code;
	}

	@Override
	public Optional<SparePartDto> execute() throws BusinessException, SQLException {
		SparePartGateway spg = PersistenceFactory.forSparePart();
		if (code == null || code.isEmpty() || code.isBlank())
			throw new IllegalArgumentException("[Find By Code Sparepart] The code must have a value");
		
		return DtoMapper.toDtoSparePartDto(spg.findByCode(code));
	}

}
