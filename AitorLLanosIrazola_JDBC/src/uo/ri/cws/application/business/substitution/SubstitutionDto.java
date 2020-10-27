package uo.ri.cws.application.business.substitution;

public class SubstitutionDto {

	public String id;
	public int quantity;
	public SubstitutionIntervention intervention;
	public SubstitutionSpare sparepart;

	
	public class SubstitutionIntervention{
		public String id;
		public int minutes;
	}
	
	public class SubstitutionSpare{
		public String id;
		public String code;
	}
}
