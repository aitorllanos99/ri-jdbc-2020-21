package uo.ri.cws.application.business.util;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import uo.ri.cws.application.business.invoice.InvoiceDto;
import uo.ri.cws.application.business.mechanic.MechanicDto;
import uo.ri.cws.application.business.sparePart.SparePartDto;
import uo.ri.cws.application.business.sparePart.SparePartReportDto;
import uo.ri.cws.application.persistence.invoice.InvoiceRecord;
import uo.ri.cws.application.persistence.mechanic.MechanicRecord;
import uo.ri.cws.application.persistence.sparepart.SparePartRecord;

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
	public static List<SparePartReportDto> toDtoListSparePart(List<SparePartRecord> arg) {
		List<SparePartReportDto> result = new ArrayList<SparePartReportDto>();
		for (SparePartRecord mr : arg)
			result.add(toDto(mr.id, mr.code, mr.description, mr.stock, mr.maxStock, mr.minStock, mr.price));
		return result;
	}

}
