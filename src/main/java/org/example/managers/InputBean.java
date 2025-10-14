package org.example.managers;

import com.google.gson.Gson;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import lombok.Getter;
import lombok.Setter;
import org.example.utils.validators.PointValidator;
import org.primefaces.PrimeFaces;

import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Бин, отвечающий за данные в форме каждого пользователя
 */
@Getter
@Setter
@Named("inputBean")
@SessionScoped
public class InputBean implements Serializable {
    @Inject
    PointsBean pointsBean;
    @Inject
    PointValidator pointValidator;

    private Float x;
    private Float y;
    private List<String> selectedRValues;

    @PostConstruct
    public void init() {
        selectedRValues = new ArrayList<>();
    }

    /**
     * Очистка полей ввода
     */
    public void clear() {
        x = null;
        y = null;
        selectedRValues.clear();
    }

    /**
     * Метод, который вызывает ajax-событие изменения радиуса
     */
    public void onRChange() {
        String rJson = new Gson().toJson(selectedRValues);

        PrimeFaces.current().ajax().addCallbackParam("selectedRJson", rJson);
    }

    public void check() {

    }


}
