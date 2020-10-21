package uo.ri.cws.application.business.util;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import uo.ri.cws.application.business.invoice.InvoiceDto;
import uo.ri.cws.application.business.mechanic.MechanicDto;
import uo.ri.cws.application.business.order.OrderDto;
import uo.ri.cws.application.business.order.OrderDto.OrderedProviderDto;
import uo.ri.cws.application.business.order.OrderDto.OrderedSpareDto;
import uo.ri.cws.application.business.sparepart.SparePartDto;
import uo.ri.cws.application.business.sparepart.SparePartReportDto;
import uo.ri.cws.application.business.supply.SupplyDto;
import uo.ri.cws.application.business.supply.SupplyDto.SupplierProviderDto;
import uo.ri.cws.application.persistence.invoice.InvoiceRecord;
import uo.ri.cws.application.persistence.mechanic.MechanicRecord;
import uo.ri.cws.application.persistence.order.OrderRecord;
import uo.ri.cws.application.persistence.sparepart.SparePartRecord;
import uo.ri.cws.application.persistence.supply.SupplyRecord;

public class DtoMapper {

	public static MechanicDto toDto(String id, String dni, String name, String surname) {
		MechanicDto result = new MechanicDto();
		result.id = id;
		result.name = name;
		result.surname = surname;
		result.dni = dni;
		return result;
	}

	public static Optional<MechanicDto> toDto(Optional<MechanicRecord> arg) {
		Optional<MechanicDto> result = arg.isEmpty() ? Optional.ofNullable(null)
				: Optional.ofNullable(toDto(arg.get().id, arg.get().dni, arg.get().name, arg.get().surname));
		return result;
	}

	public static List<MechanicDto> toDtoList(List<MechanicRecord> arg) {
		List<MechanicDto> result = new ArrayList<MechanicDto>();
		for (MechanicRecord mr : arg)
			result.add(toDto(mr.id, mr.dni, mr.name, mr.surname));
		return result;
	}

	public static MechanicRecord toRecord(MechanicDto arg) {
		MechanicRecord result = new MechanicRecord();
		result.id = arg.id;
		result.dni = arg.dni;
		result.name = arg.name;
		result.surname = arg.surname;
		return result;
	}

	public static InvoiceDto toDto(InvoiceRecord arg) {
		InvoiceDto result = new InvoiceDto();
		result.id = arg.id;
		result.number = arg.number;
		result.status = arg.status;
		result.date = LocalDate.ofInstant(arg.date.toInstant(), ZoneId.systemDefault());
		result.total = arg.total;
		result.vat = arg.vat;
		return result;
	}

	public static SparePartRecord toRecord(SparePartDto arg) {
		SparePartRecord result = new SparePartRecord();
		result.id = arg.id;
		result.code = arg.code;
		result.description = arg.description;
		result.stock = arg.stock;
		result.minStock = arg.minStock;
		result.maxStock = arg.maxStock;
		result.price = arg.price;
		return result;
	}
	public static SparePartReportDto toDto(SparePartRecord arg) {
		SparePartReportDto result = new SparePartReportDto();
		result.id = arg.id;
		result.code = arg.code;
		result.description = arg.description;
		result.stock = arg.stock;
		result.minStock = arg.minStock;
		result.maxStock = arg.maxStock;
		result.price = arg.price;
		return result;
	}
	
	public static SparePartReportDto toDto(String id, String code, String description, int stock, int maxStock, int minStock, double price) {
		SparePartReportDto result = new SparePartReportDto();
		result.id = id;
		result.code = code;
		result.description = description;
		result.stock = stock;
		result.minStock = minStock;
		result.maxStock = maxStock;
		result.price = price;
		return result;
	}
	
