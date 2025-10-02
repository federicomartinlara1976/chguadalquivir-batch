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

import net.bounceme.chronos.chguadalquivir.model.Zona;

@ExtendWith(MockitoExtension.class)
class ZonasProcessorTest {

    private ZonasProcessor zonasProcessor;

    @BeforeEach
    void setUp() {
        zonasProcessor = new ZonasProcessor();
    }

    @Test
    void testDefaultConstructor() {
        // When - Creamos una instancia
        ZonasProcessor processor = new ZonasProcessor();
        
        // Then - Debe crearse correctamente
        assertNotNull(processor);
    }

    @Test
    void testProcessWithValidZona() {
        // Given - Una Zona con datos completos
        Zona input = Zona.builder()
                .codigo("ZONA123")
                .nombre("Zona de Prueba")
                .descripcion("Descripción de la zona de prueba")
                .build();

        // When - Procesamos el item
        Zona result = zonasProcessor.process(input);

        // Then - Debe retornar el mismo objeto (identidad)
        assertNotNull(result);
        assertSame(input, result); // Misma instancia
        assertEquals("ZONA123", result.getCodigo());
        assertEquals("Zona de Prueba", result.getNombre());
        assertEquals("Descripción de la zona de prueba", result.getDescripcion());
    }

    @Test
    void testProcessWithNullZona() {
        // Given - Una Zona nula
        
        // When - Procesamos el item nulo
        Zona result = zonasProcessor.process(null);

        // Then - Debe retornar nulo
        assertNull(result);
    }

    @Test
    void testProcessWithZonaWithNullValues() {
        // Given - Una Zona con valores nulos
        Zona input = Zona.builder()
                .codigo(null)
                .nombre(null)
                .descripcion(null)
                .build();

        // When - Procesamos el item
        Zona result = zonasProcessor.process(input);

        // Then - Debe retornar el mismo objeto con valores nulos
        assertNotNull(result);
        assertSame(input, result);
        assertNull(result.getCodigo());
        assertNull(result.getNombre());
        assertNull(result.getDescripcion());
    }

    @Test
    void testProcessWithZonaWithEmptyStrings() {
        // Given - Una Zona con strings vacíos
        Zona input = Zona.builder()
                .codigo("")
                .nombre("")
                .descripcion("")
                .build();

        // When - Procesamos el item
        Zona result = zonasProcessor.process(input);

        // Then - Debe retornar el mismo objeto con strings vacíos
        assertNotNull(result);
        assertSame(input, result);
        assertEquals("", result.getCodigo());
        assertEquals("", result.getNombre());
        assertEquals("", result.getDescripcion());
    }

    @Test
    void testProcessWithZonaWithSpecialCharacters() {
        // Given - Una Zona con caracteres especiales
        Zona input = Zona.builder()
                .codigo("ZONA_ESP")
                .nombre("Zona Ñoño-Ciénaga")
                .descripcion("Descripción con áéíóú y ñ")
                .build();

        // When - Procesamos el item
        Zona result = zonasProcessor.process(input);

        // Then - Debe retornar el mismo objeto preservando caracteres especiales
        assertNotNull(result);
        assertSame(input, result);
        assertEquals("ZONA_ESP", result.getCodigo());
        assertEquals("Zona Ñoño-Ciénaga", result.getNombre());
        assertEquals("Descripción con áéíóú y ñ", result.getDescripcion());
    }

    @Test
    void testProcessWithZonaWithLongText() {
        // Given - Una Zona con textos largos
        String longDescripcion = "Esta es una descripción muy larga de una zona hidrográfica " +
                                "que incluye múltiples características geográficas, " +
                                "hidrológicas y ambientales relevantes para el estudio.";
        Zona input = Zona.builder()
                .codigo("ZONA_LARGA")
                .nombre("Zona con Descripción Extensa")
                .descripcion(longDescripcion)
                .build();

        // When - Procesamos el item
        Zona result = zonasProcessor.process(input);

        // Then - Debe retornar el mismo objeto con textos largos
        assertNotNull(result);
        assertSame(input, result);
        assertEquals("ZONA_LARGA", result.getCodigo());
        assertEquals("Zona con Descripción Extensa", result.getNombre());
        assertEquals(longDescripcion, result.getDescripcion());
    }

    @Test
    void testProcessMultipleZonas() {
        // Given - Múltiples Zonas
        Zona[] inputs = {
            Zona.builder().codigo("ZONA1").nombre("Zona Norte").descripcion("Zona norte del río").build(),
            Zona.builder().codigo("ZONA2").nombre("Zona Sur").descripcion("Zona sur del río").build(),
            Zona.builder().codigo("ZONA3").nombre("Zona Este").descripcion("Zona este del río").build()
        };

        // When - Procesamos todos los items
        for (Zona input : inputs) {
            Zona result = zonasProcessor.process(input);
            
            // Then - Cada resultado debe ser la misma instancia
            assertNotNull(result);
            assertSame(input, result);
            assertEquals(input.getCodigo(), result.getCodigo());
            assertEquals(input.getNombre(), result.getNombre());
            assertEquals(input.getDescripcion(), result.getDescripcion());
        }
    }

