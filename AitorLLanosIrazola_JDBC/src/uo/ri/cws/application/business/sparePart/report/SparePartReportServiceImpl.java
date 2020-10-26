package uo.ri.cws.application.business.sparepart.report;

import java.util.List;
import java.util.Optional;

import uo.ri.cws.application.business.BusinessException;
import uo.ri.cws.application.business.sparepart.SparePartReportDto;
import uo.ri.cws.application.business.sparepart.SparePartReportService;
import uo.ri.cws.application.business.sparepart.report.commands.FindByCode;
import uo.ri.cws.application.business.sparepart.report.commands.FindByDescription;
import uo.ri.cws.application.business.sparepart.report.commands.FindUnderStock;
import uo.ri.cws.application.business.util.command.CommandExecutor;

/**
 * Capa de servicio de las busquedas de repuesto
 * 
 * @author aitor
 *
 */
public class SparePartReportServiceImpl implements SparePartReportService {
	private CommandExecutor executor = new CommandExecutor();

	/**
	 * Capa de servicio de la busqueda por codigo
	 */
	@Override
	public Optional<SparePartReportDto> findByCode(String code) throws BusinessException {
		return executor.execute(new FindByCode(code));
	}

	/**
	 * Capa de servicio de la busqueda por descripcion
	 */
	@Override
	public List<SparePartReportDto> findByDescription(String description) throws BusinessException {
		return executor.execute(new FindByDescription(description));
	}

	/**
	 * Capa de servicio de la busqueda por existencias bajas
	 */
	@Override
	public List<SparePartReportDto> findUnderStock() throws BusinessException {
		return executor.execute(new FindUnderStock());
	}

}
