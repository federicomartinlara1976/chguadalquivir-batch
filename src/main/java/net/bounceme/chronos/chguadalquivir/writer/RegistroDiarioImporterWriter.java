package net.bounceme.chronos.chguadalquivir.writer;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import net.bounceme.chronos.chguadalquivir.model.RegistroDiarioEmbalse;
import net.bounceme.chronos.chguadalquivir.repository.RegistroDiarioEmbalseRepository;
import net.bounceme.chronos.chguadalquivir.repository.RepositoryCollectionCustom;
import net.bounceme.chronos.dto.MessageDTO;
import net.bounceme.chronos.dto.chguadalquivir.RegistroDiarioDTO;

@Component
@Slf4j
public class RegistroDiarioImporterWriter implements ItemWriter<RegistroDiarioEmbalse> {
	
	@Value("${application.queue}")
	private String queueName;
	
	@Autowired
	private RabbitTemplate rabbitTemplate;
	
	@Autowired
	private RegistroDiarioEmbalseRepository registroDiarioEmbalseRepository;
	
	@Autowired
	private RepositoryCollectionCustom repositoryCollectionCustom;
	
	@Autowired
	private SimpleDateFormat dateFormat;

    @Override
    public synchronized void write(Chunk<? extends RegistroDiarioEmbalse> items) throws Exception {
        for (RegistroDiarioEmbalse e : items) {
            repositoryCollectionCustom.setCollectionName(e.getCodigo());
            
            Optional<RegistroDiarioEmbalse> oRegistroDiario = registroDiarioEmbalseRepository.findById(dateFormat.format(e.getFecha()));
            if (!oRegistroDiario.isPresent()) {
            	writeRegistroDiario(e);
            }
        }
    }

	@SuppressWarnings("rawtypes")
	private void writeRegistroDiario(RegistroDiarioEmbalse e) throws Exception {
		// Set id
    	e.setId(dateFormat.format(e.getFecha()));
    	registroDiarioEmbalseRepository.save(e);
    	log.info("Writed {}", e.toString());
    	
    	Date fecha = dateFormat.parse(e.getId());
    	
    	RegistroDiarioDTO registroDiarioDTO = RegistroDiarioDTO.builder()
    			.codigoEmbalse(e.getCodigo())
    			.porcentaje(e.getPorcentaje())
    			.volumen(e.getVolumen())
    			.nivel(e.getNivel())
    			.fecha(fecha)
    			.build();
		
		MessageDTO messageDTO = MessageDTO.builder()
				.className(RegistroDiarioDTO.class.getName())
				.data(registroDiarioDTO)
				.build();
		
		rabbitTemplate.convertAndSend(queueName, messageDTO);
	}

}
