package net.bounceme.chronos.chguadalquivir.flow;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.job.flow.FlowExecutionStatus;
import org.springframework.batch.item.ExecutionContext;

import net.bounceme.chronos.chguadalquivir.model.Execution;
import net.bounceme.chronos.chguadalquivir.repository.ExecutionsRepository;

@ExtendWith(MockitoExtension.class)
class IsExecutedDeciderTest {

    @Mock
    private ExecutionsRepository executionsRepository;

    @Mock
    private SimpleDateFormat dateFormat;

    @Mock
    private JobExecution jobExecution;

    @Mock
    private StepExecution stepExecution;

    @Mock
    private ExecutionContext executionContext;

    private IsExecutedDecider isExecutedDecider;

    @BeforeEach
    void setUp() {
        isExecutedDecider = new IsExecutedDecider(executionsRepository, dateFormat);
        
        // Configurar mocks comunes
        lenient().when(jobExecution.getExecutionContext()).thenReturn(executionContext);
    }

    @Test
    void testDecideWhenNotExecuted() {
        // Given - No hay ejecuciones para la fecha actual
        String currentDate = "2023-12-01";
        when(dateFormat.format(any(Date.class))).thenReturn(currentDate);
        when(executionsRepository.findByDate(currentDate)).thenReturn(Collections.emptyList());

        // When - Se decide el flujo
        FlowExecutionStatus result = isExecutedDecider.decide(jobExecution, stepExecution);

        // Then - Debe retornar NO_EXECUTED
        assertNotNull(result);
        assertEquals("NO_EXECUTED", result.getName());
        
        // Verificar que no se marca como ya ejecutado
        verify(executionContext, never()).put(eq("ALREADY_EXECUTED"), eq(Boolean.TRUE));
    }

    @Test
    void testDecideWhenAlreadyExecuted() {
        // Given - Hay ejecuciones para la fecha actual
        String currentDate = "2023-12-01";
        Execution execution = new Execution();
        List<Execution> executions = Arrays.asList(execution);
        
        when(dateFormat.format(any(Date.class))).thenReturn(currentDate);
        when(executionsRepository.findByDate(currentDate)).thenReturn(executions);

        // When - Se decide el flujo
        FlowExecutionStatus result = isExecutedDecider.decide(jobExecution, stepExecution);

        // Then - Debe retornar EXECUTED
        assertNotNull(result);
        assertEquals("EXECUTED", result.getName());
        
        // Verificar que se marca como ya ejecutado en el contexto
        verify(executionContext).put("ALREADY_EXECUTED", Boolean.TRUE);
    }

    @Test
    void testDecideWithMultipleExecutions() {
        // Given - Múltiples ejecuciones para la fecha actual
        String currentDate = "2023-12-01";
        Execution execution1 = new Execution();
        Execution execution2 = new Execution();
        List<Execution> executions = Arrays.asList(execution1, execution2);
        
        when(dateFormat.format(any(Date.class))).thenReturn(currentDate);
        when(executionsRepository.findByDate(currentDate)).thenReturn(executions);

        // When - Se decide el flujo
        FlowExecutionStatus result = isExecutedDecider.decide(jobExecution, stepExecution);

        // Then - Debe retornar EXECUTED
        assertNotNull(result);
        assertEquals("EXECUTED", result.getName());
        
        // Verificar que se marca como ya ejecutado
        verify(executionContext).put("ALREADY_EXECUTED", Boolean.TRUE);
    }

    @Test
    void testDecideWithNullStepExecution() {
        // Given - StepExecution nulo
        String currentDate = "2023-12-01";
        when(dateFormat.format(any(Date.class))).thenReturn(currentDate);
        when(executionsRepository.findByDate(currentDate)).thenReturn(Collections.emptyList());

        // When - Se decide el flujo con stepExecution nulo
        FlowExecutionStatus result = isExecutedDecider.decide(jobExecution, null);

        // Then - Debe retornar NO_EXECUTED (debe manejar el nulo correctamente)
        assertNotNull(result);
        assertEquals("NO_EXECUTED", result.getName());
    }

