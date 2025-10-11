package org.example.utils.exceptions;

import org.example.entity.Point;

public class ValidationError extends RuntimeException {
    public ValidationError(Point p) {
        super(String.format("Object %s is not valid", p));
    }
}
