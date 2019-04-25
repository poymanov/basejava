package com.basejava.storage;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
    ArrayStorageTest.class,
    SortedArrayStorageTest.class,
    ListStorageTest.class,
    MapStorageTest.class,
    MapResumeStorageTest.class,
    ObjectStreamStorageTest.class,
    ObjectStreamPathStorageTest.class,
    ObjectStreamUniversalFileStorageTest.class,
    ObjectStreamUniversalPathStorageTest.class
})

public class AllStorageTest {

}
