package com.sifast.socle.springboot.service.annotations;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.ReportAsSingleViolation;

import com.sifast.socle.springboot.model.Role;
import com.sifast.socle.springboot.service.util.config.CheckUnicity;

@Documented
@Constraint(validatedBy = { CheckUnicity.class })
@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER })
@Retention(RUNTIME)
@ReportAsSingleViolation
public @interface UniqueRole {

    String message() default "Unicity Constraint Error";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    Class<Role> className();

    String attributName();

}