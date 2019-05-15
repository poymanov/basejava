package com.basejava.model;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

public class Organization implements Serializable {
    private static final long serialVersionUID = 1L;

    private String title;

    private String url;

    private List<Position> items;

    public Organization(String title, List<Position> items) {
        Objects.requireNonNull(title, "Title must not be null");
        this.title = title;
        this.items = items;
    }

    public Organization(String title, String url, List<Position> items) {
        this(title, items);
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<Position> getItems() {
        return items;
    }

    public void setItems(List<Position> items) {
        this.items = items;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Organization that = (Organization) o;

        if (!title.equals(that.title)) return false;
        return items.equals(that.items);
    }

    @Override
    public int hashCode() {
        int result = title.hashCode();
        result = 31 * result + items.hashCode();
        return result;
    }

    @Override
    public String toString() {
        StringBuilder content = new StringBuilder();

        content.append(title);
        content.append("\n");

        for (Position item : items) {
            content.append(item);
        }

        return content.toString();
    }
}
