package uo.ri.cws.application.business.order.crud.commands;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import uo.ri.cws.application.business.BusinessException;
import uo.ri.cws.application.business.order.OrderDto;
import uo.ri.cws.application.business.order.OrderDto.OrderLineDto;
import uo.ri.cws.application.business.provider.ProviderDto;
import uo.ri.cws.application.business.sparepart.SparePartReportDto;
import uo.ri.cws.application.business.supply.SupplyDto;
import uo.ri.cws.application.business.supply.SupplyDto.SupplierProviderDto;
import uo.ri.cws.application.business.util.DtoMapper;
import uo.ri.cws.application.business.util.command.Command;
import uo.ri.cws.application.persistence.PersistenceFactory;
import uo.ri.cws.application.persistence.order.OrderGateway;
import uo.ri.cws.application.persistence.order.OrderRecord;
import uo.ri.cws.application.persistence.orderline.OrderLineGateway;
import uo.ri.cws.application.persistence.orderline.OrderLineRecord;
import uo.ri.cws.application.persistence.provider.ProviderGateway;
import uo.ri.cws.application.persistence.sparepart.SparePartGateway;
import uo.ri.cws.application.persistence.supply.SupplyGateway;

/**
 * Comando de ejecucion de logica de la generacion de pedidos
 * 
 * @author aitor
 *
 */
public class GenerateOrders implements Command<List<OrderDto>> {

	@Override
	public List<OrderDto> execute() throws BusinessException, SQLException {
		OrderGateway og = PersistenceFactory.forOrders();
		OrderLineGateway olg = PersistenceFactory.forOrderLine();
		SparePartGateway spg = PersistenceFactory.forSparePart();
		SupplyGateway sg = PersistenceFactory.forSupply();
		ProviderGateway pg = PersistenceFactory.forProvider();

		List<SparePartReportDto> listUnderStock = DtoMapper.toDtoListSparePart(spg.findUnderStock());
		List<SupplyDto> sdtos = new ArrayList<SupplyDto>();

		// For every sparepart under stock we check for all the providers that supply it
		listUnderStock.forEach(c -> {
			if (!sg.findBySparePartId(c.id).isEmpty())
				sdtos.addAll(DtoMapper.toDtoListSupplyDto(sg.findBySparePartId(c.id)));
		});

		// Assign to the provider
		List<OrderRecord> ordersToGenerate = new ArrayList<OrderRecord>();
		List<OrderLineRecord> ordersLinesToGenerate = new ArrayList<OrderLineRecord>();
		List<OrderDto> ordersToPrint = new ArrayList<OrderDto>();
		for (SparePartReportDto sprd : listUnderStock) {
			// Check there isnt a orderline with this sparepart that is in order 2
			List<OrderLineRecord> idToCheck = olg.findBySparePartId(sprd.id);
			if (!idToCheck.isEmpty()) {
				List<OrderRecord> ordersInRecieved = new ArrayList<OrderRecord>();
				for(OrderLineRecord o: idToCheck)
					if(!og.findById(o.order_id).get().status.contentEquals("PENDING")) //Con que haya una en pending no se hace el pedido
						ordersInRecieved.add(og.findById(o.order_id).get());
					else {
						ordersInRecieved.clear();	
						break;
					}
				
				if (!ordersInRecieved.isEmpty()) {
					OrderDto odto = new OrderDto();
					odto.id = UUID.randomUUID().toString();
					odto.code = UUID.randomUUID().toString();
					odto.orderedDate = LocalDate.now();
					odto.receptionDate = LocalDate.MAX;
					odto.status = "PENDING";

					// Asign the sparepart
					OrderLineDto old = new OrderLineDto();
					old.sparePart = DtoMapper.toOrderedSpare(sprd);
					old.quantity = sprd.maxStock - sprd.stock; // Las necesarias para llenar el stock

					odto.lines.add(old);
					if (selectProvider(sdtos, sprd.id) != null) { // Si tiene proveedor se añade sino no
						odto.provider = DtoMapper.toOrderProvider(selectProvider(sdtos, sprd.id));
						old.price = sdtos.stream().filter(c -> c.provider.id.equals(odto.provider.id))
								.collect(Collectors.toList()).get(0).price;
						odto.amount = old.price * old.quantity;
						ordersToGenerate.add(DtoMapper.toRecord(odto));
						ordersToPrint.add(odto);
						// Para las orderlines que tambien hay que generar
						OrderLineRecord or = DtoMapper.toRecord(old);
						or.order_id = odto.id;
						ordersLinesToGenerate.add(or);
					}
				}
			} else {
				OrderDto odto = new OrderDto();
				odto.id = UUID.randomUUID().toString();
				odto.code = UUID.randomUUID().toString();
				odto.orderedDate = LocalDate.now();
				odto.receptionDate = LocalDate.MAX;
				odto.status = "PENDING";

				// Asign the sparepart
				OrderLineDto old = new OrderLineDto();
				old.sparePart = DtoMapper.toOrderedSpare(sprd);

				old.quantity = sprd.maxStock - sprd.stock; // Las necesarias para llenar el stock
	
				odto.lines.add(old);
				if (selectProvider(sdtos, sprd.id) != null) { // Si tiene proveedor se añade sino no
					odto.provider = DtoMapper.toOrderProvider(selectProvider(sdtos, sprd.id));
					old.price = sdtos.stream().filter(c -> c.provider.id.equals(odto.provider.id))
							.collect(Collectors.toList()).get(0).price;
					odto.amount = old.price * old.quantity;
					ordersToPrint.add(odto);
					ordersToGenerate.add(DtoMapper.toRecord(odto));
					// Para las orderlines que tambien hay que generar
					OrderLineRecord or = DtoMapper.toRecord(old);
					or.order_id = odto.id;
					ordersLinesToGenerate.add(or);
				}
			}
		}

		// Generate ordeline and order
		for (OrderRecord d : ordersToGenerate)
			og.add(d);
		for (OrderLineRecord o : ordersLinesToGenerate)
			olg.add(o);

		ordersToPrint.forEach(o -> {
			try {
				o.provider.nif = pg.findById(o.provider.id).get().nif;
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}
		});
		return ordersToPrint;
	}

