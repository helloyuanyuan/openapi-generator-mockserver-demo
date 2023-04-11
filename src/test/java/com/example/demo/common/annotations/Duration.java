package com.example.demo.common.annotations;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import com.example.demo.common.extensions.DurationExtension;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import org.junit.jupiter.api.extension.ExtendWith;

@Target({TYPE, METHOD, ANNOTATION_TYPE})
@Retention(RUNTIME)
@ExtendWith(DurationExtension.class)
public @interface Duration {}
