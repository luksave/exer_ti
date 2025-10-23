package com.exerti.validation;

import com.exerti.enums.MarcaVeiculo;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class MarcaValidator implements ConstraintValidator<ValidMarca, String> {
    
    @Override
    public void initialize(ValidMarca constraintAnnotation) {}
    
    @Override
    public boolean isValid(String marca, ConstraintValidatorContext context) {
        if (marca == null || marca.trim().isEmpty()) {
            return true;
        }
        
        return MarcaVeiculo.isValidMarca(marca);
    }
}
