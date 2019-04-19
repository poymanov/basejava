package com.basejava.model;

import java.util.Objects;

public class Contact {
    private final String title;
    private final String link;

    public Contact(String title, String link) {
        this.title = title;
        this.link = link;
    }

    public String getTitle() {
        return title;
    }

    public String getLink() {
        return link;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Contact contact = (Contact) o;
        return title.equals(contact.title) &&
                link.equals(contact.link);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, link);
    }
}
