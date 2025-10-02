package net.bounceme.chronos.chguadalquivir.processor;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.batch.item.validator.ValidationException;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import net.bounceme.chronos.chguadalquivir.model.RegistroDiarioEmbalse;
import net.bounceme.chronos.chguadalquivir.utils.DataSet;
import net.bounceme.chronos.chguadalquivir.validation.ValidatorService;

@ExtendWith(MockitoExtension.class)
class RegistroValidatorTest {
	
	private DataSet dataset;

    @Mock
    private ValidatorService<RegistroDiarioEmbalse> validatorService;

    @Mock
    private RegistroDiarioEmbalse registroDiarioEmbalse;

    @Mock
    private ConstraintViolation<RegistroDiarioEmbalse> constraintViolation;
    
    private Set<ConstraintViolation<RegistroDiarioEmbalse>> constraintViolations;

    private RegistroValidator registroValidator;

    @BeforeEach
    void setUp() {
    	dataset = new DataSet();
    	
        registroValidator = new RegistroValidator(validatorService);
    }

    @Test
    void testValidate_WhenNoViolations_ShouldNotThrowException() {
        // Given - no hay violaciones de validación
        doNothing().when(validatorService).validate(registroDiarioEmbalse);

        // When & Then - no debe lanzar excepción
        assertDoesNotThrow(() -> registroValidator.validate(registroDiarioEmbalse));

        // Verify que se llamó al servicio de validación
        verify(validatorService).validate(registroDiarioEmbalse);
    }

    @Test
    void testValidate_WhenConstraintViolations_ShouldThrowValidationException() {
        // Given - hay violaciones de validación
        String codigoEmbalse = "TEST123";
        constraintViolations = dataset.getConstraintViolations();
        ConstraintViolationException constraintException = new ConstraintViolationException(constraintViolations);

        when(registroDiarioEmbalse.getCodigo()).thenReturn(codigoEmbalse);
        doThrow(constraintException).when(validatorService).validate(registroDiarioEmbalse);

        // Mock de la utilidad estática (si es necesario, usar Mockito.mockStatic)
        // En este caso asumimos que Utils.logViolations funciona correctamente

        // When & Then - debe lanzar ValidationException
        ValidationException exception = assertThrows(ValidationException.class, 
            () -> registroValidator.validate(registroDiarioEmbalse));

        // Verify el mensaje de la excepción
        String expectedMessage = String.format("El embalse [%s] no se va a procesar", codigoEmbalse);
        assertEquals(expectedMessage, exception.getMessage());

        // Verify que se llamó al servicio de validación
        verify(validatorService).validate(registroDiarioEmbalse);
    }

    @Test
    void testValidate_WhenConstraintViolations_ShouldLogViolations() {
        // Given - hay violaciones de validación
        String codigoEmbalse = "TEST456";
        constraintViolations = dataset.getConstraintViolations();
        ConstraintViolationException constraintException = new ConstraintViolationException(constraintViolations);

        when(registroDiarioEmbalse.getCodigo()).thenReturn(codigoEmbalse);
        doThrow(constraintException).when(validatorService).validate(registroDiarioEmbalse);

        // When & Then - debe lanzar ValidationException
        assertThrows(ValidationException.class, 
            () -> registroValidator.validate(registroDiarioEmbalse));

        // Verify que se llamó al servicio de validación
        verify(validatorService).validate(registroDiarioEmbalse);
        // Nota: No podemos verificar directamente la llamada a Utils.logViolations 
        // ya que es un método estático, a menos que uses Mockito.mockStatic
    }

    @Test
    void testValidate_WithEmptyConstraintViolations_ShouldThrowValidationException() {
        // Given - violaciones vacías (caso edge)
        String codigoEmbalse = "TEST789";
        ConstraintViolationException constraintException = new ConstraintViolationException(Collections.emptySet());

        when(registroDiarioEmbalse.getCodigo()).thenReturn(codigoEmbalse);
        doThrow(constraintException).when(validatorService).validate(registroDiarioEmbalse);

        // When & Then - debe lanzar ValidationException incluso con violaciones vacías
        ValidationException exception = assertThrows(ValidationException.class, 
            () -> registroValidator.validate(registroDiarioEmbalse));

        String expectedMessage = String.format("El embalse [%s] no se va a procesar", codigoEmbalse);
        assertEquals(expectedMessage, exception.getMessage());

        verify(validatorService).validate(registroDiarioEmbalse);
    }

    @SuppressWarnings("unchecked")
	@Test
    void testConstructor() {
        // Given
        ValidatorService<RegistroDiarioEmbalse> service = mock(ValidatorService.class);
        
        // When
        RegistroValidator validator = new RegistroValidator(service);
        
        // Then
        assertNotNull(validator);
        // Podríamos usar reflexión para verificar el campo si es necesario
    }
}