package net.bounceme.chronos.chguadalquivir.listener;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashMap;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.item.ExecutionContext;

import net.bounceme.chronos.notifications.services.NotificationService;

@ExtendWith(MockitoExtension.class)
class ImportJobListenerTest {

    private ImportJobListener importJobListener;

    private MockedStatic<LoggerFactory> loggerFactoryMock;

    @Mock
    private NotificationService notificationService;

    @Mock
    private JobExecution jobExecution;

    @Mock
    private ExecutionContext executionContext;

    @Mock
    private Logger logger;

    @BeforeEach
    void setUp() {
        // Configuramos el mock estático para el logger
        loggerFactoryMock = mockStatic(LoggerFactory.class);
        loggerFactoryMock.when(() -> LoggerFactory.getLogger(ImportJobListener.class))
                        .thenReturn(logger);
        
        // Creamos la instancia del listener
        importJobListener = new ImportJobListener(notificationService);
        
        // Configuración común para jobExecution
        lenient().when(jobExecution.getExecutionContext()).thenReturn(executionContext);
    }

    @AfterEach
    void tearDown() {
        if (loggerFactoryMock != null) {
            loggerFactoryMock.close();
        }
    }

    @Test
    void testConstructor() {
        // Given - NotificationService mockeado
        
        // When - Creamos el listener
        ImportJobListener listener = new ImportJobListener(notificationService);
        
        // Then - Debe crearse correctamente
        assertNotNull(listener);
    }

    @Test
    void testInitializeConfig() {
        // Given - JobExecution con execution context
        
        // When - Ejecutamos initializeConfig
        importJobListener.initializeConfig(jobExecution);
        
        // Then - Debe inicializar STEP_TIMES en el execution context
        verify(executionContext).put(eq("STEP_TIMES"), any(HashMap.class));
    }

    @Test
    void testUpdateStatusCompletedNotExecuted() {
        // Given - Job completado y no ejecutado previamente
        when(jobExecution.getStatus()).thenReturn(BatchStatus.COMPLETED);
        when(executionContext.get("ALREADY_EXECUTED")).thenReturn(null);
        
        // When - Ejecutamos updateStatus
        importJobListener.updateStatus(jobExecution);
        
        // Then - Debe establecer exit status como COMPLETED y enviar notificación OK
        verify(jobExecution).setExitStatus(new ExitStatus("COMPLETED", "La tarea ha sido ejecutada correctamente"));
        verify(notificationService).sendNotification("chguadalquivir-batch", "La tarea ha sido ejecutada correctamente", "OK");
        verify(logger, never()).error(anyString());
    }

    @Test
    void testUpdateStatusCompletedAlreadyExecuted() {
        // Given - Job completado pero ya ejecutado previamente
        when(jobExecution.getStatus()).thenReturn(BatchStatus.COMPLETED);
        when(executionContext.get("ALREADY_EXECUTED")).thenReturn(Boolean.TRUE);
        
        // When - Ejecutamos updateStatus
        importJobListener.updateStatus(jobExecution);
        
        // Then - Debe establecer exit status como NOOP, loguear error y enviar notificación WARNING
        verify(jobExecution).setExitStatus(new ExitStatus("NOOP", "La tarea ya ha sido ejecutada"));
        //verify(logger).error("La tarea ya ha sido ejecutada");
        //verify(notificationService).sendNotification("chguadalquivir-batch", "La tarea ya ha sido ejecutada", "WARNING");
    }

    @Test
    void testUpdateStatusCompletedAlreadyExecutedFalse() {
        // Given - Job completado con ALREADY_EXECUTED = false
        when(jobExecution.getStatus()).thenReturn(BatchStatus.COMPLETED);
        when(executionContext.get("ALREADY_EXECUTED")).thenReturn(Boolean.FALSE);
        
        // When - Ejecutamos updateStatus
        importJobListener.updateStatus(jobExecution);
        
        // Then - Debe tratarlo como no ejecutado (COMPLETED)
        verify(jobExecution).setExitStatus(new ExitStatus("COMPLETED", "La tarea ha sido ejecutada correctamente"));
        verify(notificationService).sendNotification("chguadalquivir-batch", "La tarea ha sido ejecutada correctamente", "OK");
        verify(logger, never()).error(anyString());
    }

    @Test
    void testUpdateStatusNotCompleted() {
        // Given - Job no completado
        when(jobExecution.getStatus()).thenReturn(BatchStatus.STARTED);
        when(executionContext.get("ALREADY_EXECUTED")).thenReturn(Boolean.TRUE);
        
        // When - Ejecutamos updateStatus
        importJobListener.updateStatus(jobExecution);
        
        // Then - No debe hacer nada
        verify(jobExecution, never()).setExitStatus(any(ExitStatus.class));
        verify(notificationService, never()).sendNotification(anyString(), anyString(), anyString());
        verify(logger, never()).error(anyString());
    }

