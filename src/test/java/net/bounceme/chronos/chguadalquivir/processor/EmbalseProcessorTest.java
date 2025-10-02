package net.bounceme.chronos.chguadalquivir.processor;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import net.bounceme.chronos.chguadalquivir.model.Embalse;

@ExtendWith(MockitoExtension.class)
class EmbalseProcessorTest {

    private EmbalseProcessor embalseProcessor;

    @BeforeEach
    void setUp() {
        embalseProcessor = new EmbalseProcessor();
    }

    @Test
    void testDefaultConstructor() {
        // When - Creamos una instancia
        EmbalseProcessor processor = new EmbalseProcessor();
        
        // Then - Debe crearse correctamente
        assertNotNull(processor);
    }

    @Test
    void testProcessWithValidEmbalse() {
        // Given - Un Embalse con datos completos
        Embalse input = Embalse.builder()
                .id("EMB123")
                .nombre("Embalse de Prueba")
                .capacidad(1500.5f)
                .men(750.2f)
                .build();

        // When - Procesamos el item
        Embalse result = embalseProcessor.process(input);

        // Then - Debe retornar el mismo objeto (identidad)
        assertNotNull(result);
        assertSame(input, result); // Misma instancia
        assertEquals("EMB123", result.getId());
        assertEquals("Embalse de Prueba", result.getNombre());
        assertEquals(1500.5f, result.getCapacidad());
        assertEquals(750.2f, result.getMen());
    }

    @Test
    void testProcessWithNullEmbalse() {
        // Given - Un Embalse nulo
        
        // When - Procesamos el item nulo
        Embalse result = embalseProcessor.process(null);

        // Then - Debe retornar nulo
        assertNull(result);
    }

    @Test
    void testProcessWithEmbalseWithNullValues() {
        // Given - Un Embalse con valores nulos
        Embalse input = Embalse.builder()
                .id(null)
                .nombre(null)
                .capacidad(null)
                .men(null)
                .build();

        // When - Procesamos el item
        Embalse result = embalseProcessor.process(input);

        // Then - Debe retornar el mismo objeto con valores nulos
        assertNotNull(result);
        assertSame(input, result);
        assertNull(result.getId());
        assertNull(result.getNombre());
        assertNull(result.getCapacidad());
        assertNull(result.getMen());
    }

    @Test
    void testProcessWithEmbalseWithEmptyStrings() {
        // Given - Un Embalse con strings vacíos
        Embalse input = Embalse.builder()
                .id("")
                .nombre("")
                .capacidad(0.0f)
                .men(0.0f)
                .build();

        // When - Procesamos el item
        Embalse result = embalseProcessor.process(input);

        // Then - Debe retornar el mismo objeto con strings vacíos
        assertNotNull(result);
        assertSame(input, result);
        assertEquals("", result.getId());
        assertEquals("", result.getNombre());
        assertEquals(0.0f, result.getCapacidad());
        assertEquals(0.0f, result.getMen());
    }

    @Test
    void testProcessWithEmbalseWithZeroValues() {
        // Given - Un Embalse con valores cero
        Embalse input = Embalse.builder()
                .id("EMB000")
                .nombre("Embalse Cero")
                .capacidad(0.0f)
                .men(0.0f)
                .build();

        // When - Procesamos el item
        Embalse result = embalseProcessor.process(input);

        // Then - Debe retornar el mismo objeto con valores cero
        assertNotNull(result);
        assertSame(input, result);
        assertEquals("EMB000", result.getId());
        assertEquals("Embalse Cero", result.getNombre());
        assertEquals(0.0f, result.getCapacidad());
        assertEquals(0.0f, result.getMen());
    }

    @Test
    void testProcessWithEmbalseWithNegativeValues() {
        // Given - Un Embalse con valores negativos
        Embalse input = Embalse.builder()
                .id("EMB_NEG")
                .nombre("Embalse Negativo")
                .capacidad(-100.0f)
                .men(-50.0f)
                .build();

        // When - Procesamos el item
        Embalse result = embalseProcessor.process(input);

        // Then - Debe retornar el mismo objeto con valores negativos
        assertNotNull(result);
        assertSame(input, result);
        assertEquals("EMB_NEG", result.getId());
        assertEquals("Embalse Negativo", result.getNombre());
        assertEquals(-100.0f, result.getCapacidad());
        assertEquals(-50.0f, result.getMen());
    }

