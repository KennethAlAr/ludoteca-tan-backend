package com.ccsw.tutorial.common.criteria;

import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Root;

// Creamos una clase SpecificationUtils para separar el código del .getPath()
// Cambiando el tipo del return a Path<?> Podemos usarlo en ReservationSpecification y GameSpecification
// y no repetir el código en ambas clases
public final class SpecificationUtils {

    private SpecificationUtils() {
    }

    public static Path<?> getPath(Root<?> root, String key) {
        String[] split = key.split("[.]", 0);

        Path<?> expression = root.get(split[0]);
        for (int i = 1; i < split.length; i++) {
            expression = expression.get(split[i]);
        }

        return expression;
    }
}