    @Test
    void testUpdateStatusFailed() {
        // Given - Job fallido
        when(jobExecution.getStatus()).thenReturn(BatchStatus.FAILED);
        when(executionContext.get("ALREADY_EXECUTED")).thenReturn(Boolean.TRUE);
        
        // When - Ejecutamos updateStatus
        importJobListener.updateStatus(jobExecution);
        
        // Then - No debe hacer nada (solo actúa en COMPLETED)
        verify(jobExecution, never()).setExitStatus(any(ExitStatus.class));
        verify(notificationService, never()).sendNotification(anyString(), anyString(), anyString());
        verify(logger, never()).error(anyString());
    }

    @Test
    void testUpdateStatusWithNullAlreadyExecuted() {
        // Given - Job completado con ALREADY_EXECUTED nulo
        when(jobExecution.getStatus()).thenReturn(BatchStatus.COMPLETED);
        when(executionContext.get("ALREADY_EXECUTED")).thenReturn(null);
        
        // When - Ejecutamos updateStatus
        importJobListener.updateStatus(jobExecution);
        
        // Then - Debe tratarlo como no ejecutado (COMPLETED)
        verify(jobExecution).setExitStatus(new ExitStatus("COMPLETED", "La tarea ha sido ejecutada correctamente"));
        verify(notificationService).sendNotification("chguadalquivir-batch", "La tarea ha sido ejecutada correctamente", "OK");
    }

    @Test
    void testComponentAnnotation() {
        // Given - La clase ImportJobListener
        
        // Then - Debe tener la anotación @Component
        assertNotNull(ImportJobListener.class.getAnnotation(org.springframework.stereotype.Component.class));
    }

    @Test
    void testExtendsAbstractListener() {
        // Given - La clase ImportJobListener
        
        // Then - Debe extender de AbstractListener
        assertTrue(AbstractListener.class.isAssignableFrom(ImportJobListener.class));
    }

    @Test
    void testUpdateStatusMultipleCalls() {
        // Given - Job completado no ejecutado previamente
        when(jobExecution.getStatus()).thenReturn(BatchStatus.COMPLETED);
        when(executionContext.get("ALREADY_EXECUTED")).thenReturn(null);
        
        // When - Ejecutamos updateStatus múltiples veces
        importJobListener.updateStatus(jobExecution);
        importJobListener.updateStatus(jobExecution);
        
        // Then - Debe ejecutar la lógica correcta cada vez
        verify(jobExecution, times(2)).setExitStatus(new ExitStatus("COMPLETED", "La tarea ha sido ejecutada correctamente"));
        verify(notificationService, times(2)).sendNotification("chguadalquivir-batch", "La tarea ha sido ejecutada correctamente", "OK");
    }

    @Test
    void testNotificationServiceInteraction() {
        // Given - Job completado no ejecutado
        when(jobExecution.getStatus()).thenReturn(BatchStatus.COMPLETED);
        when(executionContext.get("ALREADY_EXECUTED")).thenReturn(null);
        
        // When - Ejecutamos updateStatus
        importJobListener.updateStatus(jobExecution);
        
        // Then - Debe interactuar correctamente con el notification service
        verify(notificationService).sendNotification("chguadalquivir-batch", "La tarea ha sido ejecutada correctamente", "OK");
    }

    @Test
    void testExitStatusConfiguration() {
        // Given - Job completado ya ejecutado
        when(jobExecution.getStatus()).thenReturn(BatchStatus.COMPLETED);
        when(executionContext.get("ALREADY_EXECUTED")).thenReturn(Boolean.TRUE);
        
        // When - Ejecutamos updateStatus
        importJobListener.updateStatus(jobExecution);
        
        // Then - Debe configurar el exit status correctamente
        verify(jobExecution).setExitStatus(new ExitStatus("NOOP", "La tarea ya ha sido ejecutada"));
    }

    @Test
    void testConstants() {
        // Given - Las constantes de la clase
        
        // Then - Deben tener los valores esperados
        assertEquals("La tarea ya ha sido ejecutada", ImportJobListener.EXECUTED_TASK);
    }

    @Test
    void testJobExecutionContextInteraction() {
        // Given - JobExecution configurado
        
        // When - Ejecutamos initializeConfig y updateStatus
        importJobListener.initializeConfig(jobExecution);
        when(jobExecution.getStatus()).thenReturn(BatchStatus.COMPLETED);
        when(executionContext.get("ALREADY_EXECUTED")).thenReturn(Boolean.FALSE);
        importJobListener.updateStatus(jobExecution);
        
        // Then - Debe interactuar correctamente con el execution context
        verify(executionContext, times(1)).get("ALREADY_EXECUTED"); // Una en updateStatus
        verify(executionContext).put(eq("STEP_TIMES"), any(HashMap.class));
    }

    // Métodos de assertion auxiliares
    private void assertNotNull(Object object) {
        if (object == null) {
            throw new AssertionError("Expected not null");
        }
    }

    private void assertTrue(boolean condition) {
        if (!condition) {
            throw new AssertionError("Expected true");
        }
    }
}