package uo.ri.cws.application.business.sparepart.crud.commands;

import java.sql.SQLException;
import java.util.List;

import uo.ri.cws.application.business.BusinessException;
import uo.ri.cws.application.business.sparepart.SparePartDto;
import uo.ri.cws.application.business.substitution.SubstitutionDto;
import uo.ri.cws.application.business.supply.SupplyDto;
import uo.ri.cws.application.business.util.DtoMapper;
import uo.ri.cws.application.business.util.command.Command;
import uo.ri.cws.application.persistence.PersistenceFactory;
import uo.ri.cws.application.persistence.intervention.InterventionGateway;
import uo.ri.cws.application.persistence.sparepart.SparePartGateway;
import uo.ri.cws.application.persistence.substitution.SubstitutionGateway;
import uo.ri.cws.application.persistence.supply.SupplyGateway;
import uo.ri.cws.application.persistence.workorder.WorkOrderGateway;
import uo.ri.cws.application.persistence.workorder.WorkOrderRecord;

/**
 * Comando de logica de ejecucion del metodo actualizar repuesto
 * 
 * @author aitor
 *
 */
public class UpdateSparePart implements Command<Void> {
	private SparePartDto dto;

	public UpdateSparePart(SparePartDto dto) {
		this.dto = dto;
	}

	@Override
	public Void execute() throws BusinessException, SQLException {
		SparePartGateway spg = PersistenceFactory.forSparePart();
		SupplyGateway sg = PersistenceFactory.forSupply();

		if (dto.code == null || dto.code.isEmpty())
			throw new BusinessException("[Update Sparepart] The code must have a value");
		if (!spg.findByCode(dto.code).isPresent())
			throw new BusinessException("[Update Sparepart] There isnt any sparepart with that code " + dto.code);
		if (dto.maxStock < dto.minStock)
			throw new BusinessException("[Update Sparepart] The maxStock must be higher than the minStock");
		if (dto.stock < 0)
			throw new BusinessException("[Update Sparepart] The stock must be higher than 0");
		if (dto.minStock < 0)
			throw new BusinessException("[Update Sparepart] The minStock must be higher than 0");
		if (dto.maxStock < 0)
			throw new BusinessException("[Update Sparepart] The maxStock must be higher than 0");
		if (dto.price < 0)
			throw new BusinessException("[Update Sparepart] The price must be higher than 0");
		List<SupplyDto> list = DtoMapper.toDtoListSupplyDto(sg.findBySparePartId(dto.id));
		// Actualizamos los precios en supply
		list.forEach(s -> s.price = dto.price);

		for (SupplyDto s : list)
			sg.update(DtoMapper.toRecord(s));

		// Actualizamos el precio de las workorder que tengan este repuesto
		WorkOrderGateway wg = PersistenceFactory.forWorkOrder();
		InterventionGateway ig  = PersistenceFactory.forIntervention();
		SubstitutionGateway subg = PersistenceFactory.forSubstitution();
		List<SubstitutionDto> sdto  = DtoMapper.toDtoListSubstitutionDto(subg.findBySparePart(dto.id));
		for(SubstitutionDto s: sdto) {
			double previousPrice = s.quantity * spg.findByCode(dto.code).get().price; //cantidad * anterior precio
			double actualPrice = s.quantity * dto.price;
			WorkOrderRecord w = wg.findById(ig.findById(s.intervention.id).get().workorderId).get();
			w.total  -= previousPrice;
			w.total += actualPrice;
			wg.update(w);
		}
		
		spg.update(DtoMapper.toRecord(dto));
		return null;
	}

}
