package com.basejava.model;

import java.util.ArrayList;
import java.util.Objects;

public class OrganizationSection extends AbstractSection {
    private static final long serialVersionUID = 1L;

    private ArrayList<Organization> items;

    public OrganizationSection(ArrayList<Organization> items) {
        this.items = items;
    }

    public ArrayList<Organization> getItems() {
        return items;
    }

    public void setItems(ArrayList<Organization> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        StringBuilder content = new StringBuilder();

        for (Organization item : items) {
            content.append(item).append("\n\n");
        }

        return content.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrganizationSection that = (OrganizationSection) o;
        return items.equals(that.items);
    }

    @Override
    public int hashCode() {
        return Objects.hash(items);
    }
}
