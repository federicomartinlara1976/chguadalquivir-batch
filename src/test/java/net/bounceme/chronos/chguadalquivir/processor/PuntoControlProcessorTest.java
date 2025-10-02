package net.bounceme.chronos.chguadalquivir.processor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import net.bounceme.chronos.chguadalquivir.model.PuntoControl;

@ExtendWith(MockitoExtension.class)
class PuntoControlProcessorTest {

    private PuntoControlProcessor puntoControlProcessor;

    @BeforeEach
    void setUp() {
        puntoControlProcessor = new PuntoControlProcessor();
    }

    @Test
    void testDefaultConstructor() {
        // When - Creamos una instancia
        PuntoControlProcessor processor = new PuntoControlProcessor();
        
        // Then - Debe crearse correctamente
        assertNotNull(processor);
    }

    @Test
    void testProcessWithValidPuntoControl() {
        // Given - Un PuntoControl con datos completos
        PuntoControl input = PuntoControl.builder()
                .id("PC123")
                .nombre("Punto de Control de Prueba")
                .build();

        // When - Procesamos el item
        PuntoControl result = puntoControlProcessor.process(input);

        // Then - Debe retornar el mismo objeto (identidad)
        assertNotNull(result);
        assertSame(input, result); // Misma instancia
        assertEquals("PC123", result.getId());
        assertEquals("Punto de Control de Prueba", result.getNombre());
    }

    @Test
    void testProcessWithNullPuntoControl() {
        // Given - Un PuntoControl nulo
        
        // When - Procesamos el item nulo
        PuntoControl result = puntoControlProcessor.process(null);

        // Then - Debe retornar nulo
        assertNull(result);
    }

    @Test
    void testProcessWithPuntoControlWithNullValues() {
        // Given - Un PuntoControl con valores nulos
        PuntoControl input = PuntoControl.builder()
                .id(null)
                .nombre(null)
                .build();

        // When - Procesamos el item
        PuntoControl result = puntoControlProcessor.process(input);

        // Then - Debe retornar el mismo objeto con valores nulos
        assertNotNull(result);
        assertSame(input, result);
        assertNull(result.getId());
        assertNull(result.getNombre());
    }

    @Test
    void testProcessWithPuntoControlWithEmptyStrings() {
        // Given - Un PuntoControl con strings vacíos
        PuntoControl input = PuntoControl.builder()
                .id("")
                .nombre("")
                .build();

        // When - Procesamos el item
        PuntoControl result = puntoControlProcessor.process(input);

        // Then - Debe retornar el mismo objeto con strings vacíos
        assertNotNull(result);
        assertSame(input, result);
        assertEquals("", result.getId());
        assertEquals("", result.getNombre());
    }

    @Test
    void testProcessWithPuntoControlWithSpecialCharacters() {
        // Given - Un PuntoControl con caracteres especiales en el nombre
        PuntoControl input = PuntoControl.builder()
                .id("PC_ESP")
                .nombre("Punto de Control Ñoño-Ciénaga")
                .build();

        // When - Procesamos el item
        PuntoControl result = puntoControlProcessor.process(input);

        // Then - Debe retornar el mismo objeto preservando caracteres especiales
        assertNotNull(result);
        assertSame(input, result);
        assertEquals("PC_ESP", result.getId());
        assertEquals("Punto de Control Ñoño-Ciénaga", result.getNombre());
    }

    @Test
    void testProcessWithPuntoControlWithLongNames() {
        // Given - Un PuntoControl con nombres largos
        String longName = "Punto de Control de Medición de Caudal del Río Guadalquivir en la Estación Norte";
        PuntoControl input = PuntoControl.builder()
                .id("PC_LARGO")
                .nombre(longName)
                .build();

        // When - Procesamos el item
        PuntoControl result = puntoControlProcessor.process(input);

        // Then - Debe retornar el mismo objeto con nombres largos
        assertNotNull(result);
        assertSame(input, result);
        assertEquals("PC_LARGO", result.getId());
        assertEquals(longName, result.getNombre());
    }

