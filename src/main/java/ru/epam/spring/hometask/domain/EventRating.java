package ru.epam.spring.hometask.domain;

/**
 * Created by Dmitrii_Topolnik on 7/13/2017.
 */
public enum EventRating {

    LOW(0.8),

    MID(1.0),

    HIGH(1.2);

    private double coefficient;

    EventRating(double coefficient) {
        this.coefficient = coefficient;
    }

    public double getCoefficient() {
        return coefficient;
    }
}