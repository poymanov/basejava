package com.basejava.model;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Objects;

public class OrganizationItem {
    private String title;
    private String subtitle;
    private String description;
    private LocalDate periodFrom;
    private LocalDate periodTo;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
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
        OrganizationItem that = (OrganizationItem) o;
        return title.equals(that.title) &&
                subtitle.equals(that.subtitle) &&
                description.equals(that.description) &&
                periodFrom.equals(that.periodFrom) &&
                periodTo.equals(that.periodTo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, subtitle, description, periodFrom, periodTo);
    }

    @Override
    public String toString() {
        String content = "";

        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("MM/yyyy");

        content += title + "\n";
        content += periodFrom.format(dateFormat) + " - ";

        if (periodTo == null) {
            content += "Сейчас";
        } else {
            content += periodTo.format(dateFormat);
        }

        content += "\n" + subtitle + "\n";

        if (description != null) {
            content += description;
        }

        return content;
    }
}
