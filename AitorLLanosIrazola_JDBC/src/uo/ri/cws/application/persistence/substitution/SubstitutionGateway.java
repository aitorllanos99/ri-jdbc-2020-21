package uo.ri.cws.application.persistence.substitution;

import java.util.List;

import uo.ri.cws.application.persistence.Gateway;

public interface SubstitutionGateway extends Gateway<SubstitutionRecord> {
	
	/**
	 * 
	 * @param id the sparepartId 
	 * @return a list with the substitutuion with that sparepar
	 */
	List<SubstitutionRecord> findBySparePart(String id);

}
