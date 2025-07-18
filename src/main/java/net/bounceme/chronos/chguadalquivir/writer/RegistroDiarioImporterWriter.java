package net.bounceme.chronos.chguadalquivir.writer;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import net.bounceme.chronos.chguadalquivir.model.RegistroDiarioEmbalse;
import net.bounceme.chronos.chguadalquivir.repository.RegistroDiarioEmbalseRepository;
import net.bounceme.chronos.chguadalquivir.repository.RepositoryCollectionCustom;
import net.bounceme.chronos.chguadalquivir.support.CHGuadalquivirHelper;
import net.bounceme.chronos.dto.chguadalquivir.CHGuadalquivirMessageDTO;
import net.bounceme.chronos.dto.chguadalquivir.MessageType;
import net.bounceme.chronos.dto.chguadalquivir.RegistroDiarioDTO;

@Component
@Slf4j
public class RegistroDiarioImporterWriter implements ItemWriter<RegistroDiarioEmbalse> {

	@Value("${application.queue}")
	private String queueName;

	private RabbitTemplate rabbitTemplate;

	private RegistroDiarioEmbalseRepository registroDiarioEmbalseRepository;

	private RepositoryCollectionCustom repositoryCollectionCustom;

	private SimpleDateFormat dateFormat;

	private CHGuadalquivirHelper helper;

	public RegistroDiarioImporterWriter(RabbitTemplate rabbitTemplate,
			RegistroDiarioEmbalseRepository registroDiarioEmbalseRepository,
			RepositoryCollectionCustom repositoryCollectionCustom, SimpleDateFormat dateFormat,
			CHGuadalquivirHelper helper) {
		this.rabbitTemplate = rabbitTemplate;
		this.registroDiarioEmbalseRepository = registroDiarioEmbalseRepository;
		this.repositoryCollectionCustom = repositoryCollectionCustom;
		this.dateFormat = dateFormat;
		this.helper = helper;
	}

	@Override
	public synchronized void write(Chunk<? extends RegistroDiarioEmbalse> items) throws Exception {
		for (RegistroDiarioEmbalse e : items) {
			repositoryCollectionCustom.setCollectionName(e.getCodigo());

			Optional<RegistroDiarioEmbalse> oRegistroDiario = registroDiarioEmbalseRepository
					.findById(dateFormat.format(e.getFecha()));
			if (!oRegistroDiario.isPresent()) {
				writeRegistroDiario(e);
			}
		}
	}

	private void writeRegistroDiario(RegistroDiarioEmbalse e) throws Exception {
		// Set id
		e.setId(dateFormat.format(e.getFecha()));
		registroDiarioEmbalseRepository.save(e);
		log.info("Writed {}", e.toString());

		Date fecha = dateFormat.parse(e.getId());

		RegistroDiarioDTO registroDiarioDTO = RegistroDiarioDTO.builder().codigoEmbalse(e.getCodigo())
				.porcentaje(e.getPorcentaje()).volumen(e.getVolumen()).nivel(e.getNivel()).fecha(fecha).build();

		CHGuadalquivirMessageDTO<RegistroDiarioDTO> messageDTO = helper.buidMessage(registroDiarioDTO,
				RegistroDiarioDTO.class, MessageType.REGISTRO_DIARIO);

		rabbitTemplate.convertAndSend(queueName, messageDTO);
	}

}
