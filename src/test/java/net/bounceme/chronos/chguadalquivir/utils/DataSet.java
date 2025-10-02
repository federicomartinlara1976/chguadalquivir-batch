package net.bounceme.chronos.chguadalquivir.utils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jakarta.validation.ConstraintViolation;
import lombok.Getter;
import net.bounceme.chronos.chguadalquivir.model.RegistroDiarioEmbalse;

public class DataSet {

	private CreateRecordsAbstractFactory createRecordsFactory;
	
	@Getter
	private List<RegistroDiarioEmbalse> registros;
	
	@Getter
	private Set<ConstraintViolation<RegistroDiarioEmbalse>> constraintViolations;
	
	public DataSet() {
		createRecordsFactory = new CreateRecordsTestFactory();
		
		initDataSet();
	}
	
	private void initDataSet() {
		createRegistros();
		createViolations();
	}
	
	private void createViolations() {
		constraintViolations = new HashSet<>();
		constraintViolations.add(createRecordsFactory.createViolation());
		constraintViolations.add(createRecordsFactory.createViolation());
		constraintViolations.add(createRecordsFactory.createViolation());
	}

	private void createRegistros() {
		RegistroDiarioEmbalse registro1 = createRecordsFactory.createRegistroDiarioEmbalse("EMB123", 
				"Embalse de Prueba", 
				1500.5f, 
				750.2f);
		
		RegistroDiarioEmbalse registro2 = createRecordsFactory.createRegistroDiarioEmbalse("EMB000", 
				"Embalse Cero", 
				0.0f, 
				0.0f);
		
		RegistroDiarioEmbalse registro3 = createRecordsFactory.createRegistroDiarioEmbalse("EMB_PREC", 
				"Embalse Precisión", 
				1234.5678f, 
				987.6543f);
		
		RegistroDiarioEmbalse registro4 = createRecordsFactory.createRegistroDiarioEmbalse("EMB_NULL_1", 
				"Embalse Null 1", 
				null, 
				-50.0f);
		
		RegistroDiarioEmbalse registro5 = createRecordsFactory.createRegistroDiarioEmbalse("EMB_NULL_2", 
				"Embalse Null 2", 
				100.0f, 
				null);
		
		RegistroDiarioEmbalse registro6 = createRecordsFactory.createRegistroDiarioEmbalse("EMB_NEG_1", 
				"Embalse Negativo 1", 
				-100.0f, 
				50.0f);
		
		RegistroDiarioEmbalse registro7 = createRecordsFactory.createRegistroDiarioEmbalse("EMB_NEG_1", 
				"Embalse Negativo 2", 
				100.0f, 
				-50.0f);
		
		registros = new ArrayList<>();
		registros.add(registro1);
		registros.add(registro2);
		registros.add(registro3);
		registros.add(registro4);
		registros.add(registro5);
		registros.add(registro6);
		registros.add(registro7);
	}
	
	public RegistroDiarioEmbalse emptyRegistroDiarioEmbalse(boolean emptyStrings) {
		RegistroDiarioEmbalse registro = new RegistroDiarioEmbalse();
		
		if (emptyStrings) {
			registro.setCodigo("");
			registro.setEmbalse("");
			registro.setCapacidad(0.0f);
			registro.setMen(0.0f);
		}
		
		return registro;
	}
	
	public RegistroDiarioEmbalse nullRegistroDiarioEmbalse() {
		return null;
	}
	
	public Set<ConstraintViolation<RegistroDiarioEmbalse>> emptyConstraintViolations() {
		return new HashSet<>();
	}
	
	public Set<ConstraintViolation<RegistroDiarioEmbalse>> nullConstraintViolations() {
		return null;
	}
}
