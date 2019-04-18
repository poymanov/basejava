package com.basejava.model;

import java.text.SimpleDateFormat;
import java.util.Date;

public class OrganizationItem {
    private String title;
    private String subtitle;
    private String description;
    private Date periodFrom;
    private Date periodTo;

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

    public Date getPeriodFrom() {
        return periodFrom;
    }

    public void setPeriodFrom(Date periodFrom) {
        this.periodFrom = periodFrom;
    }

    public Date getPeriodTo() {
        return periodTo;
    }

    public void setPeriodTo(Date periodTo) {
        this.periodTo = periodTo;
    }

    @Override
    public String toString() {
        String content = "";

        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/yyyy");

        content += title + "\n";
        content += dateFormat.format(periodFrom) + " - ";

        if (periodTo == null) {
            content += "Сейчас";
        } else {
            content += dateFormat.format(periodTo);
        }

        content += "\n" + subtitle + "\n";

        if (description != null) {
            content += description;
        }

        return content;
    }
}
