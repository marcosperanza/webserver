package com.anritsu.common.webserver.annotations;

import com.google.inject.BindingAnnotation;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Annotation to bind attributes to the context handler
 *
 * @author Marco Speranza
 */
@BindingAnnotation
@Target({ FIELD, PARAMETER, METHOD }) @Retention(RUNTIME)
public @interface ContextHandlerAttributes {
}
