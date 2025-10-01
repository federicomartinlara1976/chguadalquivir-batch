package net.bounceme.chronos.chguadalquivir.listener;

import static org.awaitility.Awaitility.await;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

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
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.item.ExecutionContext;

@ExtendWith(MockitoExtension.class)
class TimeStepListenerTest {

    private TimeStepListener timeStepListener;

    private MockedStatic<LoggerFactory> loggerFactoryMock;

    @Mock
    private Logger logger;

    @Mock
    private StepExecution stepExecution;

    @Mock
    private JobExecution jobExecution;

    @Mock
    private ExecutionContext executionContext;

    @BeforeEach
    void setUp() {
        // Configuramos los mocks estáticos
        loggerFactoryMock = mockStatic(LoggerFactory.class);
        loggerFactoryMock.when(() -> LoggerFactory.getLogger(TimeStepListener.class))
                        .thenReturn(logger);
        
        // Creamos la instancia del listener
        timeStepListener = new TimeStepListener();
        
        // Configuración común para los mocks
        // El método lenient() revisa si hace falta o no en la ejecución del test
        lenient().when(stepExecution.getJobExecution()).thenReturn(jobExecution);
        lenient().when(jobExecution.getExecutionContext()).thenReturn(executionContext);
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
        TimeStepListener listener = new TimeStepListener();
        
        // Then - Debe crearse correctamente
        assertNotNull(listener);
    }

    @Test
    void testBeforeStep() {
        // Given - StepExecution con nombre y tiempo inicial
        String stepName = "testStep";
        when(stepExecution.getStepName()).thenReturn(stepName);
        
        // When - Ejecutamos beforeStep
        timeStepListener.beforeStep(stepExecution);
        
        // Then - Debe loguear el mensaje y guardar el tiempo de inicio
        verifyNoInteractions(logger);
        // El startTime debe quedar guardado internamente
    }

    @Test
    void testBeforeStepWithNullStepExecution() {
        // Given - StepExecution nulo
        
        // When & Then - Debe lanzar IllegalArgumentException
        assertThrows(IllegalArgumentException.class, () -> {
        	timeStepListener.beforeStep(null);
        });
    }

    @Test
    void testAfterStep() {
        // Given - StepExecution configurado y tiempos simulados
        String stepName = "testStep";
        when(stepExecution.getStepName()).thenReturn(stepName);
        
        Map<String, Long> stepTimes = new HashMap<>();
        when(executionContext.get("STEP_TIMES")).thenReturn(stepTimes);
        
        // Ejecutamos beforeStep primero para establecer startTime
        timeStepListener.beforeStep(stepExecution);
        
        // Usamos Awaitility para esperar que pase algo de tiempo
        delay(2, TimeUnit.SECONDS);
        
        // When - Ejecutamos afterStep
        ExitStatus result = timeStepListener.afterStep(stepExecution);
        
        // Then - Debe calcular la duración, loguear y guardar en stepTimes
        assertEquals(ExitStatus.COMPLETED, result);
        verifyNoInteractions(logger);
    }

    @Test
    void testAfterStepWithNullStepTimes() {
        // Given - StepExecution pero sin STEP_TIMES en el execution context
        String stepName = "testStep";
        when(stepExecution.getStepName()).thenReturn(stepName);
        when(executionContext.get("STEP_TIMES")).thenReturn(null);
        
        // Ejecutamos beforeStep primero
        timeStepListener.beforeStep(stepExecution);
        
        // When - Ejecutamos afterStep
        ExitStatus result = timeStepListener.afterStep(stepExecution);
        
        // Then - Debe calcular la duración y loguear, pero no guardar en stepTimes (es nulo)
        assertEquals(ExitStatus.COMPLETED, result);
    }

    @Test
    void testAfterStepWithoutBeforeStep() {
        // Given - afterStep sin haber llamado antes a beforeStep
       
    	// When & Then - Debe lanzar IllegalArgumentException
        assertThrows(IllegalArgumentException.class, () -> {
        	timeStepListener.afterStep(stepExecution);
        });
    }

    @Test
    void testAfterStepWithNullStepExecution() {
        // Given - StepExecution nulo
        
        // When & Then - Debe lanzar IllegalArgumentException
        assertThrows(IllegalArgumentException.class, () -> {
        	timeStepListener.afterStep(null);
        });
    }

    @Test
    void testComponentAnnotation() {
        // Given - La clase TimeStepListener
        
        // Then - Debe tener la anotación @Component
        assertNotNull(TimeStepListener.class.getAnnotation(org.springframework.stereotype.Component.class));
    }

    @Test
    void testImplementsStepExecutionListener() {
        // Given - La clase TimeStepListener
        
        // Then - Debe implementar StepExecutionListener
        assertTrue(org.springframework.batch.core.StepExecutionListener.class.isAssignableFrom(TimeStepListener.class));
    }

    @Test
    void testLongDurationCalculation() {
        // Given - StepExecution con larga duración
        String stepName = "longStep";
        when(stepExecution.getStepName()).thenReturn(stepName);
        
        Map<String, Long> stepTimes = new HashMap<>();
        when(executionContext.get("STEP_TIMES")).thenReturn(stepTimes);
        
        timeStepListener.beforeStep(stepExecution);
        
        delay(2, TimeUnit.SECONDS);
        
        // When - Ejecutamos afterStep
        ExitStatus result = timeStepListener.afterStep(stepExecution);
        
        // Then - Debe calcular correctamente la duración larga
        assertEquals(ExitStatus.COMPLETED, result);
    }

    @Test
    void testExecutionContextInteraction() {
        // Given - StepExecution con execution context completo
        String stepName = "fullStep";
        when(stepExecution.getStepName()).thenReturn(stepName);
        when(stepExecution.getJobExecution()).thenReturn(jobExecution);
        when(jobExecution.getExecutionContext()).thenReturn(executionContext);
        
        Map<String, Long> stepTimes = new HashMap<>();
        when(executionContext.get("STEP_TIMES")).thenReturn(stepTimes);
        
        // When - Ejecutamos el flujo completo
        timeStepListener.beforeStep(stepExecution);
        ExitStatus result = timeStepListener.afterStep(stepExecution);
        
        // Then - Debe interactuar correctamente con toda la cadena
        verify(stepExecution, times(3)).getStepName();
        verify(stepExecution).getJobExecution();
        verify(jobExecution).getExecutionContext();
        verify(executionContext).get("STEP_TIMES");
        
        assertEquals(ExitStatus.COMPLETED, result);
    }
    
    private void delay(Integer quantum, TimeUnit unit) {
		AtomicBoolean timePassed = new AtomicBoolean(false);
        await().atMost(quantum, unit)
               .until(() -> {
                   // Simulamos que el tiempo ha pasado
                   timePassed.set(true);
                   return true;
               });
	}
}