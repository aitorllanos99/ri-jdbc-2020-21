package uo.ri.cws.application.business.order.crud.commands;

import java.sql.SQLException;
import java.util.List;

import uo.ri.cws.application.business.BusinessException;
import uo.ri.cws.application.business.order.OrderDto;
import uo.ri.cws.application.business.util.DtoMapper;
import uo.ri.cws.application.business.util.command.Command;
import uo.ri.cws.application.persistence.PersistenceFactory;
import uo.ri.cws.application.persistence.order.OrderGateway;

public class GenerateOrders implements Command<List<OrderDto>> {

	@Override
	public List<OrderDto> execute() throws BusinessException, SQLException {
		OrderGateway og = PersistenceFactory.forOrders();
		return DtoMapper.toDtoListOrderDto(og.generateOrders());
	}

}
