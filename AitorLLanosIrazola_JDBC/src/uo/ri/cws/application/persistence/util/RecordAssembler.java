package uo.ri.cws.application.persistence.util;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import uo.ri.cws.application.persistence.intervention.InterventionRecord;
import uo.ri.cws.application.persistence.invoice.InvoiceRecord;
import uo.ri.cws.application.persistence.mechanic.MechanicRecord;
import uo.ri.cws.application.persistence.order.OrderRecord;
import uo.ri.cws.application.persistence.orderline.OrderLineRecord;
import uo.ri.cws.application.persistence.provider.ProviderRecord;
import uo.ri.cws.application.persistence.sparepart.SparePartRecord;
import uo.ri.cws.application.persistence.substitution.SubstitutionRecord;
import uo.ri.cws.application.persistence.supply.SupplyRecord;
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
		while (rs.next()) {
			res.add(toMechanicRecord(rs));
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

	public static WorkOrderRecord toWorkOrderRecord(ResultSet rs) throws SQLException {
		WorkOrderRecord result = new WorkOrderRecord();

		result.id = rs.getString("id");
		result.vehicleId = rs.getString("vehicle_Id");
		result.description = rs.getString("description");
		result.date = rs.getDate("date");
		result.total = rs.getLong("amount");
		result.status = rs.getString("status");

		// might be null
		result.mechanicId = rs.getString("mechanic_Id");
		result.invoiceId = rs.getString("invoice_Id");

		return result;

	}

	public static List<WorkOrderRecord> toWorkOrderRecordList(ResultSet rs) throws SQLException {
		List<WorkOrderRecord> res = new ArrayList<>();
		while (rs.next()) {
			res.add(toWorkOrderRecord(rs));
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

	public static SparePartRecord toSparePartRecord(ResultSet rs) throws SQLException {
		SparePartRecord result = new SparePartRecord();

		result.id = rs.getString("id");
		result.code = rs.getString("code");
		result.description = rs.getString("description");
		result.maxStock = rs.getInt("maxStock");
		result.minStock = rs.getInt("minStock");
		result.price = rs.getDouble("price");
		result.stock = rs.getInt("stock");
		return result;
	}

	public static List<SparePartRecord> toSparePartRecordList(ResultSet rs) throws SQLException {
		List<SparePartRecord> res = new ArrayList<>();
		while (rs.next()) {
			res.add(toSparePartRecord(rs));
		}
		return res;
	}

	public static OrderLineRecord toOrderLineRecord(ResultSet rs) throws SQLException {
		OrderLineRecord result = new OrderLineRecord();
		result.order_id = rs.getString("ORDER_ID");
		result.sparePart_id = rs.getString("SPAREPART_ID");
		result.price = rs.getLong("price");
		result.quantity = rs.getInt("quantity");
		return result;
	}
	

	public static List<OrderLineRecord> toOrderLineRecordList(ResultSet rs) throws SQLException {
		List<OrderLineRecord> res = new ArrayList<>();
		while (rs.next()) {
			res.add(toOrderLineRecord(rs));
		}
		return res;
	}

	public static SupplyRecord toSupplyRecord(ResultSet rs) throws SQLException {
		SupplyRecord result = new SupplyRecord();
		result.id = rs.getString("id");
		result.deliveryTerm = rs.getInt("deliveryTerm");
		result.price = rs.getDouble("price");
		result.providerId = rs.getString("provider_id");
		result.sparePartId = rs.getString("sparepart_id");
		return result;
	}

	public static List<SupplyRecord> toSupplyRecordList(ResultSet rs) throws SQLException {
		List<SupplyRecord> res = new ArrayList<>();
		while (rs.next()) {
			res.add(toSupplyRecord(rs));
		}
		return res;
	}

	public static ProviderRecord toProviderRecord(ResultSet rs) throws SQLException {
		ProviderRecord result = new ProviderRecord();
		result.id = rs.getString("id");
		result.email = rs.getString("email");
		result.name = rs.getString("name");
		result.nif = rs.getString("nif");
		result.phone = rs.getString("phone");
		return result;
	}

	public static OrderRecord toOrderRecord(ResultSet rs) throws SQLException {
		OrderRecord result = new OrderRecord();
		result.id = rs.getString("id");
		result.code = rs.getString("code");
		result.amount = rs.getDouble("amount");
		result.providerId = rs.getString("provider_id");
		result.orderedDate = rs.getDate("orderedDate").toLocalDate();
		if(rs.getDate("receptionDate") != null)
			result.receptionDate = rs.getDate("receptionDate").toLocalDate();
		result.status = rs.getString("status");
		return result;
	}
	

	public static List<OrderRecord> toOrderRecordList(ResultSet rs) throws SQLException {
		List<OrderRecord> res = new ArrayList<>();
		while (rs.next()) {
			res.add(toOrderRecord(rs));
		}
		return res;
	}
	
	public static SubstitutionRecord toSubstitutionRecord(ResultSet rs) throws SQLException {
		SubstitutionRecord result  =  new SubstitutionRecord();
		result.id = rs.getString("id");
		result.quantity = rs.getInt("quantity");
		result.interventionId = rs.getString("intervention_id");
		result.sparepartId = rs.getString("sparepart_id");
		return result;
	}

	

	public static List<SubstitutionRecord> toSubstitutionRecordList(ResultSet rs) throws SQLException {
		List<SubstitutionRecord> res = new ArrayList<SubstitutionRecord>();
		while (rs.next()) {
			res.add(toSubstitutionRecord(rs));
		}
		return res;
	}
}
