package uo.ri.cws.application.business.order.crud.commands;

import java.sql.SQLException;
import java.util.List;

import uo.ri.cws.application.business.BusinessException;
import uo.ri.cws.application.business.order.OrderDto;
import uo.ri.cws.application.business.util.DtoMapper;
import uo.ri.cws.application.business.util.command.Command;
import uo.ri.cws.application.persistence.PersistenceFactory;
import uo.ri.cws.application.persistence.order.OrderGateway;

/**
 * Comando de logica de la busqueda de proveedor por el nif del proveedor
 * 
 * @author aitor
 *
 */
public class FindByProviderNif implements Command<List<OrderDto>> {

	private String nif;

	public FindByProviderNif(String nif) {
		this.nif = nif;
	}

	@Override
	public List<OrderDto> execute() throws BusinessException, SQLException {
		OrderGateway og = PersistenceFactory.forOrders();
		List<OrderDto> list = DtoMapper.toDtoListOrderDto(og.findByProviderNif(nif));
		if (list == null || list.isEmpty())
			throw new BusinessException("[Order - Find By Provider Nif] There is no orders with that nif");
		list.forEach(c -> c.provider.nif = nif);
		return list;
	}

}
