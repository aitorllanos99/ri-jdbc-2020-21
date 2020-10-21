package uo.ri.cws.application.business.order;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class OrderDto {
    public String id;
    public long version;

    public String code;
    public LocalDate orderedDate;
    public LocalDate receptionDate;
    public double amount;
    public String status;

    public OrderedProviderDto provider = new OrderedProviderDto();
    public List<OrderLineDto> lines = new ArrayList<>();

    public static class OrderLineDto {
	public OrderedSpareDto sparePart = new OrderedSpareDto();
	public double price;
	public int quantity;
    }

    public static class OrderedSpareDto {
	public String id;
	public String code;
	public String description;
	@Override
	public String toString() {
		return "OrderedSpareDto [id=" + id + ", code=" + code + ", description=" + description + "]";
	}
	
    }

    public static class OrderedProviderDto {
	public String id;
	public String nif;
	public String name;
	@Override
	public String toString() {
		return "OrderedProviderDto [id=" + id + ", nif=" + nif + ", name=" + name + "]";
	}
	
    }

	@Override
	public String toString() {
		return "OrderDto [id=" + id + ", version=" + version + ", code=" + code + ", orderedDate=" + orderedDate
				+ ", receptionDate=" + receptionDate + ", amount=" + amount + ", status=" + status + ", provider="
				+ provider + ", lines=" + lines + "]";
	}
    
    

}
