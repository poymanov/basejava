package com.basejava.exceptions;

public class NotExistedStorageException extends StorageException {

    public NotExistedStorageException(String uuid) {
        super("Resume "  + uuid + " is not found", uuid);
    }
}
