package com.basejava.model;

import java.util.ArrayList;

public class ListSection extends Section {
    private ArrayList<String> items;

    public ArrayList<String> getItems() {
        return items;
    }

    public void setItems(ArrayList<String> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        StringBuilder content = new StringBuilder();

        for (String item : items) {
            content.append("- ").append(item).append("\n");
        }

        return content.toString();
    }
}