	/**
	 * Metodo auxiliar para buscar el mejor proveedor dado el enunciado de la
	 * ampliacion
	 * 
	 * @param dtos,        la lista con los proveedores
	 * @param idSparePart, el identificador de la pieza a buscar proveedor
	 * @return el mejor proveedor por los criterios de la ampliacion
	 * @throws SQLException si ocurre algun problema en la persistencia lo
	 *                      propagamos
	 */
	private SupplierProviderDto selectProvider(List<SupplyDto> dtos, String idSparePart) throws SQLException {

		// Filter for sparepart
		dtos = dtos.stream().filter(c -> c.sparePart.id.equals(idSparePart)).collect(Collectors.toList());

		if (dtos.isEmpty()) // No hay proveedor para este repuesto
			return null;

		// First select from prices
		// we get the lowest price
		double lowerPrice = dtos.stream().min(Comparator.comparingDouble(dto -> dto.price)).get().price;
		// We get the ones with that price
		List<SupplyDto> lowerSupplyPrice = dtos.stream().filter(d -> d.price == lowerPrice)
				.collect(Collectors.toList());
		// If there is just one we returns it
		if (lowerSupplyPrice.size() == 1)
			return lowerSupplyPrice.get(0).provider;

		// Else we will check the lowest deliveryTerm
		// We get the lower deliveryTerm
		double lowerDeliveryTerm = lowerSupplyPrice.stream().min(Comparator.comparingDouble(dto -> dto.deliveryTerm))
				.get().deliveryTerm;
		// We get the ones with that delivery term
		List<SupplyDto> lowerSupplyDeliveryTerm = dtos.stream().filter(d -> d.deliveryTerm == lowerDeliveryTerm)
				.collect(Collectors.toList());
		if (lowerSupplyDeliveryTerm.size() == 1)
			return lowerSupplyDeliveryTerm.get(0).provider;

		// Comparacion por nifs
		ProviderGateway pg = PersistenceFactory.forProvider();
		// Sacamos los nifs por si hay que compararlos y para mostrarlos posteriormente
		List<ProviderDto> providers = new ArrayList<ProviderDto>();
		dtos.forEach(c -> {
			try {
				providers.add(DtoMapper.toDto(pg.findById(c.provider.id).get()));
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}
		});
		// Los asignamos a los supply
		for (ProviderDto p : providers) {
			for (SupplyDto s : dtos) {
				if (s.provider.id == p.id) {
					s.provider.nif = p.nif;
				}
			}
		}

		return lowerSupplyDeliveryTerm.stream().min(Comparator.comparing(dto -> dto.provider.nif))
				.map(dto -> dto.provider).get();

	}

}
