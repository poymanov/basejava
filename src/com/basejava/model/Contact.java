package com.basejava.model;

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
}