    @Test
    void testProcessMultiplePuntoControls() {
        // Given - Múltiples PuntoControls
        PuntoControl[] inputs = {
            PuntoControl.builder().id("PC1").nombre("Punto Control 1").build(),
            PuntoControl.builder().id("PC2").nombre("Punto Control 2").build(),
            PuntoControl.builder().id("PC3").nombre("Punto Control 3").build()
        };

        // When - Procesamos todos los items
        for (PuntoControl input : inputs) {
            PuntoControl result = puntoControlProcessor.process(input);
            
            // Then - Cada resultado debe ser la misma instancia
            assertNotNull(result);
            assertSame(input, result);
            assertEquals(input.getId(), result.getId());
            assertEquals(input.getNombre(), result.getNombre());
        }
    }

    @Test
    void testProcessReturnsSameInstance() {
        // Given - Un PuntoControl
        PuntoControl input = PuntoControl.builder()
                .id("PC_SAME")
                .nombre("Mismo Punto Control")
                .build();

        // When - Procesamos el mismo item múltiples veces
        PuntoControl result1 = puntoControlProcessor.process(input);
        PuntoControl result2 = puntoControlProcessor.process(input);
        PuntoControl result3 = puntoControlProcessor.process(input);

        // Then - Todas las veces debe retornar la misma instancia
        assertSame(input, result1);
        assertSame(input, result2);
        assertSame(input, result3);
        assertSame(result1, result2);
        assertSame(result2, result3);
    }

    @Test
    void testProcessIsIdentityOperation() {
        // Given - Un PuntoControl
        PuntoControl original = PuntoControl.builder()
                .id("PC_IDENTITY")
                .nombre("Punto Control Identidad")
                .build();

        // When - Procesamos el item
        PuntoControl result = puntoControlProcessor.process(original);

        // Then - Debe ser una operación identidad (misma instancia)
        assertSame(original, result);
        
        // Verificamos que todos los campos sean iguales (que no se modificó)
        assertEquals(original.getId(), result.getId());
        assertEquals(original.getNombre(), result.getNombre());
    }

    @Test
    void testComponentAnnotation() {
        // Given - La clase PuntoControlProcessor
        
        // Then - Debe tener la anotación @Component
        assertNotNull(PuntoControlProcessor.class.getAnnotation(org.springframework.stereotype.Component.class));
    }

    @Test
    void testImplementsItemProcessor() {
        // Given - La clase PuntoControlProcessor
        
        // Then - Debe implementar ItemProcessor<PuntoControl, PuntoControl>
        assertTrue(org.springframework.batch.item.ItemProcessor.class.isAssignableFrom(PuntoControlProcessor.class));
    }

    @Test
    void testMethodSignature() throws NoSuchMethodException {
        // Given - El método process
        
        // When - Obtenemos el método
        var processMethod = PuntoControlProcessor.class.getMethod("process", PuntoControl.class);
        
        // Then - Debe tener la firma correcta
        assertNotNull(processMethod);
        assertEquals(PuntoControl.class, processMethod.getReturnType());
        
        // No debe declarar excepciones checked
        assertEquals(0, processMethod.getExceptionTypes().length);
    }

    @Test
    void testNoExceptionDeclaration() throws NoSuchMethodException {
        // Given - El método process
        
        // When - Obtenemos el método
        var processMethod = PuntoControlProcessor.class.getMethod("process", PuntoControl.class);
        
        // Then - No debe declarar excepciones
        assertEquals(0, processMethod.getExceptionTypes().length);
    }

    @Test
    void testProcessorIsStateless() {
        // Given - Múltiples instancias del processor
        PuntoControlProcessor processor1 = new PuntoControlProcessor();
        PuntoControlProcessor processor2 = new PuntoControlProcessor();
        
        PuntoControl input = PuntoControl.builder()
                .id("PC_STATELESS")
                .nombre("Punto Control Stateless")
                .build();

        // When - Procesamos con diferentes instancias
        PuntoControl result1 = processor1.process(input);
        PuntoControl result2 = processor2.process(input);

        // Then - Ambas deben retornar la misma instancia de input
        assertSame(input, result1);
        assertSame(input, result2);
    }

