package uo.ri.cws.application.business.sparePart.crud;

import java.util.Optional;

import uo.ri.cws.application.business.BusinessException;
import uo.ri.cws.application.business.sparePart.SparePartCrudService;
import uo.ri.cws.application.business.sparePart.SparePartDto;
import uo.ri.cws.application.business.sparePart.crud.commands.AddSparePart;
import uo.ri.cws.application.business.util.command.CommandExecutor;

public class SparePartCrudServiceImpl implements SparePartCrudService {
	private CommandExecutor executor = new CommandExecutor();
	
	@Override
	public String add(SparePartDto dto) throws BusinessException {
		return executor.execute(new AddSparePart(dto));
	}

	@Override
	public void delete(String code) throws BusinessException {
		// TODO Auto-generated method stub

	}

	@Override
	public void update(SparePartDto dto) throws BusinessException {
		// TODO Auto-generated method stub

	}

	@Override
	public Optional<SparePartDto> findByCode(String code) throws BusinessException {
		// TODO Auto-generated method stub
		return null;
	}

}
