package org.example.utils.validators;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Singleton;
import org.example.entity.PointEntity;

// TODO: сделать логику валидации
@ApplicationScoped
public class PointValidator extends AbstractValidator<PointEntity> {
    public PointValidator() {
        super(point -> true);
    }

    /**
     * Проверка принадлежности точке к площади согласно варианту
     * @param point координаты и радиус
     * @return true если внутри, false если вне площади
     */
    public static boolean checkArea(PointEntity point) {
        return true;
    }
}
