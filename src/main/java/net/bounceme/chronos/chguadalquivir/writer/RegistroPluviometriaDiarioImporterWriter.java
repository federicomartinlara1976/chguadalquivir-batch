package net.bounceme.chronos.chguadalquivir.writer;

import java.util.List;

import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import net.bounceme.chronos.chguadalquivir.model.RegistroDiarioPluviometria;

@Component
@Slf4j
public class RegistroPluviometriaDiarioImporterWriter implements ItemWriter<RegistroDiarioPluviometria> {

    @Override
    public synchronized void write(List<? extends RegistroDiarioPluviometria> items) throws Exception {
        for (RegistroDiarioPluviometria e : items) {
            log.info("{}", e.toString());
        }
    }
}
