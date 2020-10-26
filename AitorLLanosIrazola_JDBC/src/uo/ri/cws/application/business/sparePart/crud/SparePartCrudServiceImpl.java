package uo.ri.cws.application.business.sparepart.crud;

import java.util.Optional;

import uo.ri.cws.application.business.BusinessException;
import uo.ri.cws.application.business.sparepart.crud.commands.FindByCode;
import uo.ri.cws.application.business.sparepart.SparePartDto;
import uo.ri.cws.application.business.sparepart.SparePartCrudService;
import uo.ri.cws.application.business.sparepart.crud.commands.AddSparePart;
import uo.ri.cws.application.business.sparepart.crud.commands.DeleteSparePart;
import uo.ri.cws.application.business.sparepart.crud.commands.UpdateSparePart;
import uo.ri.cws.application.business.util.command.CommandExecutor;


/**
 * Capa de servicio de las sparepart
 * @author aitor
 *
 */
public class SparePartCrudServiceImpl implements SparePartCrudService {
	private CommandExecutor executor = new CommandExecutor();
	/**
	 * Capa de servicio del metodo añadir repuesto
	 */
	@Override
	public String add(SparePartDto dto) throws BusinessException {
		return executor.execute(new AddSparePart(dto));
	}

	/**
	 * Capa de servicio del metodo borrar repuesto
	 */
	@Override
	public void delete(String code) throws BusinessException {
		 executor.execute(new DeleteSparePart(code));
	}

	/**
	 * Capa de servicio del metodo actualizar repuesto
	 */
	@Override
	public void update(SparePartDto dto) throws BusinessException {
		executor.execute(new UpdateSparePart(dto));
	}

	/**
	 * Capa de servicio del metodo buscar repuesto por codigo
	 */
	@Override
	public Optional<SparePartDto> findByCode(String code) throws BusinessException {
		return executor.execute(new FindByCode(code));
	}

}
