package sn.naavetane.backend.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME) // disponible partout
@Target(ElementType.PARAMETER) // elle est appliquée sur les paramètres
public @interface FilterAgent {
}
