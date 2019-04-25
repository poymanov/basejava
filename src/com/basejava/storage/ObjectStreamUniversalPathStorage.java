package com.basejava.storage;

public class ObjectStreamUniversalPathStorage extends AbstractUniversalPathStorage {

    protected ObjectStreamUniversalPathStorage(String dir) {
        super(dir);
        setIoStrategy(new ObjectStreamIOStrategy());
    }
}
