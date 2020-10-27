package uo.ri.cws.application.business.sparepart.report.commands;

import java.sql.SQLException;
import java.util.List;

import uo.ri.cws.application.business.BusinessException;
import uo.ri.cws.application.business.sparepart.SparePartReportDto;
import uo.ri.cws.application.business.util.DtoMapper;
import uo.ri.cws.application.business.util.command.Command;
import uo.ri.cws.application.persistence.PersistenceFactory;
import uo.ri.cws.application.persistence.orderline.OrderLineGateway;
import uo.ri.cws.application.persistence.sparepart.SparePartGateway;
/**
 * Comando de logica de la busqueda por descripcion
 * @author aitor
 *
 */
public class FindByDescription implements Command<List<SparePartReportDto>> {
	private String description;

	public FindByDescription(String description) {
		this.description = description;
	}

	@Override
	public List<SparePartReportDto> execute() throws BusinessException, SQLException {
		if (description == null || description.isEmpty() || description.isBlank())
			throw new IllegalArgumentException("[Update Sparepart] The description must have a value");
		SparePartGateway spg = PersistenceFactory.forSparePart();
		OrderLineGateway olg = PersistenceFactory.forOrderLine();
		List<SparePartReportDto> list = DtoMapper.toDtoListSparePart(spg.findByDescritpion(description));
		list.forEach(c -> {c.totalUnitsSold =  !olg.findBySparePartId(c.id).isEmpty() ? olg.findBySparePartId(c.id).get(0).quantity : 0;});
		return list;
	}

}
