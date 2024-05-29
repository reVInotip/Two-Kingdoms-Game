package model;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface UnitView {
    int width() default 10;
    int height() default 10;
    int[] color();
    String type();
    String name() default "";
}
