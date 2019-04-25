package com.basejava.storage;

import java.io.File;

public class ObjectStreamUniversalFileStorage extends AbstractUniversalFileStorage {

    protected ObjectStreamUniversalFileStorage(File directory) {
        super(directory);
        setIoStrategy(new ObjectStreamIOStrategy());
    }
}
