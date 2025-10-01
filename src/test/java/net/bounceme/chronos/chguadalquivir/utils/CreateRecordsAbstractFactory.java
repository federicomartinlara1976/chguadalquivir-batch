package net.bounceme.chronos.chguadalquivir.utils;

import net.bounceme.chronos.chguadalquivir.model.RegistroDiarioEmbalse;

public abstract class CreateRecordsAbstractFactory {

	public abstract RegistroDiarioEmbalse createRegistroDiarioEmbalse(String codigo,
			String embalse,
			Float capacidad,
			Float men);
}
