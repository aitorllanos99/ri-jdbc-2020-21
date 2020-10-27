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
 * Comando de logica de la busqueda por existencias bajas
 * 
 * @author aitor
 *
 */
public class FindUnderStock implements Command<List<SparePartReportDto>> {

	@Override
	public List<SparePartReportDto> execute() throws BusinessException, SQLException {
		SparePartGateway spg = PersistenceFactory.forSparePart();
		OrderLineGateway olg = PersistenceFactory.forOrderLine();
		List<SparePartReportDto> list = DtoMapper.toDtoListSparePart(spg.findUnderStock());
		list.forEach(c -> {
			c.totalUnitsSold = !olg.findBySparePartId(c.id).isEmpty() ? olg.findBySparePartId(c.id).stream().mapToInt(o -> o.quantity).reduce((o1,o2) -> o1+o2).getAsInt(): 0;
		});
		return list;
	}

}
