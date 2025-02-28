package uo.ri.cws.application.business.order.crud;

import java.util.List;
import java.util.Optional;

import uo.ri.cws.application.business.BusinessException;
import uo.ri.cws.application.business.order.OrderDto;
import uo.ri.cws.application.business.order.OrdersService;
import uo.ri.cws.application.business.order.crud.commands.FindByCode;
import uo.ri.cws.application.business.order.crud.commands.FindByProviderNif;
import uo.ri.cws.application.business.order.crud.commands.GenerateOrders;
import uo.ri.cws.application.business.util.command.CommandExecutor;

/**
 * Capa de servicio de los pedidos
 * 
 * @author aitor
 *
 */
public class OrdersServiceImpl implements OrdersService {
	private CommandExecutor executor = new CommandExecutor();

	/**
	 * Capa de servicio de la generacion de pedidos
	 */
	@Override
	public List<OrderDto> generateOrders() throws BusinessException {
		return executor.execute(new GenerateOrders());
	}

	/**
	 * Capa de servicio de la busqueda de pedidos por el nif del proveedor
	 */
	@Override
	public List<OrderDto> findByProviderNif(String nif) throws BusinessException {
		return executor.execute(new FindByProviderNif(nif));
	}

	/**
	 * Capa de servicio de la busqueda de pedidos por su codigo
	 */
	@Override
	public Optional<OrderDto> findByCode(String code) throws BusinessException {
		return executor.execute(new FindByCode(code));
	}

	@Override
	public OrderDto receive(String code) throws BusinessException {
		return null;
	}

}
