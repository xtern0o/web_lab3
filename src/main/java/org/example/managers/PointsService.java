package org.example.managers;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import org.example.entity.Point;
import org.example.utils.exceptions.ValidationError;
import org.example.utils.jpa.PointsPersistence;

import java.util.List;


/**
 * Сервис для взаимодействия с БД
 */
@ApplicationScoped
@Named("pointsService")
public class PointsService implements PointsPersistence {

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional
    public void save(Point p) throws ValidationError {
        if (p.getId() == null) em.persist(p);
        else em.merge(p);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        Point p = em.find(Point.class, id);
        if (p != null) em.remove(p);
    }

    @Override
    @Transactional(Transactional.TxType.SUPPORTS)
    public List<Point> getAllCreatedAtDesc() {
        return em.createQuery(
                "SELECT p from Point p ORDER BY p.createdAt DESC",
                Point.class
        ).getResultList();
    }

    @Override
    @Transactional(Transactional.TxType.SUPPORTS)
    public List<Point> getAllFilterByTemperature(Float temperature) {
        Query q = em.createQuery(
                "SELECT p from Point p WHERE p.temperature = :temperature ORDER BY p.createdAt DESC",
                Point.class
        );
        q.setParameter("temperature", temperature);
        return q.getResultList();
    }
}
