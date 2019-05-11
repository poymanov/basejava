package com.basejava.model;

import java.io.Serializable;
import java.util.Objects;

public class Contact implements Serializable {
    private static final long serialVersionUID = 1L;
    private final String title;

    public Contact(String title) {
        Objects.requireNonNull(this, "Title must not be null");
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Contact contact = (Contact) o;
        return title.equals(contact.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title);
    }
}
