package uo.ri.cws.application.business.mechanic.crud;

import java.util.List;
import java.util.Optional;

import uo.ri.cws.application.business.BusinessException;
import uo.ri.cws.application.business.mechanic.MechanicCrudService;
import uo.ri.cws.application.business.mechanic.MechanicDto;
import uo.ri.cws.application.business.mechanic.crud.commands.AddMechanic;
import uo.ri.cws.application.business.mechanic.crud.commands.DeleteMechanic;
import uo.ri.cws.application.business.mechanic.crud.commands.FindAllMechanics;
import uo.ri.cws.application.business.mechanic.crud.commands.UpdateMechanic;
import uo.ri.cws.application.business.util.command.CommandExecutor;
/**
 * Capa de servicio de los mecanicos
 * @author aitor
 *
 */
public class MechanicCrudServiceImpl implements MechanicCrudService {
	private CommandExecutor executor = new CommandExecutor();
	/**
	 * Capa de servicio de añadir mecanico
	 */
	@Override
	public MechanicDto addMechanic(MechanicDto mechanic) throws BusinessException {
		return executor.execute(new AddMechanic(mechanic));
	}
/**
 * Capa de servicio de borrar mecanico
 */
	@Override
	public void deleteMechanic(String idMechanic) throws BusinessException {
		 executor.execute(new DeleteMechanic(idMechanic));
	}
/**
 * Capa de servicio de actualizar mecanico
 */
	@Override
	public void updateMechanic(MechanicDto mechanic) throws BusinessException {
		 executor.execute(new UpdateMechanic(mechanic));
	}

	@Override
	public Optional<MechanicDto> findMechanicById(String idMechanic) throws BusinessException {
		return null;
	}
/**
 * Capa de servicio de buscar todos los mecanicos
 */
	@Override
	public List<MechanicDto> findAllMechanics() throws BusinessException {
		return executor.execute(new FindAllMechanics());
	}

}