    @Test
    void testDecideDateFormatting() {
        // Given - Una fecha específica
        Date testDate = new Date();
        String formattedDate = "2023-12-01 10:30:00";
        
        when(dateFormat.format(testDate)).thenReturn(formattedDate);
        lenient().when(executionsRepository.findByDate(formattedDate)).thenReturn(Collections.emptyList());

        // When - Se decide el flujo
        FlowExecutionStatus result = isExecutedDecider.decide(jobExecution, stepExecution);

        // Then - Debe usar el formato correcto y buscar por esa fecha
        assertNotNull(result);
        assertEquals("NO_EXECUTED", result.getName());
        verify(dateFormat).format(any(Date.class));
        verify(executionsRepository).findByDate(formattedDate);
    }

    @Test
    void testImplementsJobExecutionDecider() {
        // Given - La clase IsExecutedDecider
        
        // Then - Debe implementar JobExecutionDecider
        assertTrue(org.springframework.batch.core.job.flow.JobExecutionDecider.class
            .isAssignableFrom(IsExecutedDecider.class));
    }

    @Test
    void testExecutionContextIsSetWhenExecuted() {
        // Given - Ya se ejecutó para la fecha actual
        String currentDate = "2023-12-01";
        Execution execution = new Execution();
        List<Execution> executions = Arrays.asList(execution);
        
        when(dateFormat.format(any(Date.class))).thenReturn(currentDate);
        when(executionsRepository.findByDate(currentDate)).thenReturn(executions);

        // When - Se decide el flujo
        isExecutedDecider.decide(jobExecution, stepExecution);

        // Then - Debe establecer ALREADY_EXECUTED en el contexto
        verify(executionContext).put("ALREADY_EXECUTED", Boolean.TRUE);
    }

    @Test
    void testExecutionContextNotSetWhenNotExecuted() {
        // Given - No se ha ejecutado para la fecha actual
        String currentDate = "2023-12-01";
        when(dateFormat.format(any(Date.class))).thenReturn(currentDate);
        when(executionsRepository.findByDate(currentDate)).thenReturn(Collections.emptyList());

        // When - Se decide el flujo
        isExecutedDecider.decide(jobExecution, stepExecution);

        // Then - No debe establecer ALREADY_EXECUTED en el contexto
        verify(executionContext, never()).put(eq("ALREADY_EXECUTED"), eq(Boolean.TRUE));
    }

    @Test
    void testFlowExecutionStatusValues() {
        // Test para verificar que los valores de FlowExecutionStatus son los esperados
        
        // Given - Diferentes escenarios
        String currentDate = "2023-12-01";
        
        // When - No ejecutado
        when(dateFormat.format(any(Date.class))).thenReturn(currentDate);
        when(executionsRepository.findByDate(currentDate)).thenReturn(Collections.emptyList());
        FlowExecutionStatus notExecuted = isExecutedDecider.decide(jobExecution, stepExecution);
        
        // When - Ya ejecutado
        when(executionsRepository.findByDate(currentDate)).thenReturn(Arrays.asList(new Execution()));
        FlowExecutionStatus executed = isExecutedDecider.decide(jobExecution, stepExecution);

        // Then - Los estados deben ser diferentes
        assertEquals("NO_EXECUTED", notExecuted.getName());
        assertEquals("EXECUTED", executed.getName());
        assertNotEquals(notExecuted, executed);
    }

    // Método auxiliar para inyectar campos usando reflection
    private void setField(Object target, String fieldName, Object value) {
        try {
            var field = target.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(target, value);
        } catch (Exception e) {
            throw new RuntimeException("Failed to set field: " + fieldName, e);
        }
    }

    // Métodos de assertion auxiliares
    private void assertNotNull(Object object) {
        if (object == null) {
            throw new AssertionError("Expected not null");
        }
    }

    private void assertEquals(Object expected, Object actual) {
        if (expected == null && actual == null) return;
        if (expected == null || !expected.equals(actual)) {
            throw new AssertionError("Expected: " + expected + ", but was: " + actual);
        }
    }

    private void assertTrue(boolean condition) {
        if (!condition) {
            throw new AssertionError("Expected true");
        }
    }

    private void assertNotEquals(Object expected, Object actual) {
        if (expected != null && expected.equals(actual)) {
            throw new AssertionError("Expected different values");
        }
    }
}