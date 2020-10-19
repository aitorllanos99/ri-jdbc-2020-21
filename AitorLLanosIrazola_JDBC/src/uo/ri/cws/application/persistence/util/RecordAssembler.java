package uo.ri.cws.application.persistence.util;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import uo.ri.cws.application.persistence.intervention.InterventionRecord;
import uo.ri.cws.application.persistence.invoice.InvoiceRecord;
import uo.ri.cws.application.persistence.mechanic.MechanicRecord;
import uo.ri.cws.application.persistence.sparepart.SparePartRecord;
import uo.ri.cws.application.persistence.workorder.WorkOrderRecord;

public class RecordAssembler {


	public static MechanicRecord toMechanicRecord(ResultSet m) throws SQLException {
		MechanicRecord dto = new MechanicRecord();
		dto.id = m.getString("id");

		dto.dni = m.getString("dni");
		dto.name = m.getString("name");
		dto.surname = m.getString("surname");
		return dto;
	}
	

	public static List<MechanicRecord> toMechanicRecordList(ResultSet rs) throws SQLException {
		List<MechanicRecord> res = new ArrayList<>();
		while(rs.next()) {
			res.add( toMechanicRecord( rs ) );
		}

		return res;
	}
	
	public static InvoiceRecord toInvoiceRecord(ResultSet rs) throws SQLException {
		InvoiceRecord result = new InvoiceRecord();
		
		result.id = rs.getString("id");
		result.version = rs.getLong("version");
		result.date = rs.getDate("date");
		result.number = rs.getLong("number");
		result.status = rs.getString("status");
		result.vat = rs.getDouble("vat");
		
		return result;
	}


	public static WorkOrderRecord toWorkOrderRecord ( ResultSet rs ) throws SQLException {
		WorkOrderRecord result = new WorkOrderRecord();
		
		result.id = rs.getString("id");
		result.vehicleId = rs.getString( "vehicle_Id");
		result.description = rs.getString( "description");
		result.date = rs.getDate( "date");
		result.total = rs.getLong("amount");
		result.status = rs.getString( "status");

		// might be null
		result.mechanicId = rs.getString( "mechanic_Id");
		result.invoiceId = rs.getString( "invoice_Id");
		
		
		return result;
		
	}

	public static List<WorkOrderRecord> toWorkOrderRecordList(ResultSet rs) throws SQLException {
		List<WorkOrderRecord> res = new ArrayList<>();
		while(rs.next()) {
			res.add( toWorkOrderRecord( rs ) );
		}
		return res;
	}


	public static InterventionRecord toInterventionRecord(ResultSet rs) throws SQLException {
		InterventionRecord result = new InterventionRecord();
		result.id = rs.getString("id");
		result.mechanicId = rs.getString("mechanic_id");
		result.workorderId = rs.getString("workorder_id");
		result.date = rs.getDate("date");
		result.minutes = rs.getInt("minutes");
		return result;
		
	}
	
	public static SparePartRecord toSparePartRecord( ResultSet rs ) throws SQLException {
		SparePartRecord result = new SparePartRecord();
		
		result.id = rs.getString("id");
		result.code = rs.getString( "code");
		result.description = rs.getString( "description");
		result.maxStock = rs.getInt( "maxStock");
		result.minStock = rs.getInt("minStock");
		result.price = rs.getInt( "price");
		result.stock = rs.getInt("stock");
		return result;
	}

	public static List<SparePartRecord> toSparePartRecordList(ResultSet rs) throws SQLException {
		List<SparePartRecord> res = new ArrayList<>();
		while(rs.next()) {
			res.add(toSparePartRecord( rs ) );
		}
		return res;
	}

}