	public static SparePartDto toDtoSparePart(String id, String code, String description, int stock, int maxStock, int minStock, double price) {
		SparePartDto result = new SparePartDto();
		result.id = id;
		result.code = code;
		result.description = description;
		result.stock = stock;
		result.minStock = minStock;
		result.maxStock = maxStock;
		result.price = price;
		return result;
	}
	public static List<SparePartReportDto> toDtoListSparePart(List<SparePartRecord> arg) {
		List<SparePartReportDto> result = new ArrayList<SparePartReportDto>();
		for (SparePartRecord mr : arg)
			result.add(toDto(mr.id, mr.code, mr.description, mr.stock, mr.maxStock, mr.minStock, mr.price));
		return result;
	}
	
	public static Optional<SparePartReportDto> toDtoSparePartRecord(Optional<SparePartRecord> arg) {
		Optional<SparePartReportDto> result = arg.isEmpty() ? Optional.ofNullable(null)
				: Optional.ofNullable(toDto(arg.get().id, arg.get().code, arg.get().description, arg.get().stock, arg.get().maxStock, arg.get().minStock, arg.get().price));
		return result;
	}
	
	public static Optional<SparePartDto> toDtoSparePartDto(Optional<SparePartRecord> arg) {
		Optional<SparePartDto> result = arg.isEmpty() ? Optional.ofNullable(null)
				: Optional.ofNullable(toDtoSparePart(arg.get().id, arg.get().code, arg.get().description, arg.get().stock, arg.get().maxStock, arg.get().minStock, arg.get().price));
		return result;
	}
	
	public static OrderDto toDtoOrderDto(String id, String code, double amount, LocalDate orderedDate, LocalDate receptionDate, String status) {
		OrderDto result = new OrderDto();
		result.id = id;
		result.code = code;
		result.orderedDate = orderedDate;
		result.receptionDate = receptionDate;
		result.amount = amount;
		result.status = status;
		return result;
	}
	
	public static List<OrderDto> toDtoListOrderDto(List<OrderRecord> arg) {
		List<OrderDto> result = new ArrayList<OrderDto>();
		for (OrderRecord mr : arg)
			result.add(toDtoOrderDto(mr.id, mr.code, mr.amount, mr.orderedDate, mr.receptionDate, mr.status));
		return result;
	}
	public static OrderDto toDto(OrderRecord arg) {
		OrderDto result = new OrderDto();
		result.id = arg.id;
		result.code = arg.code;
		result.provider.id = arg.providerId;
		result.orderedDate = arg.orderedDate;
		result.receptionDate = arg.receptionDate;
		result.amount = arg.amount;
		result.status = arg.status;
		return result;
	}
	
	public static SupplyDto toDto(SupplyRecord arg) {
		SupplyDto result = new SupplyDto();
		result.id = arg.id;
		result.price  = arg.price;
		result.deliveryTerm = arg.deliveryTerm;
		result.provider.id = arg.providerId;
		result.sparePart.id = arg.sparePartId;
		return result;
	}
	
	public static List<SupplyDto> toDtoListSupplyDto(List<SupplyRecord> arg) {
		List<SupplyDto> result = new ArrayList<SupplyDto>();
		for (SupplyRecord mr : arg)
			result.add(toDto(mr));
		return result;
	}
	
	public static OrderedProviderDto toOrderProvider(SupplierProviderDto arg) {
		OrderedProviderDto result = new OrderedProviderDto();
		result.id = arg.id;
		result.name = arg.name;
		result.nif = arg.nif;
		return result;
	}
	
	public static OrderedSpareDto toOrderedSpare(SparePartReportDto arg) {
		OrderedSpareDto result  = new OrderedSpareDto();
		result.id = arg.id;
		result.code = arg.code;
		result.description = arg.description;
		return result;
	}
	
	public static OrderRecord toRecord(OrderDto arg) {
		OrderRecord result = new OrderRecord();
		result.id = arg.id;
		result.code = arg.code;
		result.providerId = arg.provider.id;
		result.orderedDate = arg.orderedDate;
		result.receptionDate = arg.receptionDate;
		result.amount = arg.amount;
		result.status = arg.status;
		return result;
	}
	

}
