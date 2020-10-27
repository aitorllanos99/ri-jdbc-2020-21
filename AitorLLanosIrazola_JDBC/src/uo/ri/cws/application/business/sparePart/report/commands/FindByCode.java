package uo.ri.cws.application.business.sparepart.report.commands;

import java.sql.SQLException;
import java.util.Optional;

import uo.ri.cws.application.business.BusinessException;
import uo.ri.cws.application.business.sparepart.SparePartReportDto;
import uo.ri.cws.application.business.util.DtoMapper;
import uo.ri.cws.application.business.util.command.Command;
import uo.ri.cws.application.persistence.PersistenceFactory;
import uo.ri.cws.application.persistence.orderline.OrderLineGateway;
import uo.ri.cws.application.persistence.sparepart.SparePartGateway;

/**
 * Comando de logica de la busqueda por codigo en los reports
 * @author aitor
 *
 */
public class FindByCode implements Command<Optional<SparePartReportDto>> {
	private String code;

	public FindByCode(String code) {
		this.code = code;
	}

	@Override
	public Optional<SparePartReportDto> execute() throws BusinessException, SQLException {
		SparePartGateway spg = PersistenceFactory.forSparePart();
		OrderLineGateway olg = PersistenceFactory.forOrderLine();
		if (code == null || code.isEmpty() || code.isBlank())
			throw new IllegalArgumentException("[Find By Code Sparepart] The code must have a value");
		if(spg.findByCode(code).isEmpty())
				throw new IllegalArgumentException("[Find By Code Sparepart] The code doesnt belong to a real sparepart");
		SparePartReportDto dto = DtoMapper.toDtoSparePartRecord(spg.findByCode(code)).get();
		dto.totalUnitsSold = !olg.findBySparePartId(dto.id).isEmpty() ? olg.findBySparePartId(dto.id).stream().mapToInt(o -> o.quantity).reduce((o1,o2) -> o1+o2).getAsInt(): 0;
		return Optional.of(dto);
	}
}
