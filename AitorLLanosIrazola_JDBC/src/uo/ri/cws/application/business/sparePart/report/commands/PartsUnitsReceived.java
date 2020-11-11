package uo.ri.cws.application.business.sparepart.report.commands;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import uo.ri.cws.application.business.BusinessException;
import uo.ri.cws.application.business.order.OrderDto;
import uo.ri.cws.application.business.sparepart.SparePartReportService.SparePartUnitsReceivedDto;
import uo.ri.cws.application.business.util.DtoMapper;
import uo.ri.cws.application.business.util.command.Command;
import uo.ri.cws.application.persistence.PersistenceFactory;
import uo.ri.cws.application.persistence.order.OrderGateway;
import uo.ri.cws.application.persistence.orderline.OrderLineGateway;
import uo.ri.cws.application.persistence.orderline.OrderLineRecord;
import uo.ri.cws.application.persistence.sparepart.SparePartGateway;

public class PartsUnitsReceived implements Command<Optional<SparePartUnitsReceivedDto>> {
	
	private String code;
	
	public PartsUnitsReceived(String code) {
	this.code = code;
	}

	@Override
	public Optional<SparePartUnitsReceivedDto> execute() throws BusinessException, SQLException {
		SparePartUnitsReceivedDto returnSparePart = new SparePartUnitsReceivedDto();
		returnSparePart.code = code;
		SparePartGateway spg = PersistenceFactory.forSparePart();
		OrderLineGateway olg = PersistenceFactory.forOrderLine();
		OrderGateway og = PersistenceFactory.forOrders();
		
		//Comprobaciones
		if (code == null || code.isEmpty() || code.isBlank())
			throw new IllegalArgumentException("[PartsUnitsReceived Sparepart] The code must have a value");
		if(spg.findByCode(code).isEmpty())
				throw new IllegalArgumentException("[PartsUnitsReceived Sparepart] The code doesnt belong to a real sparepart");
		
		List<OrderLineRecord> dtos = olg.findBySparePartId(spg.findByCode(code).get().id);
		if(dtos.isEmpty()) //The sparepart has no orders
			return Optional.empty();
		
		
		for(OrderLineRecord oldto: dtos) {
			OrderDto orderDto = DtoMapper.toDto(og.findById(oldto.order_id).get());
			if(orderDto.status.contentEquals("RECEIVED")) { //Si fue recibido
				LocalDate prueba = LocalDate.now(); //dia de hoy 
				if(orderDto.receptionDate.isAfter(prueba.minusDays(60)))  // si tiene menos de 60 dias
					returnSparePart.unitsReceived += oldto.quantity;
			}
		}
		return Optional.of(returnSparePart);
	}

}
