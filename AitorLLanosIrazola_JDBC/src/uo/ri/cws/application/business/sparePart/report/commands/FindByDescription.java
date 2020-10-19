package uo.ri.cws.application.business.sparePart.report.commands;

import java.sql.SQLException;
import java.util.List;

import uo.ri.cws.application.business.BusinessException;
import uo.ri.cws.application.business.sparepart.SparePartReportDto;
import uo.ri.cws.application.business.util.DtoMapper;
import uo.ri.cws.application.business.util.command.Command;
import uo.ri.cws.application.persistence.PersistenceFactory;
import uo.ri.cws.application.persistence.sparepart.SparePartGateway;

public class FindByDescription implements Command<List<SparePartReportDto>> {
	private String description;

	public FindByDescription(String description) {
		this.description = description;
	}

	@Override
	public List<SparePartReportDto> execute() throws BusinessException, SQLException {
		SparePartGateway spg = PersistenceFactory.forSparePart();
		return DtoMapper.toDtoListSparePart(spg.findByDescritpion(description));
	}

}
