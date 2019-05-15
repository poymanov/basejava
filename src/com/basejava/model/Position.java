package com.basejava.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Position implements Serializable {
    private static final long serialVersionUID = 1L;
    private String title;
    private String description;
    private LocalDate periodFrom;
    private LocalDate periodTo;

    public Position(String title, LocalDate periodFrom, LocalDate periodTo) {
        Objects.requireNonNull(title, "Title must not be null");
        Objects.requireNonNull(periodFrom, "Period from must not be null");
        this.title = title;
        this.periodFrom = periodFrom;
        this.periodTo = periodTo;
    }

    public Position(String title, String description, LocalDate periodFrom, LocalDate periodTo) {
        this(title, periodFrom, periodTo);
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getPeriodFrom() {
        return periodFrom;
    }

    public void setPeriodFrom(LocalDate periodFrom) {
        this.periodFrom = periodFrom;
    }

    public LocalDate getPeriodTo() {
        return periodTo;
    }

    public void setPeriodTo(LocalDate periodTo) {
        this.periodTo = periodTo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Position that = (Position) o;
        return title.equals(that.title) &&
                periodFrom.equals(that.periodFrom);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, periodFrom);
    }

    @Override
    public String toString() {
        String content = "";

        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("MM/yyyy");

        content += periodFrom.format(dateFormat) + " - ";

        if (periodTo == null) {
            content += "Сейчас";
        } else {
            content += periodTo.format(dateFormat);
        }

        content += "\n" + title;

        if (description != null) {
            content += "\n" + description;
        }

        content += "\n";

        return content;
    }
}
