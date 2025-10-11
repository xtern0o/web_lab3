package org.example.utils.validators;

import org.example.entity.Point;

import java.util.function.Predicate;

// TODO: сделать логику валидации
public class PointValidator extends Validator<Point> {
    public PointValidator() {
        super(point -> true);
    }

    /**
     * Проверка принадлежности точке к площади согласно варианту
     * @param point координаты и радиус
     * @return true если внутри, false если вне площади
     */
    public static boolean checkArea(Point point) {
        return true;
    }
}
