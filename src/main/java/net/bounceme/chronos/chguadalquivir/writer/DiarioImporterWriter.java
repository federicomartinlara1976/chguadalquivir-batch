package net.bounceme.chronos.chguadalquivir.writer;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import net.bounceme.chronos.chguadalquivir.model.Embalse;
import net.bounceme.chronos.chguadalquivir.model.RegistroDiarioEmbalse;
import net.bounceme.chronos.chguadalquivir.repository.RegistroDiarioEmbalseRepository;
import net.bounceme.chronos.chguadalquivir.repository.RepositoryCollectionCustom;
import net.bounceme.chronos.dto.MessageDTO;
import net.bounceme.chronos.dto.chguadalquivir.RegistroDiarioDTO;

@Component
@Slf4j
public class DiarioImporterWriter implements ItemWriter<Embalse> {
	
	@Value("${application.queue}")
	private String queueName;
	
	private RabbitTemplate rabbitTemplate;
	
	private RegistroDiarioEmbalseRepository registroDiarioEmbalseRepository;
	
	private RepositoryCollectionCustom repositoryCollectionCustom;
	
	private SimpleDateFormat dateFormat;

    public DiarioImporterWriter(RabbitTemplate rabbitTemplate,
			RegistroDiarioEmbalseRepository registroDiarioEmbalseRepository,
			RepositoryCollectionCustom repositoryCollectionCustom, SimpleDateFormat dateFormat) {
		this.rabbitTemplate = rabbitTemplate;
		this.registroDiarioEmbalseRepository = registroDiarioEmbalseRepository;
		this.repositoryCollectionCustom = repositoryCollectionCustom;
		this.dateFormat = dateFormat;
	}



	@Override
    @SuppressWarnings("rawtypes")
    public synchronized void write(Chunk<? extends Embalse> items) throws Exception {
        for (Embalse embalse : items) {
        	repositoryCollectionCustom.setCollectionName(embalse.getId());
        	
        	List<RegistroDiarioEmbalse> registros = registroDiarioEmbalseRepository.findAll();
            
        	for (RegistroDiarioEmbalse registro : registros) {
        		
        		Date fecha = dateFormat.parse(registro.getId());
        		
        		RegistroDiarioDTO registroDiarioDTO = RegistroDiarioDTO.builder()
            			.codigoEmbalse(embalse.getId())
            			.porcentaje(registro.getPorcentaje())
            			.volumen(registro.getVolumen())
            			.nivel(registro.getNivel())
            			.fecha(fecha)
            			.build();
        		
				MessageDTO messageDTO = MessageDTO.builder()
        				.className(RegistroDiarioDTO.class.getName())
        				.data(registroDiarioDTO)
        				.build();
        		
        		rabbitTemplate.convertAndSend(queueName, messageDTO);
        		log.info("Writed {}", registroDiarioDTO.toString());
        	}
        }
    }

}
