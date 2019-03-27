package com.basejava.exceptions;

public class ExistedStorageException extends StorageException{

    public ExistedStorageException(String uuid) {
        super("Resume " + uuid + " already exists", uuid);
    }
}
