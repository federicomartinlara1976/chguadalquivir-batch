package net.bounceme.chronos.chguadalquivir.reader;

import java.util.List;
import java.util.Optional;

import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemStreamSupport;
import org.springframework.beans.factory.annotation.Autowired;

import net.bounceme.chronos.chguadalquivir.model.Embalse;
import net.bounceme.chronos.chguadalquivir.model.RegistroDiarioEmbalse;
import net.bounceme.chronos.chguadalquivir.repository.EmbalseRepository;
import net.bounceme.chronos.chguadalquivir.repository.RegistroDiarioEmbalseRepository;
import net.bounceme.chronos.chguadalquivir.repository.RepositoryCollectionCustom;

public class EmbalsesItemReader extends ItemStreamSupport implements ItemReader<Embalse> {

	@Autowired
	private EmbalseRepository embalseRepository;
	
	@Autowired
	private RepositoryCollectionCustom repositoryCollectionCustom;
	
	@Autowired
	private RegistroDiarioEmbalseRepository registroDiarioEmbalseRepository;

	private List<Embalse> records;

	private Integer index = 0;

	@Override
	public void open(ExecutionContext executionContext) {
		initialize();
	}

	/**
	 * 
	 */
	private void initialize() {
		records = embalseRepository.findAll();

		// Por cada embalse, rellenar capacidad y men
		records.forEach(embalse -> {
			repositoryCollectionCustom.setCollectionName(embalse.getId());
            
            List<RegistroDiarioEmbalse> registros = registroDiarioEmbalseRepository.findAll();
            RegistroDiarioEmbalse registro = registros.get(0);
            embalse.setCapacidad(registro.getCapacidad());
            embalse.setMen(registro.getMEN());
		});
		
		index = 0;
	}

	/**
	 *
	 */
	@Override
	public Embalse read() {
		Embalse nextElement = null;

		if (index < records.size()) {
			nextElement = records.get(index);
			index++;
		} else {
			index = 0;
		}

		return nextElement;
	}

}
