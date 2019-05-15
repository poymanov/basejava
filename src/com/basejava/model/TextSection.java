package com.basejava.model;

import java.util.Objects;

public class TextSection extends AbstractSection {
    private static final long serialVersionUID = 1L;

    private String title;

    public TextSection(String title) {
        Objects.requireNonNull(title, "Title must not be null");
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TextSection that = (TextSection) o;
        return title.equals(that.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title);
    }

    @Override
    public String toString() {
        return title;
    }
}
