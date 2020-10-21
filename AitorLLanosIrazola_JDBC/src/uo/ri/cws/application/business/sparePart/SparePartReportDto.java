package uo.ri.cws.application.business.sparepart;

public class SparePartReportDto {
    public String id;
    public long version;

    public String code;
    public String description;
    public double price;
    public int stock;
    public int minStock;
    public int maxStock;
    public int totalUnitsSold;
	@Override
	public String toString() {
		return "SparePartReportDto [id=" + id + ", version=" + version + ", code=" + code + ", description="
				+ description + ", price=" + price + ", stock=" + stock + ", minStock=" + minStock + ", maxStock="
				+ maxStock + ", totalUnitsSold=" + totalUnitsSold + "]";
	}
    
    
}
