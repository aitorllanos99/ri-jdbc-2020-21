package uo.ri.cws.application.business.order.crud.commands;

import java.sql.SQLException;
import java.util.Optional;

import uo.ri.cws.application.business.BusinessException;
import uo.ri.cws.application.business.order.OrderDto;
import uo.ri.cws.application.business.order.OrderDto.OrderLineDto;
import uo.ri.cws.application.business.provider.ProviderDto;
import uo.ri.cws.application.business.sparepart.SparePartReportDto;
import uo.ri.cws.application.business.util.DtoMapper;
import uo.ri.cws.application.business.util.command.Command;
import uo.ri.cws.application.persistence.PersistenceFactory;
import uo.ri.cws.application.persistence.order.OrderGateway;
import uo.ri.cws.application.persistence.orderline.OrderLineGateway;
import uo.ri.cws.application.persistence.provider.ProviderGateway;
import uo.ri.cws.application.persistence.sparepart.SparePartGateway;
/**
 * Comando de logica de la busqueda de pedidos por el codigo
 * @author aitor
 *
 */
public class FindByCode implements Command<Optional<OrderDto>> {

	private String code;

	public FindByCode(String code) {
		this.code = code;
	}

	@Override
	public Optional<OrderDto> execute() throws BusinessException, SQLException {
		OrderGateway og = PersistenceFactory.forOrders();
		ProviderGateway pg = PersistenceFactory.forProvider();
		OrderLineGateway olg = PersistenceFactory.forOrderLine();
		SparePartGateway spg = PersistenceFactory.forSparePart();
		if (code == null || code.isEmpty() || code.isBlank())
			throw new IllegalArgumentException("[Find By Code Order] The code must have a value");
		
		OrderDto dto = DtoMapper.toDto(og.findByCode(code).get());
		ProviderDto pdto = DtoMapper.toDto(pg.findById(dto.provider.id).get());
		OrderLineDto old = DtoMapper.toDto(olg.findByOrderId(dto.id).get());
		
		SparePartReportDto spd = DtoMapper.toDto(spg.findById(old.sparePart.id).get());
		
		dto.provider.name = pdto.name;
		dto.provider.nif = pdto.nif;
		old.sparePart.code = spd.code;
		old.sparePart.description = spd.description;
		dto.lines.add(old);
		return Optional.of(dto);
	}

}
