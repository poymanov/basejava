package com.basejava.util;

import com.basejava.model.AbstractSection;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.Reader;
import java.io.Writer;

public class JsonParser {
    private static Gson GSON = new GsonBuilder()
            .registerTypeAdapter(AbstractSection.class, new JsonSectionAdapter())
            .create();

    public static <T> T read(Reader reader, Class<T> clazz) {
        return GSON.fromJson(reader, clazz);
    }

    public static <T> void write(T object, Writer writer) {
        GSON.toJson(object, writer);
    }

    public static <T> T read(String content, Class<T> cl) {
        return GSON.fromJson(content, cl);
    }

    public static <T> String write(T object) {
        return GSON.toJson(object);
    }

    public static <T> String write(T object, Class<T> cl) {
        return GSON.toJson(object, cl);
    }
}