package com.exerti.validation;

import jakarta.validation.ConstraintValidatorContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class MarcaValidatorTest {

    private MarcaValidator marcaValidator;

    @Mock
    private ConstraintValidatorContext context;

    @BeforeEach
    void setUp() {
        marcaValidator = new MarcaValidator();
    }

    @Test
    void deve_validar_marca_correta() {
        assertTrue(marcaValidator.isValid("Honda", context));
        assertTrue(marcaValidator.isValid("Toyota", context));
        assertTrue(marcaValidator.isValid("BMW", context));
        assertTrue(marcaValidator.isValid("Mercedes-Benz", context));
        assertTrue(marcaValidator.isValid("Volkswagen", context));
    }

    @Test
    void deve_rejeitar_marca_invalida() {
        assertFalse(marcaValidator.isValid("MarcaInvalida", context));
        assertFalse(marcaValidator.isValid("Hondaa", context));
        assertFalse(marcaValidator.isValid("Toyta", context));
        assertFalse(marcaValidator.isValid("BMWX", context));
    }

    @Test
    void deve_aceitar_marca_nula() {
        assertTrue(marcaValidator.isValid(null, context));
    }

    @Test
    void deve_aceitar_marca_vazia() {
        assertTrue(marcaValidator.isValid("", context));
        assertTrue(marcaValidator.isValid("   ", context));
    }

    @Test
    void deve_ignorar_maiusculas_minusculas() {
        assertTrue(marcaValidator.isValid("honda", context));
        assertTrue(marcaValidator.isValid("HONDA", context));
        assertTrue(marcaValidator.isValid("Honda", context));
        assertTrue(marcaValidator.isValid("  Honda  ", context));
    }

    @Test
    void deve_ignorar_acentos() {
        assertTrue(marcaValidator.isValid("Citroën", context));
        assertTrue(marcaValidator.isValid("Citroen", context));
    }

    @Test
    void deve_ignorar_hifens() {
        assertTrue(marcaValidator.isValid("Mercedes-Benz", context));
        assertTrue(marcaValidator.isValid("Mercedes Benz", context));
        assertTrue(marcaValidator.isValid("Rolls-Royce", context));
        assertTrue(marcaValidator.isValid("Rolls Royce", context));
    }
}
