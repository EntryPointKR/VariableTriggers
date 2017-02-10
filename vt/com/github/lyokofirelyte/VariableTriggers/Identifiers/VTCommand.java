package com.github.lyokofirelyte.VariableTriggers.Identifiers;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(value = RetentionPolicy.RUNTIME)
@Target(value = ElementType.METHOD)
public @interface VTCommand {
    String[] aliases();

    String name() default "none";

    String desc() default "A VT Command";

    String help() default "/vt ?";

    String perm() default "vtriggers.use";

    boolean player() default false;

    int max() default 9999;

    int min() default 0;
}