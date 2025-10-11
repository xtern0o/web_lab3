package org.example.managers;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.example.entity.Point;
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
    private final List<Point> pointsCache = new CopyOnWriteArrayList<>();

    @Inject
    PointsService pointsService;

    @PostConstruct
    void init() {
        refresh();
    }

    public List<Point> getAll() {
        return Collections.unmodifiableList(pointsCache);
    }

    public synchronized void refresh() {
        List<Point> fresh = pointsService.getAllCreatedAtDesc();
        pointsCache.clear();
        pointsCache.addAll(fresh);
    }

    public void add(Point p) throws ValidationError {
        pointsService.save(p);
        pointsCache.add(p);
    }

    public String testString() {
        return "Hello, pointsBean!";
    }
}
