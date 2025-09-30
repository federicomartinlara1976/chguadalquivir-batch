package net.bounceme.chronos.chguadalquivir.listener;

import org.springframework.batch.core.SkipListener;
import org.springframework.batch.item.file.FlatFileParseException;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class CustomSkipListener implements SkipListener<Object, Object> {

    private static final String EXCEPCION_ERROR = "Debe informar la excepción";

	@Override
    public void onSkipInRead(Throwable t) {
    	Assert.notNull(t, EXCEPCION_ERROR);
    	
        StringBuilder message = new StringBuilder("ERROR en LECTURA: ");

        if (t instanceof FlatFileParseException exception) {
            message.append("Linea ")
                    .append(exception.getLineNumber())
                    .append(": Error de formato para la siguiente entrada: ")
                    .append(exception.getInput());
        } else {
            message.append(t.getMessage());
        }
        
        log.error(message.toString());
    }

    @Override
    public void onSkipInWrite(Object item, Throwable t) {
    	Assert.notNull(t, EXCEPCION_ERROR);
    	
        StringBuilder message = new StringBuilder("ERROR en ESCRITURA: ").append(t.getMessage());
        
        log.error(message.toString());
    }

    @Override
    public void onSkipInProcess(Object item, Throwable t) {
    	Assert.notNull(t, EXCEPCION_ERROR);
    	
        StringBuilder message = new StringBuilder("ERROR en PROCESADO: ").append(t.getMessage());
        
        log.error(message.toString());
    }
}