    @Test
    void testProcessReturnsSameInstance() {
        // Given - Una Zona
        Zona input = Zona.builder()
                .codigo("ZONA_SAME")
                .nombre("Misma Zona")
                .descripcion("Descripción de la misma zona")
                .build();

        // When - Procesamos el mismo item múltiples veces
        Zona result1 = zonasProcessor.process(input);
        Zona result2 = zonasProcessor.process(input);
        Zona result3 = zonasProcessor.process(input);

        // Then - Todas las veces debe retornar la misma instancia
        assertSame(input, result1);
        assertSame(input, result2);
        assertSame(input, result3);
        assertSame(result1, result2);
        assertSame(result2, result3);
    }

    @Test
    void testProcessIsIdentityOperation() {
        // Given - Una Zona
        Zona original = Zona.builder()
                .codigo("ZONA_IDENTITY")
                .nombre("Zona Identidad")
                .descripcion("Descripción para test de identidad")
                .build();

        // When - Procesamos el item
        Zona result = zonasProcessor.process(original);

        // Then - Debe ser una operación identidad (misma instancia)
        assertSame(original, result);
        
        // Verificamos que todos los campos sean iguales (que no se modificó)
        assertEquals(original.getCodigo(), result.getCodigo());
        assertEquals(original.getNombre(), result.getNombre());
        assertEquals(original.getDescripcion(), result.getDescripcion());
    }

    @Test
    void testComponentAnnotation() {
        // Given - La clase ZonasProcessor
        
        // Then - Debe tener la anotación @Component
        assertNotNull(ZonasProcessor.class.getAnnotation(org.springframework.stereotype.Component.class));
    }

    @Test
    void testImplementsItemProcessor() {
        // Given - La clase ZonasProcessor
        
        // Then - Debe implementar ItemProcessor<Zona, Zona>
        assertTrue(org.springframework.batch.item.ItemProcessor.class.isAssignableFrom(ZonasProcessor.class));
    }

    @Test
    void testMethodSignature() throws NoSuchMethodException {
        // Given - El método process
        
        // When - Obtenemos el método
        var processMethod = ZonasProcessor.class.getMethod("process", Zona.class);
        
        // Then - Debe tener la firma correcta
        assertNotNull(processMethod);
        assertEquals(Zona.class, processMethod.getReturnType());
        
        // No debe declarar excepciones checked
        assertEquals(0, processMethod.getExceptionTypes().length);
    }

    @Test
    void testNoExceptionDeclaration() throws NoSuchMethodException {
        // Given - El método process
        
        // When - Obtenemos el método
        var processMethod = ZonasProcessor.class.getMethod("process", Zona.class);
        
        // Then - No debe declarar excepciones
        assertEquals(0, processMethod.getExceptionTypes().length);
    }

    @Test
    void testProcessorIsStateless() {
        // Given - Múltiples instancias del processor
        ZonasProcessor processor1 = new ZonasProcessor();
        ZonasProcessor processor2 = new ZonasProcessor();
        
        Zona input = Zona.builder()
                .codigo("ZONA_STATELESS")
                .nombre("Zona Stateless")
                .descripcion("Descripción stateless")
                .build();

        // When - Procesamos con diferentes instancias
        Zona result1 = processor1.process(input);
        Zona result2 = processor2.process(input);

        // Then - Ambas deben retornar la misma instancia de input
        assertSame(input, result1);
        assertSame(input, result2);
    }

    @Test
    void testNoTransformationApplied() {
        // Given - Una Zona
        Zona input = Zona.builder()
                .codigo("ZONA_NO_TRANSFORM")
                .nombre("Zona Sin Transformación")
                .descripcion("Descripción sin transformación")
                .build();

        // Capturamos el estado original
        String originalCodigo = input.getCodigo();
        String originalNombre = input.getNombre();
        String originalDescripcion = input.getDescripcion();

        // When - Procesamos el item
        Zona result = zonasProcessor.process(input);

        // Then - No debe haber ninguna transformación o modificación
        assertSame(input, result);
        assertEquals(originalCodigo, result.getCodigo());
        assertEquals(originalNombre, result.getNombre());
        assertEquals(originalDescripcion, result.getDescripcion());
        
        // Verificamos que son exactamente los mismos objetos (no copias)
        assertSame(originalCodigo, result.getCodigo());
        assertSame(originalNombre, result.getNombre());
        assertSame(originalDescripcion, result.getDescripcion());
    }

    @Test
    void testProcessWithMinimalData() {
        // Given - Una Zona con datos mínimos (solo código)
        Zona input = Zona.builder()
                .codigo("ZONA_MIN")
                .build();

        // When - Procesamos el item
        Zona result = zonasProcessor.process(input);

        // Then - Debe retornar el mismo objeto
        assertNotNull(result);
        assertSame(input, result);
        assertEquals("ZONA_MIN", result.getCodigo());
        assertNull(result.getNombre());
        assertNull(result.getDescripcion());
    }

