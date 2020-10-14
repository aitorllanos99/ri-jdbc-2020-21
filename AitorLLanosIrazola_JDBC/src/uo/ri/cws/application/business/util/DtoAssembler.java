package uo.ri.cws.application.business.util;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import uo.ri.cws.application.business.mechanic.MechanicDto;

public class DtoAssembler {

	public static MechanicDto toMechanicDto(ResultSet m) throws SQLException {
		MechanicDto dto = new MechanicDto();
		dto.id = m.getString(1);
		//dto.version = m.getVersion();

		dto.dni = m.getString(2);
		dto.name = m.getString(3);
		dto.surname = m.getString(4);
		return dto;
	}

	public static List<MechanicDto> toMechanicDtoList(ResultSet rs) throws SQLException {
		List<MechanicDto> res = new ArrayList<>();
		while(rs.next()) {
			res.add( toMechanicDto( rs ) );
		}

		return res;
	}
	
//	public static InvoicingWorkOrderDto toWorkOrderForInvoicingDto(ResultSet rs) throws SQLException {
//		InvoicingWorkOrderDto dto = new InvoicingWorkOrderDto();
//
//		dto.id = rs.getString("id");
//		dto.description = rs.getString("Description");
//		dto.date = LocalDate.ofInstant(rs.getDate("date").toInstant(), ZoneId.systemDefault());
//		dto.total = rs.getDouble("amount");
//		dto.status = rs.getString("status");
//
//		return dto;
//	}


}
