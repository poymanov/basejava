package com.basejava.model;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class OrganizationSection extends AbstractSection {
    private static final long serialVersionUID = 1L;

    private ArrayList<OrganizationList> items;

    public OrganizationSection(ArrayList<OrganizationList> items) {
        this.items = items;
    }

    public ArrayList<OrganizationList> getItems() {
        return items;
    }

    public void setItems(ArrayList<OrganizationList> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        StringBuilder content = new StringBuilder();

        for (OrganizationList item : items) {
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

    @Override
    public void writeDS(DataOutputStream dos) throws IOException {
        dos.writeInt(items.size());

        for (OrganizationList item : items) {
            dos.writeUTF(item.getTitle());

            dos.writeInt(item.getItems().size());

            for (OrganizationItem subitem : item.getItems()) {
                dos.writeUTF(subitem.getTitle());
                dos.writeUTF(subitem.getDescription());
                dos.writeUTF(subitem.getPeriodFrom().toString());
                dos.writeUTF(subitem.getPeriodTo() == null ? "" : subitem.getPeriodTo().toString());
            }
        }
    }
}
