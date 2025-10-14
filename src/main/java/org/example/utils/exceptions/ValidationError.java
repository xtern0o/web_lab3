package org.example.utils.exceptions;

import org.example.entity.PointEntity;

public class ValidationError extends RuntimeException {
    public ValidationError(PointEntity p) {
        super(String.format("Object %s is not valid", p));
    }
}
