package com.basejava.model;

import java.io.Serializable;
import java.util.List;

public class OrganizationList implements Serializable {
    private static final long serialVersionUID = 1L;

    private String title;

    private List<OrganizationItem> items;

    public OrganizationList(String title, List<OrganizationItem> items) {
        this.title = title;
        this.items = items;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<OrganizationItem> getItems() {
        return items;
    }

    public void setItems(List<OrganizationItem> items) {
        this.items = items;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrganizationList that = (OrganizationList) o;

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

        for (OrganizationItem item : items) {
            content.append(item);
        }

        return content.toString();
    }
}
