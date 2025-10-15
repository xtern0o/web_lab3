package org.example.controller;

import com.google.gson.Gson;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.validator.ValidatorException;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.example.dto.PointDTO;
import org.example.entity.PointEntity;
import org.example.managers.PointsBean;
import org.example.services.WeatherService;
import org.example.utils.exceptions.APIException;
import org.example.utils.exceptions.ValidationError;
import org.example.utils.validators.PointValidator;
import org.primefaces.PrimeFaces;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Бин, отвечающий за данные в форме каждого пользователя
 */
@Data
@Named("inputBean")
@SessionScoped
public class InputBean implements Serializable {
    @Inject
    PointsBean pointsBean;
    @Inject
    PointValidator pointValidator;
    @Inject
    WeatherService weatherService;

    private Float x;
    private Float y;
    private List<String> selectedRValues;
    private Float temperature;

    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private String ip;

    @PostConstruct
    public void init() {
        selectedRValues = new ArrayList<>();
        setCurrentIp();
        refreshTemperature();
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

    /**
     * Отправка формы, проверка факта попадания точек и обновление кэша
     */
    public void check() {
        if (selectedRValues.isEmpty()) throw new ValidatorException(new FacesMessage("Ошибка валидации", "Валидация провалилась!"));

        refreshTemperature();

        List<PointDTO> pointDTOs = new CopyOnWriteArrayList<>();
        selectedRValues.forEach((r) -> pointDTOs.add(new PointDTO(x, y, Float.parseFloat(r), this.temperature)));
        for (int i = 0; i < pointDTOs.size(); i++) {
            PointDTO currentPointDTO = pointDTOs.get(i);
            currentPointDTO.setHit(pointValidator.checkArea(currentPointDTO));
            pointDTOs.set(i, currentPointDTO);
        }

        List<PointEntity> pointEntities = new CopyOnWriteArrayList<>();
        pointDTOs.forEach(pointDTO -> pointEntities.add(new PointEntity(pointDTO)));
        try {
            pointsBean.addAll(pointEntities);
        } catch (ValidationError validationError) {
            throw new ValidatorException(new FacesMessage("Ошибка валидации", validationError.getMessage()));
        } finally {
            String pointsJsonRaw = new Gson().toJson(pointDTOs);
            PrimeFaces.current().ajax().addCallbackParam("pointsJsonRaw", pointsJsonRaw);
        }
    }

    /**
     * Получить просто все точки из кэша
     * @return List из DTOшек
     */
    public List<PointDTO> getAllPoints() {
        return pointsBean.getAll().stream()
                .map(PointDTO::new)
                .toList();
    }

    /**
     * Получить и перевернуть все точки из кэша
     * @return List из DTOшек
     */
    public List<PointDTO> getReversedPoints() {
        List<PointDTO> reversedList = new ArrayList<>(getAllPoints());
        Collections.reverse(reversedList);
        return reversedList;
    }

    /**
     * Получение температуры по айпишнику
     */
    private void refreshTemperature() {
        try {
            this.temperature = weatherService.getTemperatureByIp(this.ip);
        } catch (APIException apiException) {
            FacesMessage apiExceptionMessage = new FacesMessage(
                    FacesMessage.SEVERITY_ERROR,
                    "Ошибка API",
                    apiException.getMessage()
            );
            FacesContext.getCurrentInstance().addMessage(null, apiExceptionMessage);
        }
    }

    /**
     * Метод для получения айпишника пользователя
     */
    private void setCurrentIp() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpServletRequest httpServletRequest = (HttpServletRequest) facesContext.getExternalContext().getRequest();

        String ipAdress = httpServletRequest.getHeader("X-FORWARDED-FOR");
        if (ipAdress != null && !ipAdress.isEmpty()) {
            ipAdress = ipAdress.split(",")[0];
        } else {
            ipAdress = httpServletRequest.getRemoteAddr();
        }

        this.ip = ipAdress;
    }

}
