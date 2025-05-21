package net.bounceme.chronos.chguadalquivir.writer;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import net.bounceme.chronos.chguadalquivir.model.Zona;
import net.bounceme.chronos.dto.MessageDTO;
import net.bounceme.chronos.dto.chguadalquivir.ZonaDTO;

@Component
@Slf4j
public class ZonasImporterWriter implements ItemWriter<Zona> {
	
	@Value("${application.queue}")
	private String queueName;
	
	@Autowired
	private RabbitTemplate rabbitTemplate;

    @SuppressWarnings("rawtypes")
	@Override
    public synchronized void write(Chunk<? extends Zona> items) throws Exception {
        for (Zona zona : items) {
        	ZonaDTO zonaDTO = ZonaDTO.builder()
        			.codigo(zona.getCodigo())
        			.nombre(zona.getNombre())
        			.descripcion(zona.getDescripcion())
        			.build();
    		
			MessageDTO messageDTO = MessageDTO.builder()
    				.className(ZonaDTO.class.getName())
    				.data(zonaDTO)
    				.build();
    		
    		rabbitTemplate.convertAndSend(queueName, messageDTO);
    		log.info("Writed {}", zonaDTO.toString());
        }
    }

}
