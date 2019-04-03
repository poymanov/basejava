package com.basejava.storage;

import org.junit.jupiter.api.Nested;

public class AllStorageTest {

    @Nested
    class Array extends ArrayStorageTest {
    }

    @Nested
    class SortedArray extends SortedArrayStorageTest {
    }

    @Nested
    class List extends ListStorageTest {
    }

    @Nested
    class Map extends MapStorageTest {
    }

    @Nested
    class MapHash extends MapHashStorageTest {
    }
}