    @Test
    void testProcessWithEmbalseWithFloatPrecision() {
        // Given - Un Embalse con valores float de alta precisión
        Embalse input = Embalse.builder()
                .id("EMB_PREC")
                .nombre("Embalse Precisión")
                .capacidad(1234.5678f)
                .men(987.6543f)
                .build();

        // When - Procesamos el item
        Embalse result = embalseProcessor.process(input);

        // Then - Debe retornar el mismo objeto manteniendo la precisión
        assertNotNull(result);
        assertSame(input, result);
        assertEquals("EMB_PREC", result.getId());
        assertEquals("Embalse Precisión", result.getNombre());
        assertEquals(1234.5678f, result.getCapacidad(), 0.0001f);
        assertEquals(987.6543f, result.getMen(), 0.0001f);
    }

    @Test
    void testProcessWithEmbalseWithSpecialCharacters() {
        // Given - Un Embalse con caracteres especiales en el nombre
        Embalse input = Embalse.builder()
                .id("EMB_ESP")
                .nombre("Embalse Ñoño-Ciénaga")
                .capacidad(1000.0f)
                .men(500.0f)
                .build();

        // When - Procesamos el item
        Embalse result = embalseProcessor.process(input);

        // Then - Debe retornar el mismo objeto preservando caracteres especiales
        assertNotNull(result);
        assertSame(input, result);
        assertEquals("EMB_ESP", result.getId());
        assertEquals("Embalse Ñoño-Ciénaga", result.getNombre());
        assertEquals(1000.0f, result.getCapacidad());
        assertEquals(500.0f, result.getMen());
    }

    @Test
    void testProcessWithEmbalseWithLongNames() {
        // Given - Un Embalse con nombres largos
        String longName = "Embalse de la Presa de Regulación del Río Guadalquivir en la Zona Norte";
        Embalse input = Embalse.builder()
                .id("EMB_LARGO")
                .nombre(longName)
                .capacidad(2000.0f)
                .men(1000.0f)
                .build();

        // When - Procesamos el item
        Embalse result = embalseProcessor.process(input);

        // Then - Debe retornar el mismo objeto con nombres largos
        assertNotNull(result);
        assertSame(input, result);
        assertEquals("EMB_LARGO", result.getId());
        assertEquals(longName, result.getNombre());
        assertEquals(2000.0f, result.getCapacidad());
        assertEquals(1000.0f, result.getMen());
    }

    @Test
    void testProcessMultipleEmbalses() {
        // Given - Múltiples Embalses
        Embalse[] inputs = {
            Embalse.builder().id("EMB1").nombre("Embalse 1").capacidad(100.0f).men(50.0f).build(),
            Embalse.builder().id("EMB2").nombre("Embalse 2").capacidad(200.0f).men(100.0f).build(),
            Embalse.builder().id("EMB3").nombre("Embalse 3").capacidad(300.0f).men(150.0f).build()
        };

        // When - Procesamos todos los items
        for (Embalse input : inputs) {
            Embalse result = embalseProcessor.process(input);
            
            // Then - Cada resultado debe ser la misma instancia
            assertNotNull(result);
            assertSame(input, result);
            assertEquals(input.getId(), result.getId());
            assertEquals(input.getNombre(), result.getNombre());
            assertEquals(input.getCapacidad(), result.getCapacidad());
            assertEquals(input.getMen(), result.getMen());
        }
    }

    @Test
    void testProcessReturnsSameInstance() {
        // Given - Un Embalse
        Embalse input = Embalse.builder()
                .id("EMB_SAME")
                .nombre("Mismo Embalse")
                .capacidad(500.0f)
                .men(250.0f)
                .build();

        // When - Procesamos el mismo item múltiples veces
        Embalse result1 = embalseProcessor.process(input);
        Embalse result2 = embalseProcessor.process(input);
        Embalse result3 = embalseProcessor.process(input);

        // Then - Todas las veces debe retornar la misma instancia
        assertSame(input, result1);
        assertSame(input, result2);
        assertSame(input, result3);
        assertSame(result1, result2);
        assertSame(result2, result3);
    }

