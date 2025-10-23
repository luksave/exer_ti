package com.exerti.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = MarcaValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidMarca {
    
    String message() default "Marca inválida. Use uma das marcas aceitas pelo sistema.";
    
    Class<?>[] groups() default {};
    
    Class<? extends Payload>[] payload() default {};
}
