package org.example.dto;

import lombok.*;
import org.example.entity.PointEntity;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PointDTO {
    private Float x;
    private Float y;
    private Float r;
    private Float temperature;
    private boolean hit;

    public PointDTO(Float x, Float y, Float r, Float temperature) {
        this.x = x;
        this.y = y;
        this.r = r;
        this.hit = false;
        this.temperature = temperature;
    }

    public PointDTO(PointEntity pointEntity) {
        this.x = pointEntity.getX();
        this.y = pointEntity.getY();
        this.r = pointEntity.getR();
        this.hit = pointEntity.getHit();
        this.temperature = pointEntity.getTemperature();
    }

}
