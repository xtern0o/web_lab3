package org.example.utils.jpa;

import org.example.entity.Point;
import org.example.utils.exceptions.ValidationError;

import java.util.List;

public interface PointsPersistence {
    void save(Point p) throws ValidationError;
    void deleteById(Long id);
    List<Point> getAllCreatedAtDesc();
    List<Point> getAllFilterByTemperature(Float temperature);
}