    @Test
    void testProcessWithOnlyNombre() {
        // Given - Una Zona con solo nombre
        Zona input = Zona.builder()
                .nombre("Solo Nombre")
                .build();

        // When - Procesamos el item
        Zona result = zonasProcessor.process(input);

        // Then - Debe retornar el mismo objeto
        assertNotNull(result);
        assertSame(input, result);
        assertNull(result.getCodigo());
        assertEquals("Solo Nombre", result.getNombre());
        assertNull(result.getDescripcion());
    }

    @Test
    void testProcessWithOnlyDescripcion() {
        // Given - Una Zona con solo descripción
        Zona input = Zona.builder()
                .descripcion("Solo Descripción")
                .build();

        // When - Procesamos el item
        Zona result = zonasProcessor.process(input);

        // Then - Debe retornar el mismo objeto
        assertNotNull(result);
        assertSame(input, result);
        assertNull(result.getCodigo());
        assertNull(result.getNombre());
        assertEquals("Solo Descripción", result.getDescripcion());
    }

    @Test
    void testProcessWithNumericCodigo() {
        // Given - Una Zona con código numérico
        Zona input = Zona.builder()
                .codigo("001")
                .nombre("Zona 001")
                .descripcion("Zona con código numérico")
                .build();

        // When - Procesamos el item
        Zona result = zonasProcessor.process(input);

        // Then - Debe retornar el mismo objeto
        assertNotNull(result);
        assertSame(input, result);
        assertEquals("001", result.getCodigo());
        assertEquals("Zona 001", result.getNombre());
        assertEquals("Zona con código numérico", result.getDescripcion());
    }

    @Test
    void testProcessWithComplexCodigo() {
        // Given - Una Zona con código complejo
        Zona input = Zona.builder()
                .codigo("ZONA-GUAD-2024-NORTE-001")
                .nombre("Zona Compleja")
                .descripcion("Zona con código de identificación complejo")
                .build();

        // When - Procesamos el item
        Zona result = zonasProcessor.process(input);

        // Then - Debe retornar el mismo objeto
        assertNotNull(result);
        assertSame(input, result);
        assertEquals("ZONA-GUAD-2024-NORTE-001", result.getCodigo());
        assertEquals("Zona Compleja", result.getNombre());
        assertEquals("Zona con código de identificación complejo", result.getDescripcion());
    }

    @Test
    void testProcessMaintainsObjectIdentity() {
        // Given - Una Zona
        Zona input = Zona.builder()
                .codigo("ZONA_IDENTITY_TEST")
                .nombre("Test Identidad")
                .descripcion("Test de mantenimiento de identidad")
                .build();

        // When - Procesamos el item
        Zona result = zonasProcessor.process(input);

        // Then - Debe mantener la identidad del objeto
        assertEquals(input.hashCode(), result.hashCode());
        assertSame(input, result);
        assertSame(result, input);
    }

    @Test
    void testProcessWithWhitespace() {
        // Given - Una Zona con espacios en blanco
        Zona input = Zona.builder()
                .codigo("  ZONA_WHITESPACE  ")
                .nombre("  Zona con Espacios  ")
                .descripcion("  Descripción con espacios  ")
                .build();

        // When - Procesamos el item
        Zona result = zonasProcessor.process(input);

        // Then - Debe retornar el mismo objeto con espacios preservados
        assertNotNull(result);
        assertSame(input, result);
        assertEquals("  ZONA_WHITESPACE  ", result.getCodigo());
        assertEquals("  Zona con Espacios  ", result.getNombre());
        assertEquals("  Descripción con espacios  ", result.getDescripcion());
    }

    @Test
    void testProcessWithVeryLongCodigo() {
        // Given - Una Zona con código muy largo
        String longCodigo = "ZONA-" + "X".repeat(50);
        Zona input = Zona.builder()
                .codigo(longCodigo)
                .nombre("Zona Código Largo")
                .descripcion("Zona con código excesivamente largo")
                .build();

        // When - Procesamos el item
        Zona result = zonasProcessor.process(input);

        // Then - Debe retornar el mismo objeto
        assertNotNull(result);
        assertSame(input, result);
        assertEquals(longCodigo, result.getCodigo());
        assertEquals("Zona Código Largo", result.getNombre());
        assertEquals("Zona con código excesivamente largo", result.getDescripcion());
    }

    @Test
    void testProcessWithHtmlInDescripcion() {
        // Given - Una Zona con HTML en la descripción
        Zona input = Zona.builder()
                .codigo("ZONA_HTML")
                .nombre("Zona HTML")
                .descripcion("Descripción con <b>HTML</b> y &amp; símbolos")
                .build();

        // When - Procesamos el item
        Zona result = zonasProcessor.process(input);

        // Then - Debe retornar el mismo objeto preservando el HTML
        assertNotNull(result);
        assertSame(input, result);
        assertEquals("ZONA_HTML", result.getCodigo());
        assertEquals("Zona HTML", result.getNombre());
        assertEquals("Descripción con <b>HTML</b> y &amp; símbolos", result.getDescripcion());
    }
}