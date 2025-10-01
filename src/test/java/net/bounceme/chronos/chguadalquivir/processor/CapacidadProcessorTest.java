package net.bounceme.chronos.chguadalquivir.processor;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import java.util.Objects;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import net.bounceme.chronos.chguadalquivir.model.Embalse;
import net.bounceme.chronos.chguadalquivir.model.RegistroDiarioEmbalse;
import net.bounceme.chronos.chguadalquivir.utils.DataSet;

@ExtendWith(MockitoExtension.class)
class CapacidadProcessorTest {

	private DataSet dataset;
	
    private CapacidadProcessor capacidadProcessor;

    @BeforeEach
    void setUp() {
    	dataset = new DataSet();
        capacidadProcessor = new CapacidadProcessor();
    }

    @Test
    void testDefaultConstructor() {
        // When - Creamos una instancia
        CapacidadProcessor processor = new CapacidadProcessor();
        
        // Then - Debe crearse correctamente
        assertNotNull(processor);
    }

    @Test
    void testProcess() throws Exception {
        // Given - Un RegistroDiarioEmbalse con datos completos
        RegistroDiarioEmbalse input = dataset.getRegistros().get(0);

        // When - Procesamos el item
        Embalse result = capacidadProcessor.process(input);

        // Then - Debe retornar un Embalse con los datos mapeados correctamente
        assertNotNull(result);
        assertEquals("EMB123", result.getId());
        assertEquals("Embalse de Prueba", result.getNombre());
        assertEquals(1500.5f, result.getCapacidad());
        assertEquals(750.2f, result.getMen());
    }

    @Test
    void testProcessWithNullValues() {
        // Given - Un RegistroDiarioEmbalse con valores nulos
        RegistroDiarioEmbalse input = dataset.emptyRegistroDiarioEmbalse(false);

        // When & then - Debe lanzar IllegalArgumentException porque no puede tener valores nulos
        assertThrows(IllegalArgumentException.class, () -> {
        	capacidadProcessor.process(input);
        });
    }

    @Test
    void testProcessWithEmptyStrings() throws Exception {
        // Given - Un RegistroDiarioEmbalse con strings vacíos
        RegistroDiarioEmbalse input = dataset.emptyRegistroDiarioEmbalse(true);

        // When - Procesamos el item
        Embalse result = capacidadProcessor.process(input);

        // Then - Debe retornar un Embalse con strings vacíos
        assertNotNull(result);
        assertEquals("", result.getId());
        assertEquals("", result.getNombre());
        assertEquals(0.0f, result.getCapacidad());
        assertEquals(0.0f, result.getMen());
    }

    @Test
    void testProcessWithZeroValues() throws Exception {
        // Given - Un RegistroDiarioEmbalse con valores cero
        RegistroDiarioEmbalse input = dataset.getRegistros().get(1);

        // When - Procesamos el item
        Embalse result = capacidadProcessor.process(input);

        // Then - Debe retornar un Embalse con valores cero
        assertNotNull(result);
        assertEquals("EMB000", result.getId());
        assertEquals("Embalse Cero", result.getNombre());
        assertEquals(0.0f, result.getCapacidad());
        assertEquals(0.0f, result.getMen());
    }

    @Test
    void testProcessWithNegativeValues() {
        // Given - Un RegistroDiarioEmbalse con valor nulo en capacidad
        RegistroDiarioEmbalse input1 = dataset.getRegistros().get(3);
        
        // Given - Un RegistroDiarioEmbalse con valor nulo en MEN
        RegistroDiarioEmbalse input2 = dataset.getRegistros().get(4);
        
     // Given - Un RegistroDiarioEmbalse con valor negativo en capacidad
        RegistroDiarioEmbalse input3 = dataset.getRegistros().get(5);
        
        // Given - Un RegistroDiarioEmbalse con valor negativo en MEN
        RegistroDiarioEmbalse input4 = dataset.getRegistros().get(6);

        // When & then - Debe lanzar IllegalArgumentException porque no puede tener valores nulos
        assertThrows(IllegalArgumentException.class, () -> {
        	capacidadProcessor.process(input1);
        });
        
        // When & then - Debe lanzar IllegalArgumentException porque no puede tener valores nulos
        assertThrows(IllegalArgumentException.class, () -> {
        	capacidadProcessor.process(input2);
        });
        
        // When & then - Debe lanzar IllegalArgumentException porque no puede tener valores negativos
        assertThrows(IllegalArgumentException.class, () -> {
        	capacidadProcessor.process(input3);
        });
        
        // When & then - Debe lanzar IllegalArgumentException porque no puede tener valores negativos
        assertThrows(IllegalArgumentException.class, () -> {
        	capacidadProcessor.process(input4);
        });
    }

    @Test
    void testProcessWithFloatPrecision() throws Exception {
        // Given - Un RegistroDiarioEmbalse con valores float de alta precisión
        RegistroDiarioEmbalse input = dataset.getRegistros().get(2);

        // When - Procesamos el item
        Embalse result = capacidadProcessor.process(input);

        // Then - Debe mantener la precisión de los valores float
        assertNotNull(result);
        assertEquals("EMB_PREC", result.getId());
        assertEquals("Embalse Precisión", result.getNombre());
        assertEquals(1234.5678f, result.getCapacidad(), 0.0001f);
        assertEquals(987.6543f, result.getMen(), 0.0001f);
    }

    @Test
    void testProcessMultipleItems() throws Exception {
        // Given - Múltiples RegistroDiarioEmbalse (sin valores negativos)
    	List<RegistroDiarioEmbalse> registros = dataset.getRegistros().stream()
    			.filter(registro -> 
    				(!Objects.isNull(registro.getCapacidad()) && registro.getCapacidad() >= 0.0) &&
    				(!Objects.isNull(registro.getMEN()) && registro.getMEN() >= 0.0)
    			).toList();
    	RegistroDiarioEmbalse[] inputs = new RegistroDiarioEmbalse[registros.size()];
    	inputs = registros.toArray(inputs);
    	
        // When - Procesamos todos los items
        for (RegistroDiarioEmbalse input : inputs) {
            Embalse result = capacidadProcessor.process(input);
            
            // Then - Cada resultado debe ser correcto
            assertNotNull(result);
            assertEquals(input.getCodigo(), result.getId());
            assertEquals(input.getEmbalse(), result.getNombre());
            assertEquals(input.getCapacidad(), result.getCapacidad());
            assertEquals(input.getMEN(), result.getMen());
        }
    }

    @Test
    void testComponentAnnotation() {
        // Given - La clase CapacidadProcessor
        
        // Then - Debe tener la anotación @Component
        assertNotNull(CapacidadProcessor.class.getAnnotation(org.springframework.stereotype.Component.class));
    }

    @Test
    void testImplementsItemProcessor() {
        // Given - La clase CapacidadProcessor
        
        // Then - Debe implementar ItemProcessor<RegistroDiarioEmbalse, Embalse>
        assertTrue(org.springframework.batch.item.ItemProcessor.class.isAssignableFrom(CapacidadProcessor.class));
    }

    @Test
    void testMethodSignature() throws NoSuchMethodException {
        // Given - El método process
        
        // When - Obtenemos el método
        var processMethod = CapacidadProcessor.class.getMethod("process", RegistroDiarioEmbalse.class);
        
        // Then - Debe tener la firma correcta
        assertNotNull(processMethod);
        assertEquals(Embalse.class, processMethod.getReturnType());
    }
}