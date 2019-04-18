package com.basejava.model;

import java.util.ArrayList;

public class OrganizationSection extends Section {
    private ArrayList<OrganizationItem> items;

    public ArrayList<OrganizationItem> getItems() {
        return items;
    }

    public void setItems(ArrayList<OrganizationItem> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        StringBuilder content = new StringBuilder();

        for (OrganizationItem item : items) {
            content.append(item).append("\n\n");
        }

        return content.toString();
    }
}
