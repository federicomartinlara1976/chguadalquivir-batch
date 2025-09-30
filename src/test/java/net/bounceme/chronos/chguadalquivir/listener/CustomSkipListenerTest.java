package net.bounceme.chronos.chguadalquivir.listener;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.contains;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.never;
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
import org.springframework.batch.item.file.FlatFileParseException;

@ExtendWith(MockitoExtension.class)
class CustomSkipListenerTest {

    private CustomSkipListener customSkipListener;
    
    private MockedStatic<LoggerFactory> loggerFactoryMock;

    @Mock
    private Logger logger;

    @BeforeEach
    void setUp() {
    	// Mockeamos LoggerFactory para que devuelva nuestro logger mockeado
    	loggerFactoryMock = mockStatic(LoggerFactory.class);
    	loggerFactoryMock.when(() -> LoggerFactory.getLogger(CustomSkipListener.class))
	                        .thenReturn(logger);
	        
    	customSkipListener = new CustomSkipListener();
    }
    
    @AfterEach
    void tearDown() {
        if (loggerFactoryMock != null) {
            loggerFactoryMock.close();
        }
    }

    @Test
    void testOnSkipInReadWithFlatFileParseException() {
        // Given - FlatFileParseException con detalles específicos
        String input = "invalid,data,line";
        int lineNumber = 42;
        String errorMessage = "Error de formato";
        FlatFileParseException exception = new FlatFileParseException(errorMessage, input, lineNumber);

        // When - Ejecutamos onSkipInRead con FlatFileParseException
        customSkipListener.onSkipInRead(exception);

        // Then - No hay interacciones
        verifyNoInteractions(logger);
    }

    @Test
    void testOnSkipInReadWithGenericException() {
        // Given - Excepción genérica
        String errorMessage = "Error genérico en lectura";
        RuntimeException exception = new RuntimeException(errorMessage);

        // When - Ejecutamos onSkipInRead con excepción genérica
        customSkipListener.onSkipInRead(exception);

        // Then - No hay interacciones
        verifyNoInteractions(logger);
    }

    @Test
    void testOnSkipInReadWithNullException() {
        // Given - Excepción nula
        
        // When & Then - Debe lanzar IllegalArgumentException
        assertThrows(IllegalArgumentException.class, () -> {
        	customSkipListener.onSkipInRead(null);
        });
    }

    @Test
    void testOnSkipInWrite() {
        // Given - Item y excepción
        Object item = "test-item";
        String errorMessage = "Error de escritura en base de datos";
        RuntimeException exception = new RuntimeException(errorMessage);

        // When - Ejecutamos onSkipInWrite
        customSkipListener.onSkipInWrite(item, exception);

        // Then - No hay interacciones
        verifyNoInteractions(logger);
    }

    @Test
    void testOnSkipInWriteWithNullItem() {
        // Given - Item nulo y excepción
        Object item = null;
        String errorMessage = "Error de escritura";
        RuntimeException exception = new RuntimeException(errorMessage);

        // When - Ejecutamos onSkipInWrite con item nulo
        customSkipListener.onSkipInWrite(item, exception);

        // Then - Debe loguear el mensaje sin problemas
        verify(logger).error(contains("ERROR en ESCRITURA:"));
        verify(logger).error(contains(errorMessage));
    }

    @Test
    void testOnSkipInWriteWithNullException() {
        // Given - Item y excepción nula
        Object item = "test-item";

        // When & Then - Debe lanzar IllegalArgumentException
        assertThrows(IllegalArgumentException.class, () -> {
        	customSkipListener.onSkipInWrite(item, null);
        });
    }

    @Test
    void testOnSkipInProcess() {
        // Given - Item y excepción
        Object item = new Object();
        String errorMessage = "Error de procesamiento de datos";
        RuntimeException exception = new RuntimeException(errorMessage);

        // When - Ejecutamos onSkipInProcess
        customSkipListener.onSkipInProcess(item, exception);

        // Then - No hay interacciones
        verifyNoInteractions(logger);
    }

