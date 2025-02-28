package uo.ri.cws.application.business.sparepart;

import java.util.List;
import java.util.Optional;

import uo.ri.cws.application.business.BusinessException;

/**
 * This service is intended to be used by the Manager It follows the ISP
 * principle (@see SOLID principles from RC Martin)
 */
public interface SparePartReportService {

    /**
     * Note: this operation is quite similar to
     * 
     * @see SparePartCrudService.findByCode, but this one returns
     * @see SparePartReportDto instead of
     * @see SparePartDto (that).
     * @param spare part code
     * @return the spare part identified by the code or Optional.empty() if does not
     *         exist
     * @throws BusinessException DOES NOT
     */
    Optional<SparePartReportDto> findByCode(String code) throws BusinessException;

    /**
     * Returns a list (might be empty) of all the spare whose description matches
     * the pattern %<description>%. Thus, description may be a partial descriptipn
     * 
     * @param a (partial) description of the spare part
     * @return a list with spare part reports or empty if there is no one
     * @throws BusinessException DOES NOT
     */
    List<SparePartReportDto> findByDescription(String description) throws BusinessException;

    /**
     * @return a list, probably empty, with spare part reports for all the spares
     *         whose stock is < min stock
     * @throws BusinessException DOES NOT
     */
    List<SparePartReportDto> findUnderStock() throws BusinessException;

    /**
     * Counts how many units have been received in the last 60 days.
     * 
     * @param a spare part code
     * @return a SparePartUnitsReceivedDto or Optional.empty() if does not exist the
     *         part
     * @throws BusinessException DOES NOT
     */
    Optional<SparePartUnitsReceivedDto> partUnitsReceived(String partCode) throws BusinessException;

    public class SparePartUnitsReceivedDto {
	public String code; // part code
	public int unitsReceived; // number of units received in the last 30 days
    }

   
}