    @Test
    void testProcessIsIdentityOperation() {
        // Given - Un Embalse
        Embalse original = Embalse.builder()
                .id("EMB_IDENTITY")
                .nombre("Embalse Identidad")
                .capacidad(999.9f)
                .men(888.8f)
                .build();

        // When - Procesamos el item
        Embalse result = embalseProcessor.process(original);

        // Then - Debe ser una operación identidad (misma instancia)
        assertSame(original, result);
        
        // Verificamos que todos los campos sean iguales (que no se modificó)
        assertEquals(original.getId(), result.getId());
        assertEquals(original.getNombre(), result.getNombre());
        assertEquals(original.getCapacidad(), result.getCapacidad());
        assertEquals(original.getMen(), result.getMen());
    }

    @Test
    void testComponentAnnotation() {
        // Given - La clase EmbalseProcessor
        
        // Then - Debe tener la anotación @Component
        assertNotNull(EmbalseProcessor.class.getAnnotation(org.springframework.stereotype.Component.class));
    }

    @Test
    void testImplementsItemProcessor() {
        // Given - La clase EmbalseProcessor
        
        // Then - Debe implementar ItemProcessor<Embalse, Embalse>
        assertTrue(org.springframework.batch.item.ItemProcessor.class.isAssignableFrom(EmbalseProcessor.class));
    }

    @Test
    void testMethodSignature() throws NoSuchMethodException {
        // Given - El método process
        
        // When - Obtenemos el método
        var processMethod = EmbalseProcessor.class.getMethod("process", Embalse.class);
        
        // Then - Debe tener la firma correcta
        assertNotNull(processMethod);
        assertEquals(Embalse.class, processMethod.getReturnType());
        
        // No debe declarar excepciones checked
        assertEquals(0, processMethod.getExceptionTypes().length);
    }

    @Test
    void testNoExceptionDeclaration() throws NoSuchMethodException {
        // Given - El método process
        
        // When - Obtenemos el método
        var processMethod = EmbalseProcessor.class.getMethod("process", Embalse.class);
        
        // Then - No debe declarar excepciones (a diferencia del CapacidadProcessor)
        assertEquals(0, processMethod.getExceptionTypes().length);
    }

    @Test
    void testProcessorIsStateless() {
        // Given - Múltiples instancias del processor
        EmbalseProcessor processor1 = new EmbalseProcessor();
        EmbalseProcessor processor2 = new EmbalseProcessor();
        
        Embalse input = Embalse.builder()
                .id("EMB_STATELESS")
                .nombre("Embalse Stateless")
                .capacidad(100.0f)
                .men(50.0f)
                .build();

        // When - Procesamos con diferentes instancias
        Embalse result1 = processor1.process(input);
        Embalse result2 = processor2.process(input);

        // Then - Ambas deben retornar la misma instancia de input
        assertSame(input, result1);
        assertSame(input, result2);
    }

    @Test
    void testNoTransformationApplied() {
        // Given - Un Embalse
        Embalse input = Embalse.builder()
                .id("EMB_NO_TRANSFORM")
                .nombre("Embalse Sin Transformación")
                .capacidad(123.45f)
                .men(67.89f)
                .build();

        // Capturamos el estado original
        String originalId = input.getId();
        String originalNombre = input.getNombre();
        Float originalCapacidad = input.getCapacidad();
        Float originalMen = input.getMen();

        // When - Procesamos el item
        Embalse result = embalseProcessor.process(input);

        // Then - No debe haber ninguna transformación o modificación
        assertSame(input, result);
        assertEquals(originalId, result.getId());
        assertEquals(originalNombre, result.getNombre());
        assertEquals(originalCapacidad, result.getCapacidad());
        assertEquals(originalMen, result.getMen());
        
        // Verificamos que son exactamente los mismos objetos (no copias)
        assertSame(originalId, result.getId()); // misma referencia de String
        assertSame(originalNombre, result.getNombre()); // misma referencia de String
        assertSame(originalCapacidad, result.getCapacidad()); // misma referencia de Float
        assertSame(originalMen, result.getMen()); // misma referencia de Float
    }
}