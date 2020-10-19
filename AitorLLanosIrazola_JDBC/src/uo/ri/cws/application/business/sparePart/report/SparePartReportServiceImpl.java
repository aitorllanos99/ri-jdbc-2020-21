package uo.ri.cws.application.business.sparepart.report;

import java.util.List;
import java.util.Optional;

import uo.ri.cws.application.business.BusinessException;
import uo.ri.cws.application.business.sparepart.SparePartReportDto;
import uo.ri.cws.application.business.sparepart.SparePartReportService;
import uo.ri.cws.application.business.sparepart.report.commands.FindByDescription;
import uo.ri.cws.application.business.util.command.CommandExecutor;

public class SparePartReportServiceImpl implements SparePartReportService{
	private CommandExecutor executor = new CommandExecutor();
	@Override
	public Optional<SparePartReportDto> findByCode(String code) throws BusinessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<SparePartReportDto> findByDescription(String description) throws BusinessException {
		return executor.execute(new FindByDescription(description));
	}

	@Override
	public List<SparePartReportDto> findUnderStock() throws BusinessException {
		// TODO Auto-generated method stub
		return null;
	}

}
