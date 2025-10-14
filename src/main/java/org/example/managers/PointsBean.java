package org.example.managers;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.example.entity.PointEntity;
import org.example.utils.exceptions.ValidationError;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Managed Bean для управления точками
 */
@Named("points")
@ApplicationScoped
public class PointsBean {
    private final List<PointEntity> pointsCache = new CopyOnWriteArrayList<>();

    @Inject
    PointsService pointsService;

    @PostConstruct
    void init() {
        refresh();
    }

    public List<PointEntity> getAll() {
        return Collections.unmodifiableList(pointsCache);
    }

    public synchronized void refresh() {
        List<PointEntity> fresh = pointsService.getAllCreatedAtDesc();
        pointsCache.clear();
        pointsCache.addAll(fresh);
    }

    public void add(PointEntity p) throws ValidationError {
        pointsService.save(p);
        pointsCache.add(p);
    }

    public String testString() {
        return "Hello, pointsBean!";
    }
}
