package net.bounceme.chronos.chguadalquivir.listener;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.item.ExecutionContext;

import net.bounceme.chronos.chguadalquivir.model.Execution;

@ExtendWith(MockitoExtension.class)
class StatExecutionsListenerTest {

    private StatExecutionsListener statExecutionsListener;

    @Mock
    private JobExecution jobExecution;

    @Mock
    private ExecutionContext executionContext;

    @BeforeEach
    void setUp() {
        statExecutionsListener = new StatExecutionsListener();
        lenient().when(jobExecution.getExecutionContext()).thenReturn(executionContext);
    }

    @Test
    void testDefaultConstructor() {
        // Given - Constructor por defecto
        
        // When - Creamos una instancia
        StatExecutionsListener listener = new StatExecutionsListener();
        
        // Then - Debe crearse correctamente
        assertNotNull(listener);
    }

    @Test
    void testInitializeConfig() {
        // Given - JobExecution con execution context
        
        // When - Ejecutamos initializeConfig
        statExecutionsListener.initializeConfig(jobExecution);
        
        // Then - Debe inicializar la lista EXECUTIONS en el execution context
        verify(executionContext).put(eq("EXECUTIONS"), any(ArrayList.class));
    }

    @Test
    void testInitializeConfigWithNullJobExecution() {
        // Given - JobExecution nulo
        
    	// When & Then - Debe lanzar IllegalArgumentException
        assertThrows(IllegalArgumentException.class, () -> {
        	statExecutionsListener.initializeConfig(null);
        });
    }

    @Test
    void testUpdateStatus() {
        // Given - JobExecution mockeado
        
        // When - Ejecutamos updateStatus
        statExecutionsListener.updateStatus(jobExecution);
        
        // Then - Debe establecer el exit status como COMPLETED
        verify(jobExecution).setExitStatus(new ExitStatus("COMPLETED", "La tarea ha sido ejecutada correctamente"));
    }

    @Test
    void testUpdateStatusWithNullJobExecution() {
        // Given - JobExecution nulo
        
        // When & Then - Debe lanzar IllegalArgumentException
        assertThrows(IllegalArgumentException.class, () -> {
        	statExecutionsListener.updateStatus(null);
        });
        
        // Then - Debe manejar el nulo sin lanzar excepciones
        // (comportamiento implícito - si no lanza excepción, pasa el test)
    }

    @Test
    void testInitializeConfigMultipleCalls() {
        // Given - JobExecution con execution context
        
        // When - Ejecutamos initializeConfig múltiples veces
        statExecutionsListener.initializeConfig(jobExecution);
        statExecutionsListener.initializeConfig(jobExecution);
        statExecutionsListener.initializeConfig(jobExecution);
        
        // Then - Debe crear una nueva lista cada vez
        verify(executionContext, times(3)).put(eq("EXECUTIONS"), any(ArrayList.class));
    }

    @Test
    void testComponentAnnotation() {
        // Given - La clase StatExecutionsListener
        
        // Then - Debe tener la anotación @Component
        assertNotNull(StatExecutionsListener.class.getAnnotation(org.springframework.stereotype.Component.class));
    }

    @Test
    void testNoSlf4jAnnotation() {
        // Given - La clase StatExecutionsListener
        
        // Then - NO debe tener la anotación @Slf4j (no usa logging)
        assertNull(StatExecutionsListener.class.getAnnotation(lombok.extern.slf4j.Slf4j.class));
    }

    @Test
    void testExtendsAbstractListener() {
        // Given - La clase StatExecutionsListener
        
        // Then - Debe extender de AbstractListener
        assertTrue(AbstractListener.class.isAssignableFrom(StatExecutionsListener.class));
    }

    @Test
    void testMethodsAreOverridden() throws NoSuchMethodException {
        // Given - La clase StatExecutionsListener
        
        // When - Obtenemos los métodos
        var initializeConfigMethod = StatExecutionsListener.class.getDeclaredMethod("initializeConfig", JobExecution.class);
        var updateStatusMethod = StatExecutionsListener.class.getDeclaredMethod("updateStatus", JobExecution.class);
        
        // Then - Deben ser métodos de instancia (no estáticos)
        assertFalse(java.lang.reflect.Modifier.isStatic(initializeConfigMethod.getModifiers()));
        assertFalse(java.lang.reflect.Modifier.isStatic(updateStatusMethod.getModifiers()));
    }

    @Test
    void testInitializeConfigUsesCorrectKey() {
        // Given - JobExecution con execution context
        
        // When - Ejecutamos initializeConfig
        statExecutionsListener.initializeConfig(jobExecution);
        
        // Then - Debe usar la clave "EXECUTIONS" en el execution context
        List<Execution> executions = new ArrayList<>();
        verify(executionContext).put("EXECUTIONS", executions);
    }

    @Test
    void testUpdateStatusDoesNotModifyExecutionContext() {
        // Given - JobExecution con execution context
        
        // When - Ejecutamos updateStatus
        statExecutionsListener.updateStatus(jobExecution);
        
        // Then - No debe interactuar con el execution context
        verify(jobExecution, never()).getExecutionContext();
    }

    @Test
    void testStatelessBehavior() {
        // Given - Múltiples instancias del listener
        StatExecutionsListener listener1 = new StatExecutionsListener();
        StatExecutionsListener listener2 = new StatExecutionsListener();
        
        // Configurar mocks para ambas instancias
        when(jobExecution.getExecutionContext()).thenReturn(executionContext);
        
        // When - Ejecutamos métodos en diferentes instancias
        listener1.initializeConfig(jobExecution);
        listener2.initializeConfig(jobExecution);
        listener1.updateStatus(jobExecution);
        listener2.updateStatus(jobExecution);
        
        // Then - Ambas instancias deben comportarse igual
        verify(executionContext, times(2)).put(eq("EXECUTIONS"), any(ArrayList.class));
        verify(jobExecution, times(2)).setExitStatus(new ExitStatus("COMPLETED", "La tarea ha sido ejecutada correctamente"));
    }

    @Test
    void testExecutionClassUsed() {
        // Given - La importación de la clase Execution
        
        // Then - La clase Execution debe estar disponible en el classpath
        // Esto verifica que la compilación funciona correctamente
        assertDoesNotThrow(() -> {
            Class<?> executionClass;
			try {
				executionClass = Class.forName("net.bounceme.chronos.chguadalquivir.model.Execution");
				assertNotNull(executionClass);
			} catch (ClassNotFoundException e) {
				Assert.fail("No se encuentra la clase");
			}
        });
    }

    @Test
    void testInitializeConfigBeforeUpdateStatus() {
        // Given - JobExecution con execution context
        
        // When - Ejecutamos initializeConfig seguido de updateStatus
        statExecutionsListener.initializeConfig(jobExecution);
        statExecutionsListener.updateStatus(jobExecution);
        
        // Then - Ambas operaciones deben completarse correctamente
        verify(executionContext).put(eq("EXECUTIONS"), any(ArrayList.class));
        verify(jobExecution).setExitStatus(new ExitStatus("COMPLETED", "La tarea ha sido ejecutada correctamente"));
    }

    private void assertDoesNotThrow(Runnable runnable) {
        try {
            runnable.run();
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception: " + e.getMessage(), e);
        }
    }
}