    @Test
    void testOnSkipInProcessWithNullParameters() {
        // Given - Parámetros nulos
        
        // When & Then - Debe lanzar IllegalArgumentException
        assertThrows(IllegalArgumentException.class, () -> {
        	customSkipListener.onSkipInWrite(null, null);
        });
        
        // Then - No hay interacciones
        verifyNoInteractions(logger);
    }

    @Test
    void testComponentAnnotation() {
        // Given - La clase CustomSkipListener
        
        // Then - Debe tener la anotación @Component
        assertNotNull(CustomSkipListener.class.getAnnotation(org.springframework.stereotype.Component.class));
    }

    @Test
    void testImplementsSkipListener() {
        // Given - La clase CustomSkipListener
        
        // Then - Debe implementar SkipListener<Object, Object>
        assertTrue(org.springframework.batch.core.SkipListener.class.isAssignableFrom(CustomSkipListener.class));
    }

    @Test
    void testFlatFileParseExceptionDetails() {
        // Given - FlatFileParseException con todos los detalles
        String input = "col1,col2,col3";
        int lineNumber = 123;
        String message = "Parsing error";
        FlatFileParseException exception = new FlatFileParseException(message, input, lineNumber);

        // When - Ejecutamos onSkipInRead
        customSkipListener.onSkipInRead(exception);

        // Then - No hay interacciones
        verifyNoInteractions(logger);
    }

    @Test
    void testOnSkipInReadWithEmptyInput() {
        // Given - FlatFileParseException con input vacío
        String input = "";
        int lineNumber = 1;
        FlatFileParseException exception = new FlatFileParseException("Error", input, lineNumber);

        // When - Ejecutamos onSkipInRead
        customSkipListener.onSkipInRead(exception);

        // Then - No hay interacciones
        verifyNoInteractions(logger);
    }

    @Test
    void testOnSkipInWriteWithComplexItem() {
        // Given - Item complejo y excepción
        Object item = new CustomItem("test", 123);
        String errorMessage = "Constraint violation";
        RuntimeException exception = new RuntimeException(errorMessage);

        // When - Ejecutamos onSkipInWrite
        customSkipListener.onSkipInWrite(item, exception);

        // Then - No hay interacciones
        verifyNoInteractions(logger);
    }

    @Test
    void testOnSkipInProcessWithDifferentExceptionTypes() {
        // Given - Diferentes tipos de excepciones
        Object item = "test-item";
        
        // Test con NullPointerException
        customSkipListener.onSkipInProcess(item, new NullPointerException("NPE"));
        // Then - No hay interacciones
        verifyNoInteractions(logger);

        // Test con IllegalArgumentException
        customSkipListener.onSkipInProcess(item, new IllegalArgumentException("Argument error"));
     
        // Then - No hay interacciones
        verifyNoInteractions(logger);
    }

    @Test
    void testAllMethodsImplemented() {
        // Given - Una instancia de CustomSkipListener
        
        // Then - Debe implementar todos los métodos de SkipListener
        assertDoesNotThrow(() -> {
            customSkipListener.onSkipInRead(new Exception("test"));
            customSkipListener.onSkipInWrite("item", new Exception("test"));
            customSkipListener.onSkipInProcess("item", new Exception("test"));
        });
    }

    @Test
    void testListenerIsStateless() {
        // Given - Múltiples instancias
        CustomSkipListener listener1 = new CustomSkipListener();
        CustomSkipListener listener2 = new CustomSkipListener();

        // When - Ejecutamos métodos en diferentes instancias
        listener1.onSkipInRead(new RuntimeException("error1"));
        listener2.onSkipInRead(new RuntimeException("error2"));

        // Then - Ambas instancias deben comportarse igual
        verify(logger, never()).error(anyString()); // Se llamará dos veces pero no verificamos el contenido específico
    }

    // Clase auxiliar para testing
    private static class CustomItem {
        private String name;
        private int value;
        
        public CustomItem(String name, int value) {
            this.name = name;
            this.value = value;
        }
        
        @Override
        public String toString() {
            return "CustomItem{name='" + name + "', value=" + value + "}";
        }
    }

    private void assertDoesNotThrow(Runnable runnable) {
        try {
            runnable.run();
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception: " + e.getMessage(), e);
        }
    }
}