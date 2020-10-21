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
import uo.ri.cws.application.persistence.provider.ProviderGateway;
import uo.ri.cws.application.persistence.sparepart.SparePartGateway;
import uo.ri.cws.application.persistence.supply.SupplyGateway;

public class GenerateOrders implements Command<List<OrderDto>> {

	@Override
	public List<OrderDto> execute() throws BusinessException, SQLException {
		OrderGateway og = PersistenceFactory.forOrders();
		SparePartGateway spg = PersistenceFactory.forSparePart();
		SupplyGateway sg = PersistenceFactory.forSupply();

		List<SparePartReportDto> listUnderStock = DtoMapper.toDtoListSparePart(spg.findUnderStock());
		List<SupplyDto> sdtos = new ArrayList<SupplyDto>();

		// For every sparepart under stock we check for all the providers that supply it
		listUnderStock.forEach(c -> {
			if (!sg.findBySparePartId(c.id).isEmpty())
				sdtos.addAll(DtoMapper.toDtoListSupplyDto(sg.findBySparePartId(c.id)));
		});

		// Assign to the provider
		List<OrderRecord> ordersToGenerate = new ArrayList<OrderRecord>();

		for (SparePartReportDto sprd : listUnderStock) {
			OrderDto odto = new OrderDto();
			odto.id = UUID.randomUUID().toString();
			odto.code = UUID.randomUUID().toString(); // TODO: ASI DE AUTOGENERADO???
			odto.orderedDate = LocalDate.now();

			// Asign the sparepart
			OrderLineDto old = new OrderLineDto();
			old.sparePart = DtoMapper.toOrderedSpare(sprd);
			old.price = 0; // TODO: SACAR PRECIO DEL PROVEEDOR
			old.quantity = sprd.maxStock - sprd.stock; // Las necesarias para llenar el stock
			odto.lines.add(old);
			if (selectProvider(sdtos, sprd.id) != null) { // Si tiene proveedor se añade sino no
				odto.provider = DtoMapper.toOrderProvider(selectProvider(sdtos, sprd.id));
				ordersToGenerate.add(DtoMapper.toRecord(odto));
			}
			System.out.println("ORDERS: " + odto.toString());
		}

		// Check there isnt a orderline with this sparepart that is in order 2
		return DtoMapper.toDtoListOrderDto(og.generateOrders(ordersToGenerate));
	}

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

		//Obtenemos los nifs para compararlos
		ProviderGateway pg = PersistenceFactory.forProvider();

		List<ProviderDto> providers = new ArrayList<ProviderDto>();
		lowerSupplyDeliveryTerm.forEach(c -> {
			try {
				providers.add(DtoMapper.toDto(pg.findById(c.provider.id).get()));
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}
		});
		//Los asignamos a los supply
		for (ProviderDto p : providers) {
			for (SupplyDto s : lowerSupplyDeliveryTerm) {
				if (s.provider.id == p.id) {
					s.provider.nif = p.nif;
				}
			}
		}

		return lowerSupplyDeliveryTerm.stream().min(Comparator.comparing(dto -> dto.provider.nif))
				.map(dto -> dto.provider).get();

	}

}