    @Test
    void testNoTransformationApplied() {
        // Given - Un PuntoControl
        PuntoControl input = PuntoControl.builder()
                .id("PC_NO_TRANSFORM")
                .nombre("Punto Control Sin Transformación")
                .build();

        // Capturamos el estado original
        String originalId = input.getId();
        String originalNombre = input.getNombre();

        // When - Procesamos el item
        PuntoControl result = puntoControlProcessor.process(input);

        // Then - No debe haber ninguna transformación o modificación
        assertSame(input, result);
        assertEquals(originalId, result.getId());
        assertEquals(originalNombre, result.getNombre());
        
        // Verificamos que son exactamente los mismos objetos (no copias)
        assertSame(originalId, result.getId()); // misma referencia de String
        assertSame(originalNombre, result.getNombre()); // misma referencia de String
    }

    @Test
    void testProcessWithMinimalData() {
        // Given - Un PuntoControl con datos mínimos (solo ID)
        PuntoControl input = PuntoControl.builder()
                .id("PC_MIN")
                .build();

        // When - Procesamos el item
        PuntoControl result = puntoControlProcessor.process(input);

        // Then - Debe retornar el mismo objeto
        assertNotNull(result);
        assertSame(input, result);
        assertEquals("PC_MIN", result.getId());
        assertNull(result.getNombre()); // nombre debería ser null
    }

    @Test
    void testProcessWithOnlyName() {
        // Given - Un PuntoControl con solo nombre (ID null)
        PuntoControl input = PuntoControl.builder()
                .nombre("Solo Nombre")
                .build();

        // When - Procesamos el item
        PuntoControl result = puntoControlProcessor.process(input);

        // Then - Debe retornar el mismo objeto
        assertNotNull(result);
        assertSame(input, result);
        assertNull(result.getId());
        assertEquals("Solo Nombre", result.getNombre());
    }

    @Test
    void testProcessWithNumericId() {
        // Given - Un PuntoControl con ID numérico
        PuntoControl input = PuntoControl.builder()
                .id("12345")
                .nombre("Punto Control 12345")
                .build();

        // When - Procesamos el item
        PuntoControl result = puntoControlProcessor.process(input);

        // Then - Debe retornar el mismo objeto
        assertNotNull(result);
        assertSame(input, result);
        assertEquals("12345", result.getId());
        assertEquals("Punto Control 12345", result.getNombre());
    }

    @Test
    void testProcessWithComplexId() {
        // Given - Un PuntoControl con ID complejo
        PuntoControl input = PuntoControl.builder()
                .id("PC-GUAD-2024-NORTE-001")
                .nombre("Punto Control Complejo")
                .build();

        // When - Procesamos el item
        PuntoControl result = puntoControlProcessor.process(input);

        // Then - Debe retornar el mismo objeto
        assertNotNull(result);
        assertSame(input, result);
        assertEquals("PC-GUAD-2024-NORTE-001", result.getId());
        assertEquals("Punto Control Complejo", result.getNombre());
    }

    @Test
    void testProcessMaintainsObjectIdentity() {
        // Given - Un PuntoControl
        PuntoControl input = PuntoControl.builder()
                .id("PC_IDENTITY_TEST")
                .nombre("Test Identidad")
                .build();

        // When - Procesamos el item
        PuntoControl result = puntoControlProcessor.process(input);

        // Then - Debe mantener la identidad del objeto (mismo hashCode)
        assertEquals(input.hashCode(), result.hashCode());
        assertSame(input, result);
        assertSame(result, input);
    }

    @Test
    void testProcessWithWhitespace() {
        // Given - Un PuntoControl con espacios en blanco
        PuntoControl input = PuntoControl.builder()
                .id("  PC_WHITESPACE  ")
                .nombre("  Punto Control con Espacios  ")
                .build();

        // When - Procesamos el item
        PuntoControl result = puntoControlProcessor.process(input);

        // Then - Debe retornar el mismo objeto con espacios preservados
        assertNotNull(result);
        assertSame(input, result);
        assertEquals("  PC_WHITESPACE  ", result.getId());
        assertEquals("  Punto Control con Espacios  ", result.getNombre());
    }
}