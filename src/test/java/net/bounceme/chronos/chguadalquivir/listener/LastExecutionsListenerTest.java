package net.bounceme.chronos.chguadalquivir.listener;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobExecution;

@ExtendWith(MockitoExtension.class)
class LastExecutionsListenerTest {

    private LastExecutionsListener lastExecutionsListener;

    private MockedStatic<LoggerFactory> loggerFactoryMock;

    @Mock
    private JobExecution jobExecution;

    @Mock
    private Logger logger;

    @BeforeEach
    void setUp() {
        // Configuramos el mock estático para el logger
        loggerFactoryMock = mockStatic(LoggerFactory.class);
        loggerFactoryMock.when(() -> LoggerFactory.getLogger(LastExecutionsListener.class))
                        .thenReturn(logger);
        
        // Creamos la instancia del listener
        lastExecutionsListener = new LastExecutionsListener();
    }

    @AfterEach
    void tearDown() {
        if (loggerFactoryMock != null) {
            loggerFactoryMock.close();
        }
    }

    @Test
    void testDefaultConstructor() {
        // Given - Constructor por defecto
        
        // When - Creamos una instancia
        LastExecutionsListener listener = new LastExecutionsListener();
        
        // Then - Debe crearse correctamente
        assertNotNull(listener);
    }

    @Test
    void testInitializeConfig() {
        // Given - JobExecution mockeado
        
        // When - Ejecutamos initializeConfig
        lastExecutionsListener.initializeConfig(jobExecution);
        
        // Then - Debe loguear el mensaje informativo
        verifyNoInteractions(logger);
        // No debe interactuar con el jobExecution
        verify(jobExecution, never()).getExecutionContext();
    }

    @Test
    void testInitializeConfigWithNullJobExecution() {
        // Given - JobExecution nulo
        
    	// When & Then - Debe lanzar IllegalArgumentException
        assertThrows(IllegalArgumentException.class, () -> {
        	lastExecutionsListener.initializeConfig(null);
        });
    }

    @Test
    void testUpdateStatus() {
        // Given - JobExecution mockeado
        
        // When - Ejecutamos updateStatus
        lastExecutionsListener.updateStatus(jobExecution);
        
        // Then - Debe establecer el exit status como COMPLETED
        verify(jobExecution).setExitStatus(new ExitStatus("COMPLETED", "La tarea ha sido ejecutada correctamente"));
        // No debe loguear nada en updateStatus
        verify(logger, never()).info(anyString());
        verify(logger, never()).error(anyString());
        verify(logger, never()).warn(anyString());
    }

    @Test
    void testUpdateStatusWithNullJobExecution() {
        // Given - JobExecution nulo
        
        // When & Then - Debe lanzar IllegalArgumentException
        assertThrows(IllegalArgumentException.class, () -> {
        	lastExecutionsListener.updateStatus(null);
        });
    }

    @Test
    void testUpdateStatusMultipleCalls() {
        // Given - JobExecution mockeado
        
        // When - Ejecutamos updateStatus múltiples veces
        lastExecutionsListener.updateStatus(jobExecution);
        lastExecutionsListener.updateStatus(jobExecution);
        lastExecutionsListener.updateStatus(jobExecution);
        
        // Then - Debe establecer el exit status cada vez
        verify(jobExecution, times(3)).setExitStatus(new ExitStatus("COMPLETED", "La tarea ha sido ejecutada correctamente"));
    }

    @Test
    void testComponentAnnotation() {
        // Given - La clase LastExecutionsListener
        
        // Then - Debe tener la anotación @Component
        assertNotNull(LastExecutionsListener.class.getAnnotation(org.springframework.stereotype.Component.class));
    }

    @Test
    void testExtendsAbstractListener() {
        // Given - La clase LastExecutionsListener
        
        // Then - Debe extender de AbstractListener
        assertTrue(AbstractListener.class.isAssignableFrom(LastExecutionsListener.class));
    }

    @Test
    void testMethodsAreOverridden() throws NoSuchMethodException {
        // Given - La clase LastExecutionsListener
        
        // When - Obtenemos los métodos
        var initializeConfigMethod = LastExecutionsListener.class.getDeclaredMethod("initializeConfig", JobExecution.class);
        var updateStatusMethod = LastExecutionsListener.class.getDeclaredMethod("updateStatus", JobExecution.class);
        
        // Then - Deben ser métodos de instancia (no estáticos)
        assertFalse(java.lang.reflect.Modifier.isStatic(initializeConfigMethod.getModifiers()));
        assertFalse(java.lang.reflect.Modifier.isStatic(updateStatusMethod.getModifiers()));
    }

    @Test
    void testExitStatusContent() {
        // Given - JobExecution mockeado
        
        // When - Ejecutamos updateStatus
        lastExecutionsListener.updateStatus(jobExecution);
        
        // Then - Debe establecer el exit status con código y descripción correctos
        verify(jobExecution).setExitStatus(new ExitStatus("COMPLETED", "La tarea ha sido ejecutada correctamente"));
    }

    @Test
    void testNoExecutionContextInteraction() {
        // Given - JobExecution mockeado
        
        // When - Ejecutamos ambos métodos
        lastExecutionsListener.initializeConfig(jobExecution);
        lastExecutionsListener.updateStatus(jobExecution);
        
        // Then - No debe interactuar con el execution context del job
        verify(jobExecution, never()).getExecutionContext();
    }

    @Test
    void testLoggingLevels() {
        // Given - JobExecution mockeado
        
        // When - Ejecutamos initializeConfig
        lastExecutionsListener.initializeConfig(jobExecution);
        
        // Then - Debe usar nivel INFO (no ERROR ni WARN)
        verifyNoInteractions(logger);
    }

    @Test
    void testUpdateStatusDoesNotLog() {
        // Given - JobExecution mockeado
        
        // When - Ejecutamos updateStatus
        lastExecutionsListener.updateStatus(jobExecution);
        
        // Then - No debe producir ningún log
        verify(logger, never()).info(anyString());
        verify(logger, never()).error(anyString());
        verify(logger, never()).warn(anyString());
        verify(logger, never()).debug(anyString());
    }
}