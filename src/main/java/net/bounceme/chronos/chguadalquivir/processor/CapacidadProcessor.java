package net.bounceme.chronos.chguadalquivir.processor;

import java.util.Objects;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import net.bounceme.chronos.chguadalquivir.model.Embalse;
import net.bounceme.chronos.chguadalquivir.model.RegistroDiarioEmbalse;

@Component
public class CapacidadProcessor implements ItemProcessor<RegistroDiarioEmbalse, Embalse> {

	@Override
	public Embalse process(RegistroDiarioEmbalse item) {
		Assert.notNull(item, "El registro no puede ser nulo");
		Assert.isTrue(!Objects.isNull(item.getCapacidad()) && item.getCapacidad() >= 0.0f,
				"La capacidad no puede ser nula o negativa");
		Assert.isTrue(!Objects.isNull(item.getMen()) && item.getMen() >= 0.0f,
				"MEN no puede ser nulo o negativo");

		return Embalse.builder().id(item.getCodigo()).nombre(item.getEmbalse()).capacidad(item.getCapacidad())
				.men(item.getMen()).build();
	}

}
