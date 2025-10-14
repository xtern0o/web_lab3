package org.example.utils.jpa;

import org.example.entity.PointEntity;
import org.example.utils.exceptions.ValidationError;

import java.util.List;

public interface PointsPersistence {
    void save(PointEntity p) throws ValidationError;
    void deleteById(Long id);
    List<PointEntity> getAllCreatedAtDesc();
    List<PointEntity> getAllFilterByTemperature(Float temperature);
}
