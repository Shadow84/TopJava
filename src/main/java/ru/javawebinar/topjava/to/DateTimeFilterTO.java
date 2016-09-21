package ru.javawebinar.topjava.to;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Created by Privat on 21.09.2016.
 */
public class DateTimeFilterTO {

    public DateTimeFilterTO() {
    }

    private  LocalDate fromLocalDate;
    private  LocalDate toLocalDate;
    private LocalTime fromLocalTime;
    private LocalTime toLocalTime;

    public LocalDate getFromLocalDate() {
        return fromLocalDate;
    }

    public void setFromLocalDate(LocalDate fromLocalDate) {
        this.fromLocalDate = fromLocalDate;
    }

    public LocalDate getToLocalDate() {
        return toLocalDate;
    }

    public void setToLocalDate(LocalDate toLocalDate) {
        this.toLocalDate = toLocalDate;
    }

    public LocalTime getFromLocalTime() {
        return fromLocalTime;
    }

    public void setFromLocalTime(LocalTime fromLocalTime) {
        this.fromLocalTime = fromLocalTime;
    }

    public LocalTime getToLocalTime() {
        return toLocalTime;
    }

    public void setToLocalTime(LocalTime toLocalTime) {
        this.toLocalTime = toLocalTime;
    }
}
